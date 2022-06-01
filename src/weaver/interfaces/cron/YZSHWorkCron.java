package weaver.interfaces.cron;



import com.alibaba.fastjson.JSONObject;
import ebu9.util.CreateModeByTableName;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class YZSHWorkCron extends BaseCronJob {

    @Override
    public void execute() {
        BaseBean bs = new BaseBean();
        bs.writeLog("==========进入计划任务===========");
        //2.获取假期数据 1 法定节假日 2.法定节假日延续的休息日 3.调配工作日
        List<JSONObject> jsJQ = new ArrayList<>();                              //假期数据集合
        RecordSet recordSetJQ = new RecordSet();
        String sqlJQ = "select holidayDate,holidayDesc,changeType from kq_HolidaySet";
        recordSetJQ.execute(sqlJQ);
        while (recordSetJQ.next()){
            JSONObject js = new JSONObject();
            js.put("holidayDate",recordSetJQ.getString("holidayDate"));        //放假日期
            js.put("holidayDesc",recordSetJQ.getString("holidayDesc"));        //描述
            js.put("changeType",recordSetJQ.getString("changeType"));          //类型1,2,3
            jsJQ.add(js);
        }
        bs.writeLog("===poc获取假期数据集合数据成功==");


        /*  公司总值班表：uf_zbypbb，明细1、2、3：uf_zbypbb_dt1、2、3
            值班费发放明细表：uf_zbfffb，明细1：uf_zbfffb_dt1
            值班费发放汇总表：uf_zbfmxb，明细1：uf_zbfmxb_dt
        */
        //1.根据当前月份日期 判断需要插入的数据
        RecordSet recordSet1_1 = new RecordSet();
        String sql1_1 = "SELECT id,NF1,YF1 FROM UF_ZBYPBB";

        recordSet1_1.execute(sql1_1);
        while (recordSet1_1.next()){
            String id = recordSet1_1.getString("id");
            String NF1 = recordSet1_1.getString("NF1");//年份
            String YF1 = recordSet1_1.getString("YF1");//起始月份0 第一季度 3第二季度 6第三季度 9第四季度
            //2.查询当前年份 当前季度的明细表数据
            JSONObject listDate = getTimes();
            Integer times = listDate.getInteger("times");
            Integer jd = listDate.getInteger("jd");
            Integer nowJD = 0;
            if(times==0) continue;
            //如果当前年月 季度不匹配 跳过
            if(YF1.equals("0")){
                nowJD = 0;
            }
            if(YF1.equals("3")){
                nowJD = 1;
            }
            if(YF1.equals("6")){
                nowJD = 2;
            }
            if(YF1.equals("9")){
                nowJD = 3;
            }

            if(nowJD!=jd){
                continue;
            }
            bs.writeLog("--------------jin-ru-cron-work------times-----"+times);

            //3.生成数据
            List<JSONObject> jsAll = new ArrayList<>();
            for (int i=1;i<times+1;i++){
                RecordSet recordSet3_1 = new RecordSet();
                String sql3_1 = "SELECT RQ,XQ,GSLD,ZBGB,BM1,ZBRY2,BM2,ZBRY3,BM3 FROM UF_ZBYPBB_DT"+i+" WHERE MAINID = "+id;
                bs.writeLog("--------------jin-ru-cron-work-----------"+sql3_1);

                recordSet3_1.execute(sql3_1);
                JSONObject js3_1 = new JSONObject();
                while (recordSet3_1.next()){
                    String type = getDateType(jsJQ, recordSet3_1.getString("RQ"));
                    Integer mon = 0;
                    if(type.equals("1")){
                        mon = 300;
                    }else if(type.equals("2")){
                        mon = 200;
                    }else {
                        mon = 100;
                    }

                    js3_1.put("mon",mon);
                    js3_1.put("RQ",recordSet3_1.getString("RQ"));
                    js3_1.put("XQ",recordSet3_1.getString("XQ"));
                    js3_1.put("GSLD",recordSet3_1.getString("GSLD"));
                    js3_1.put("ZBGB",recordSet3_1.getString("ZBGB"));
                    js3_1.put("BM1",recordSet3_1.getString("BM1"));
                    js3_1.put("ZBRY2",recordSet3_1.getString("ZBRY2"));
                    js3_1.put("BM2",recordSet3_1.getString("BM2"));
                    js3_1.put("ZBRY3",recordSet3_1.getString("ZBRY3"));
                    js3_1.put("BM3",recordSet3_1.getString("BM3"));
                    jsAll.add(js3_1);
                }
            }

            //4.开始插表  值班费发放明细表：uf_zbfffb，明细1：uf_zbfffb_dt1
            String[] n = {"一","二","三","四"};
            Map<String,Object> map0 = new HashMap<>();
            map0.put("ND",NF1);
            map0.put("JD",jd);
            map0.put("JB",0);
            map0.put("ND",NF1);
            map0.put("ZBFYFFJD",NF1+"年第"+n[jd]+"季度值班费表");
            int jc = CreateModeByTableName.createModeData_ByTableName("uf_zbfffb", map0);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("ND",NF1);
            map1.put("JD",jd);
            map1.put("JB",1);
            map1.put("ND",NF1);
            map1.put("ZBFYFFJD",NF1+"年第"+n[jd]+"季度值班费表");
            int gc = CreateModeByTableName.createModeData_ByTableName("uf_zbfffb", map1);


            List<JSONObject> jsJCList = new ArrayList<>();
            List<JSONObject> jsGCList = new ArrayList<>();



            for (JSONObject js : jsAll) {
                String zbgb = js.getString("ZBGB");
                String bm1 = js.getString("BM1");
                String mon = js.getString("mon");
                String rq = js.getString("RQ");
                String ZBRY2 = js.getString("ZBRY2");
                String BM2 = js.getString("BM2");
                String ZBRY3 = js.getString("ZBRY3");
                String BM3 = js.getString("BM3");

                RecordSet rs = new RecordSet();
                String sql4_1 = "INSERT INTO UF_ZBFFFB_DT1( 'MAINID', 'YGBH', 'XM', 'DW', 'JE', 'RQ') VALUES ( "+gc+", NULL, "+zbgb+", "+bm1+", "+mon+", "+rq+");";
                bs.writeLog("--------------jin-ru-cron-work---sql4_1--------"+sql4_1);
                rs.execute(sql4_1);

                String sql4_2 = "INSERT INTO UF_ZBFFFB_DT1( 'MAINID', 'YGBH', 'XM', 'DW', 'JE', 'RQ') VALUES ( "+jc+", NULL, "+ZBRY2+", "+BM2+", "+mon+", "+rq+");";
                bs.writeLog("--------------jin-ru-cron-work----sql4_2-------"+sql4_2);
                rs.execute(sql4_2);

                String sql4_3 = "INSERT INTO UF_ZBFFFB_DT1( 'MAINID', 'YGBH', 'XM', 'DW', 'JE', 'RQ') VALUES ( "+jc+", NULL, "+ZBRY3+", "+BM3+", "+mon+", "+rq+");";
                bs.writeLog("--------------jin-ru-cron-work-----sql4_3------"+sql4_3);
                rs.execute(sql4_3);
            }
            //5值班费发放汇总表：uf_zbfmxb，明细1：uf_zbfmxb_dt1
            Map<String,Object> map2 = new HashMap<>();
            map2.put("ND",NF1);
            map2.put("JD",jd);
            map2.put("JB",0);
            map2.put("ND",NF1);
            map2.put("YF",YF1);
            map2.put("ZBFYFFJD",NF1+"年第"+n[jd]+"季度值班费表");
            int jcHZ = CreateModeByTableName.createModeData_ByTableName("uf_zbfmxb", map2);
            RecordSet recordSet5_1 = new RecordSet();
            String sql5_1 = "INSERT INTO UF_ZBFMXB_DT1( 'MAINID', 'YGBH', 'XM', 'DW', 'JE') SELECT "+jcHZ+",YGBH,XM,DW,SUM(JE) JE FROM UF_ZBFFFB_DT1 WHERE MAINID = "+jc+" GROUP BY XM ,MAINID,YGBH,DW ;";
            bs.writeLog("--------------jin-ru-cron-work-----sql5_1------"+sql5_1);
            recordSet5_1.execute(sql5_1);

            Map<String,Object> map3 = new HashMap<>();
            map3.put("ND",NF1);
            map3.put("JD",jd);
            map3.put("JB",1);
            map3.put("ND",NF1);
            map3.put("YF",YF1);
            map3.put("ZBFYFFJD",NF1+"年第"+n[jd]+"季度值班费表");
            int gcHZ = CreateModeByTableName.createModeData_ByTableName("uf_zbfmxb", map3);
            RecordSet recordSet5_2 = new RecordSet();
            String sql5_2 = "INSERT INTO UF_ZBFMXB_DT1('MAINID', 'YGBH', 'XM', 'DW', 'JE') SELECT "+gcHZ+",YGBH,XM,DW,SUM(JE) JE FROM UF_ZBFFFB_DT1 WHERE MAINID = "+gc+" GROUP BY XM ,MAINID,YGBH,DW ;";
            bs.writeLog("--------------jin-ru-cron-work-----sql5_2------"+sql5_2);
            recordSet5_2.execute(sql5_2);





        }






    }


    /**
     * 判断该日期是否是该月的最后一天
     * @return
     */
    public static boolean isLastDayOfMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /*
    * 根据传入日期判断需要差几个月的数据
    * 1。一个月 2.两个月 3.三个月
    * */
    public static JSONObject getTimes(){
        Calendar now = Calendar.getInstance();
        Integer n = now.get(Calendar.YEAR) ;
        Integer y =  (now.get(Calendar.MONTH) + 1) ;
        Integer r = now.get(Calendar.DAY_OF_MONTH);
        int[] nyr = {n,y,r};
        JSONObject times = new JSONObject();

        //第一种情况
        if(y==1||y==4||y==7||y==10){
            if(isLastDayOfMonth()){
                times.put("times",1);
            }else {
                times.put("times",0);
            }
        }
        //第2种情况
        if(y==2||y==5||y==8||y==11){
            if(isLastDayOfMonth()){
                times.put("times",2);
            }else {
                times.put("times",1);
            }
        }
        //第3种情况
        if(y==3||y==6||y==9||y==12){
            if(isLastDayOfMonth()){
                times.put("times",3);
            }else {
                times.put("times",2);
            }
        }


        if(y==1||y==2||y==3){
            times.put("jd",0);
        }
        if(y==4||y==5||y==6){
            times.put("jd",1);
        }
        if(y==7||y==8||y==9){
            times.put("jd",2);
        }
        if(y==10||y==11||y==12){
            times.put("jd",3);
        }




        return times;
    }

    /*
     * list 放假安排汇总
     * data 需要判定的日期--真实加班日期
     * return 1 法定节假日-300  2 休息日-200 3 工作日-100
     * */
    public  static String getDateType(List<JSONObject> list,String date) {

        BaseBean baseBean = new BaseBean();
        baseBean.writeLog("====进入日期判定"+date);

        String changeType = "";
        Boolean flag = true;  //是否确定的标志
        //1.是否存在节假日表里 1 法定节假日 2.调配工作日 3.法定节假日延续的休息日
        for (JSONObject js : list) {
            String holidayDate = js.get("holidayDate")+"";
            if(holidayDate.equals(date)){

                changeType = js.get("changeType")+"";
                baseBean.writeLog("====holidayDate"+holidayDate);
                baseBean.writeLog("====changeType"+changeType);
                flag = false;
            }
        }

        //虑调配工作日，统一按照休息日处理------------------
        if("2".equals(changeType)){
            changeType = "3"; //这里如果考虑的话 就等于3
            flag = false;
            return changeType;
        }
        if("3".equals(changeType)){
            changeType = "1";
            flag = false;
            return changeType;
        }

        if(flag){
            //2.确定类型
            changeType = isWeekend(date);
        }
        return changeType;
    }




    public static String isWeekend(String bDate)  {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = null;
        try {
            bdate = format1.parse(bDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return "2";
        } else{
            return "3";
        }

    }

    public static void main(String[] args) {


        //Integer nyr1 = getNYR();




    }


    
}
