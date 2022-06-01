package ebu9.util;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.TimeUtil;
import weaver.general.Util;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CreateModeByTableName {
    /**
     * 根据表名创建建模数据
     * @param modeTableName 建模表名
     * @param map 数据集合map
     * @return 数据ID
     */
    public static int createModeData_ByTableName(String modeTableName,Map map){
        int dataid=0;
        RecordSet rs = new RecordSet();
        //查询该模块表名
        rs.execute("select a.id from modeinfo a left join workflow_bill b on a.FORMID=b.id where b.tablename='"+modeTableName+"'");
        rs.next();
        int modeid = Util.getIntValue(rs.getString("id"));

        String uuid = UUID.randomUUID().toString();
        boolean flag=rs.execute("insert into "+modeTableName+"(modeuuid,modedatacreater,modedatacreatedate,modedatacreatetime,formmodeid) values('"+uuid+"',"+1+",'"+TimeUtil.getCurrentDateString()+"','"+TimeUtil.getOnlyCurrentTimeString()+"',"+modeid+")");
        if(flag){
            rs.execute("select id from "+modeTableName+" where modeuuid='"+uuid+"'");
            rs.next();
            dataid=Util.getIntValue(rs.getString("id"));
            if(dataid>0){
                //遍历数据 进行update
                String updatesql="update "+modeTableName+" set ";
                Set<String> keySet = map.keySet();
                for(String key : keySet){
                    updatesql+=key+"='"+map.get(key).toString()+"',";
                }
                if(updatesql.endsWith(",")){
                    updatesql=updatesql.substring(0, updatesql.length()-1);
                    updatesql+=" where id="+dataid;
                    rs.execute(updatesql);
                }
                /*
                 * 进行权限重构
                 */
                ModeRightInfo moderight=new ModeRightInfo();
                moderight.editModeDataShare(1, modeid, dataid);
            }
        }
        return dataid;
    }



}
