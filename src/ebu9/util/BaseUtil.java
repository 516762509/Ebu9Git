package ebu9.util;

import com.weaver.general.TimeUtil;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.jysh.util.MangoUtil;
import weaver.soa.workflow.request.*;
import weaver.workflow.request.RequestComInfo;
import weaver.workflow.workflow.WorkflowComInfo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static weaver.general.LocateUtil.getUserNameById;

/**
 * 业务相关的工具类
 */
public class BaseUtil {

    /**
     * 调用接口
     *
     * @param svcUrlStr
     * @param tokenName
     * @param tokenValue
     * @param jsonRequest1
     * @return
     * @throws IOException
     */
    public static String postJSON_AolToken(String svcUrlStr, String tokenName, String tokenValue, String jsonRequest1) throws IOException {
        BaseBean bs = new BaseBean();
        URL url = new URL(svcUrlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.addRequestProperty("Cookie", tokenName + "=" + tokenValue);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Language", "en-US");
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(jsonRequest1.toCharArray());
        wr.flush();
        wr.close();
        conn.connect();
        String back = null;
        try {
            back = readHttpResponse(conn);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            bs.writeLog(e.toString());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return back;
    }

    /**
     * 解析返回值
     *
     * @param conn
     * @return
     */

    public static String readHttpResponse(HttpURLConnection conn) {
        InputStream is = null;
        BufferedReader rd = null;
        StringBuffer response = new StringBuffer();
        try {
            if (conn.getResponseCode() >= 400) {
                is = conn.getErrorStream();
            } else {
                is = conn.getInputStream();
            }
            rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                line = new String(line.getBytes(), "UTF-8");
                response.append(line);
                response.append('\n');
            }
        } catch (IOException ioe) {
            response.append(ioe.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
            if (rd != null) {
                try {
                    rd.close();
                } catch (Exception e) {
                }
            }
        }
        return (response.toString());
    }

    /**
     * 创建流程通用方法
     *
     * @param createrWorkcode   创建人工号
     * @param workflowId        流程Id
     * @param workflowName      流程名称
     *                          //* @param isNextFlow 是否提交到下一节点(0:否，1:是)
     * @param requestMainMap    主表信息 map
     *                          //* @param requestDetailListMap 明细表map
     * @param workFlowTableName 主表表名
     * @return
     */
    public static String WorkflowCreateCommonMethod(String createrWorkcode, String workflowId, String workflowName, String IsNextFlow, Map<String, String> requestMainMap, List<List<Map<String, String>>> requestDetailMapList, String workFlowTableName) {

//      LogTool logTool = new LogTool("/log/xxx/xxx", true);
        BaseBean logTool = new BaseBean();
        RecordSet datailRs = new RecordSet();
        logTool.writeLog("-----创建流程传递的参数个数-----");
        logTool.writeLog("createrWorkcode:" + createrWorkcode);
        logTool.writeLog("workflowId:" + workflowId);
        logTool.writeLog("workflowName:" + workflowName);
        //最终返回的requestid
        String requestid = "";
        //开始创建流程
        RequestService service = new RequestService();
        RequestInfo requestInfo = new RequestInfo();
        //根据人员登录名查询人员ID
        String creatorId = getUserIdByWorkcode(createrWorkcode);
        //创建人Id
        requestInfo.setCreatorid(creatorId);
        //根据创建人id获取创建人姓名
        String creatorName = getUserNameById(creatorId);
        //工作流Id
        requestInfo.setWorkflowid(workflowId);
        //请求标题
        requestInfo.setDescription(workflowName + "-" + creatorName + "-" + TimeUtil.getCurrentDateString());
        //是否提交到下一节点
        requestInfo.setIsNextFlow(IsNextFlow);
        MainTableInfo mainTableInfo = new MainTableInfo();
        //==================================


        //=========================
        Property[] propertyArray = new Property[requestMainMap.size()];
        int k = 0;
        //遍历主表的字段数据信息
        for (Map.Entry<String, String> entry : requestMainMap.entrySet()) {
            propertyArray[k] = new Property();
            propertyArray[k].setName(Util.null2String(entry.getKey()));
            propertyArray[k].setValue(Util.null2String(entry.getValue()));
            k++;
        }
        mainTableInfo.setProperty(propertyArray);
        requestInfo.setMainTableInfo(mainTableInfo);
        try {
            //获取流程创建后返回的requestid
            requestid = service.createRequest(requestInfo);
            //根据创建流程主表数据成功返回的requestid查询主表数据的id
            String searchMainTableDataIdSQL = "select id from " + workFlowTableName + " where requestid = '" + requestid + "' ";
            datailRs.execute(searchMainTableDataIdSQL);
            if (datailRs.next()) {
                //获取到主表的数据id,即得到明细表的mainid
                String mainId = Util.null2String(datailRs.getString("id"));
                //定义单个明细表存放的List<Map<String,String>>
                List<Map<String, String>> detailInsertMapList = new ArrayList<Map<String, String>>();
                //定义执行明细插入所需使用的map
                Map<String, String> detailInsertMap = new HashMap<String, String>();
                //判断传入的List<List<Map<String,String>>>  是否为空
                if (requestDetailMapList != null && requestDetailMapList.size() > 0) {
                    //遍历一共有多少明细表
                    for (int i = 0; i < requestDetailMapList.size(); i++) {
                        //遍历每一个明细表中的数据  mapList
                        detailInsertMapList = requestDetailMapList.get(i);
                        if (detailInsertMapList != null && detailInsertMapList.size() > 0) {
                            //遍历第i个明细表的数据
                            for (int j = 0; j < detailInsertMapList.size(); j++) {
                                //第j个mapList中明细表数据的map
                                detailInsertMap = detailInsertMapList.get(j);
                                //判断明细表数据的map是否为空
                                if (detailInsertMapList != null && detailInsertMapList.size() > 0) {
                                    //遍历第j个明细中的详细数据
                                    for (Map.Entry<String, String> entry : detailInsertMap.entrySet()) {
                                        detailInsertMap.put(entry.getKey(), entry.getValue());
                                    }
                                    //将主表的数据id作为明细的mainid进行map存值
                                    detailInsertMap.put("mainid", mainId);
                                }
                                //执行明细表的插入操作,i表示第i个明细表
                                MangoUtil.datainsert(workFlowTableName + "_dt" + (i + 1), detailInsertMap, "");
                            }
                        }
                    }
                }
                //记录日志
                StringBuffer sbf = new StringBuffer("-----创建工作流记录日志开始");
                WorkflowComInfo wfcif = new WorkflowComInfo();
                RequestComInfo rcif = new RequestComInfo();
                ResourceComInfo rscif = new ResourceComInfo();
                sbf.append("\r\n-----姓名:" + rscif.getLastname(rcif.getRequestCreater(requestid)));
                sbf.append("\r\n-----时间:" + rcif.getRequestCreateTime(requestid));
                sbf.append("\r\n-----创建流程:" + wfcif.getWorkflowname(workflowId));
                sbf.append("\r\n-----请求:" + rcif.getRequestname(requestid));
                sbf.append("\r\n-----请求requestid:" + requestid);
                sbf.append("\r\n-----创建工作流记录日志结束");
                logTool.writeLog(sbf.toString());
            }
        } catch (Exception e) {
            logTool.writeLog("错误信息:" + e);
        }
        return requestid;
    }

    /**
     * 根据工号获取数据
     *
     * @param createrWorkcode 工号
     * @return
     */
    private static String getUserIdByWorkcode(String createrWorkcode) {
        //LogTool logTool = new LogTool("/log/xxx/xxx", true);
        BaseBean logTool = new BaseBean();
        RecordSet datailRs = new RecordSet();
        String querycreatorIdSql = "select id from hrmresource where gh = '" + createrWorkcode + "'";
        datailRs.execute(querycreatorIdSql);
        Map<String, String> map = new HashMap<>();
        if (datailRs.next()) {
            map.put("creatorId", Util.null2String(datailRs.getString("id")));
            logTool.writeLog("查询到的数据id" + datailRs.getString("id"));
        }
        String creatorId = map.get("creatorId");
        return creatorId;
    }

    /**
     * 创建文档
     *
     * @param user
     * @param fileid
     * @param mlid
     * @param ip
     * @param port
     * @return
     */
    public static int CreateDoc(User user, int fileid, String mlid, String ip, int port) {
        RecordSet rs = new RecordSet();
        int docid = 0;
        String nameSql = "select filerealpath,iszip,imagefilename,imagefile from imagefile where imagefileid=" + fileid;
        rs.execute(nameSql);
        if (rs.next()) {
            String filePath = Util.null2String(rs.getString("filerealpath"));
            String fileName = Util.null2String(rs.getString("imagefilename"));
            String locapath = "";
            int index = 0;
            index = filePath.lastIndexOf(".");
            if (index > 0) {
                locapath = filePath.substring(0, index); //无后缀路径
            }
            DocServiceImpl serviceImpl = new DocServiceImpl(); //创建文档服务
            try {
                String session1 = serviceImpl.login(user.getLoginid(), user.getPwd(), 0, "127.0.0.1");
                DocInfo doc = new DocInfo();
                DocAttachment da = new DocAttachment();
                da.setDocid(0);
                da.setImagefileid(0);
                da.setFilename(fileName);
                da.setFilerealpath(filePath);
                da.setIszip(0);
                da.setImagefilesize(0);
                String DocSubject = "";
                if (fileName.indexOf(".") > 0) {
                    DocSubject = fileName.substring(0, fileName.indexOf("."));
                } else {
                    DocSubject = fileName;
                }
                doc.setId(0);
                doc.setDocSubject(DocSubject);
                doc.setDoccontent("附件");
                doc.setAttachments(new DocAttachment[]{da});
                doc.setSeccategory(Integer.parseInt(mlid));
                docid = serviceImpl.createDoc(doc, session1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return docid;
    }

}
