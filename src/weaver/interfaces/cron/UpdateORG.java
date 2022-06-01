package weaver.interfaces.cron;


import ebu9.entity.NccDeptMsg;
import ebu9.entity.UpdateDeptMsg;
import ebu9.util.StrToPinyin;
import ebu9.util.UUIDUtils;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

import java.util.*;

/**
 * 组织架构定时刷新
 */
public class UpdateORG extends BaseCronJob {

    @Override
    public void execute() {
        new BaseBean().writeLog("进入组织信息更新定时任务");


        //获取所有分部部门编码，部门名称
        /*List<Map<String,String>> allBMinfoList = new ArrayList<>();
        RecordSetDataSource recordSetDataSource = new RecordSetDataSource("NCC");
        String sql1 = "select * from V_PSNDOC_DEPT where ISMAINJOB ='Y'";
        recordSetDataSource.execute(sql1);
        while (recordSetDataSource.next()){
            Map<String,String> map = new HashMap<>();
            map.put("orgcode",recordSetDataSource.getString("ORGCODE"));//组织编码
            map.put("bmmc",recordSetDataSource.getString("NAME"));
            map.put("bmbm",recordSetDataSource.getString("CODE"));
            allBMinfoList.add(map);
        }*/
        RecordSet recordSet = new RecordSet();
        String sql = "SELECT * FROM nccdeptmsg";
        recordSet.execute(sql);
        List<NccDeptMsg> list = new ArrayList<>();
        while (recordSet.next()) {
            NccDeptMsg nccDeptMsg = new NccDeptMsg();
            nccDeptMsg.setBpcode(recordSet.getString("bpcode"));
            nccDeptMsg.setOdcode(recordSet.getString("odcode"));
            nccDeptMsg.setPk_dept(recordSet.getString("pk_dept"));
            nccDeptMsg.setOdname(recordSet.getString("odname"));
            nccDeptMsg.setIsmanjob(recordSet.getString("ismanjob"));
            nccDeptMsg.setPk_org(recordSet.getString("pk_org"));
            nccDeptMsg.setOdpk_dept(recordSet.getString("odpk_dept"));
            nccDeptMsg.setOdpk_vid(recordSet.getString("odpk_vid"));
            nccDeptMsg.setPk_psndoc(recordSet.getString("pk_psndoc"));
            nccDeptMsg.setId(recordSet.getString("id"));
            list.add(nccDeptMsg);
        }

        new BaseBean().writeLog(sql);
        for (NccDeptMsg nccDeptMsg : list) {
            new BaseBean().writeLog(nccDeptMsg.toString());
            String s = UUIDUtils.randomUUID();
            String updatedept = "update hrmdepartment set\n" +
                    "                    departmentmark='"+nccDeptMsg.getOdname()+"',\n" +
                    "            departmentname='"+nccDeptMsg.getOdname()+"',\n" +
                    "                    subcompanyid1=?,\n" +
                    "            supdepid=?,\n" +
                    "                    allsupdepid=?,\n" +
                    "            canceled=?,\n" +
                    "                    departmentcode=?,\n" +
                    "            coadjutant=?,\n" +
                    "                    zzjgbmfzr=?,\n" +
                    "            zzjgbmfgld=?,\n" +
                    "                    jzglbmfzr=?,\n" +
                    "            jzglbmfgld=?,\n" +
                    "                    bmfzr=?,\n" +
                    "            bmfgld=?,\n" +
                    "                    outkey=?,\n" +
                    "            budgetAtuoMoveOrder=?,\n" +
                    "                    ecology_pinyin_search=?,\n" +
                    "            tlevel=?,\n" +
                    "                    created=?,\n" +
                    "            creater=?,\n" +
                    "                    modified=?,\n" +
                    "            modifier=?,\n" +
                    "                    uuid=?,\n" +
                    "            showorder=?,\n" +
                    "                    showOrderOfTree=?";
            //拼接ecology_pinyin_search字符信息
            new BaseBean().writeLog("执行的sql。。。。。。。。"+updatedept);
            String firstSpell = StrToPinyin.getFirstSpell(nccDeptMsg.getOdname());
            String fullSpell = StrToPinyin.getFullSpell(nccDeptMsg.getOdname());
            String ecology_pinyin_search = firstSpell + "^" + firstSpell + "^" + fullSpell + "^" + fullSpell;
            new BaseBean().writeLog("拼接ecology_pinyin_search字符信息.................." + ecology_pinyin_search);
            String s1 = UUIDUtils.randomUUID();
            recordSet.executeUpdate(updatedept,  9, 0, 0, null, null, null,null, null,null, null,null, null, null, null, ecology_pinyin_search, 2,null, 1, null, 1 , s1, 0, 0);
            new BaseBean().writeLog("更新执行完毕。。。。。。。。。。。。。");
            String selecthrmdepartment ="SELECT * FROM hrmdepartment";
            recordSet.execute(selecthrmdepartment);
            List<UpdateDeptMsg> list2 = new ArrayList<>();
            while (recordSet.next()) {
                UpdateDeptMsg updateDeptMsg = new UpdateDeptMsg();
                updateDeptMsg.setDepartmentname(recordSet.getString("departmentname"));
                list2.add(updateDeptMsg);
            }
            for (UpdateDeptMsg updateDeptMsg : list2) {
                new BaseBean().writeLog(updateDeptMsg.toString());
            }
        }
    }
}
