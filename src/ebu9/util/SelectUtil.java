package ebu9.util;

import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.Util;
import weaver.soa.workflow.request.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectUtil {
    /**
     * 获取流程表单数据
     *
     * @param requestInfo
     * @return
     */
    public static Map<String, String> getDataMap(RequestInfo requestInfo) {
        Map<String, String> dataMap = new HashMap();
        Property[] properties = requestInfo.getMainTableInfo().getProperty();
        if (properties.length > 0) {
            for (int i = 0; i < properties.length; i++) {
                String name = properties[i].getName();
                String value = Util.null2String(properties[i].getValue());
                dataMap.put(name.toLowerCase(), value);
            }
        }
        return dataMap;
    }

    /**
     * 获取明细表数据
     *
     * @param request
     * @param Mxindex
     * @return
     */
    public static ArrayList<HashMap> getMXDataMap(RequestInfo request, int Mxindex) {
        ArrayList detailArrayList = new ArrayList();
        DetailTableInfo di = request.getDetailTableInfo();
        DetailTable detailTable = di.getDetailTable(Mxindex - 1);
        Row[] rows = detailTable.getRow();
        for (int m = 0; m < rows.length; m++) {
            Cell[] cell = rows[m].getCell();
            HashMap ItemMap = new HashMap();
            for (int n = 0; n < cell.length; n++) {
                String value = cell[n].getValue();
                String name = cell[n].getName();
                ItemMap.put(name.toLowerCase(), Util.null2String(value));
            }
            detailArrayList.add(ItemMap);
        }
        return detailArrayList;
    }

    public static ArrayList<ArrayList<HashMap>> getMXDataMap1(RequestInfo request) {
        ArrayList detailArrayList = new ArrayList();
        DetailTableInfo di = request.getDetailTableInfo();
        DetailTable[] detailTables = di.getDetailTable();
        for (int i = 0; i < detailTables.length; i++) {
            ArrayList list = new ArrayList();
            DetailTable detailTable = detailTables[i];
            Row[] rows = detailTable.getRow();
            for (int m = 0; m < rows.length; m++) {
                Cell[] cell = rows[m].getCell();
                HashMap ItemMap = new HashMap();
                for (int n = 0; n < cell.length; n++) {
                    String value = cell[n].getValue();
                    String name = cell[n].getName();
                    ItemMap.put(name.toLowerCase(), value);
                }
                list.add(ItemMap);
            }
            detailArrayList.add(list);
        }
        return detailArrayList;
    }

    /**
     * 遍历部门信息
     *
     * @return
     */
    public static List<String> getDepartInfo(String departId) {
        RecordSet departRS = new RecordSet();
        List<String> deptList = new ArrayList<>();
        String departSql = "select * from hrmdepartment where id = '" + departId + "'";
        departRS.execute(departSql);
        if (departRS.next()) {
            String supdepid = Util.null2String(departRS.getString("supdepid"));
            //没有上级部门的情况
            if ("0".equals(supdepid)) {
                deptList.add(departId);
                //存在上级部门的情况
            } else {
                RecordSet departRS2 = new RecordSet();
                String sql2 = "select * from hrmdepartment where id = '" + supdepid + "'";
                departRS2.execute(sql2);
                if (departRS2.next()) {
                    String supdepid2 = Util.null2String(departRS2.getString("supdepid"));
                    //上级存在一个部门
                    if ("0".equals(supdepid2)) {
                        deptList.add(supdepid);
                        deptList.add(departId);
                    } else {
                        deptList.add(supdepid2);
                        deptList.add(supdepid);
                        deptList.add(departId);
                    }
                }
            }
        }
        return deptList;
    }

    /**
     * ecology内部表单查询单表通用方法
     *
     * @param searchParam select参数(多个参数用英文符号逗号分割,最后一个参数后不需加逗号)
     * @param tableName   查询表名
     * @param whereParam  where参数
     * @param whereValue  where值
     * @return
     */

    public static String searchValueCommonMethod(String searchParam, String tableName, String whereParam, String whereValue) {
        //定义最终返回的对象
        JSONObject obj = new JSONObject();
        //数据库驱动类
        RecordSet mainRs = new RecordSet();
        mainRs.execute("select " + searchParam + " from " + tableName + " where " + whereParam + " = '" + whereValue + "' ");
        //select参数分割
        String[] array = searchParam.split(",");
        if (mainRs.next()) {
            for (int k = 0; k <= array.length; k++) {
                obj.put(array[k], Util.null2String(mainRs.getString(array[k])));
            }
        }
        return obj.toString();
    }

    /**
     * ecology 查询第三方视图通用方法
     *
     * @param sourceName 数据源名称
     * @param selectParm 查询参数（多个参数使用逗号隔开，最后一个无需逗号）
     * @param vname      视图名称
     * @param whereParm
     * @return
     */
    public static String SearchViewvalueMethod(String sourceName, String selectParm, String vname, String whereParm,String whereValue) {
        //定义最终返回的对象
        JSONObject obj = new JSONObject();
        RecordSetDataSource recordSetDataSource = new RecordSetDataSource(sourceName);
        recordSetDataSource.execute("select " + selectParm + " from " + vname + " where " + whereParm + " = '" + whereValue + "' ");
        //select参数分割
        String[] array = selectParm.split(",");
        if (recordSetDataSource.next()) {
            for (int k = 0; k <= array.length; k++) {
                obj.put(array[k], Util.null2String(recordSetDataSource.getString(array[k])));
            }
        }
        return obj.toString();
    }
    /**
     * ecology 查询第三方视图通用方法
     *
     * @param sourceName 数据源名称
     * @param selectParm 查询参数（多个参数使用逗号隔开，最后一个无需逗号）
     * @param vname      视图名称
     * @param whereParm
     * @return
     */
    public static String SearchViewvalueMethod(String sourceName, String selectParm, String vname, String whereParm) {
        //定义最终返回的对象
        JSONObject obj = new JSONObject();
        RecordSetDataSource recordSetDataSource = new RecordSetDataSource(sourceName);
        recordSetDataSource.execute("select " + selectParm + " from " + vname + " where " + whereParm );
        //select参数分割
        String[] array = selectParm.split(",");
        if (recordSetDataSource.next()) {
            for (int k = 0; k <= array.length; k++) {
                obj.put(array[k], Util.null2String(recordSetDataSource.getString(array[k])));
            }
        }
        return obj.toString();
    }
}
