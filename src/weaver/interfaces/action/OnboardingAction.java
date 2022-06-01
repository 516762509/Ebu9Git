package weaver.interfaces.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import ebu9.util.*;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.workflow.WorkflowComInfo;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 健友生化——入职登记
 *
 * @author: Chen
 * @Create: 2022-03-2022/3/28 11:34
 */

public class OnboardingAction implements Action {

    @Override
    public String execute(RequestInfo requestInfo) {

        String requestid = requestInfo.getRequestid();
        String workflowid = requestInfo.getWorkflowid();
        String workflowname = new WorkflowComInfo().getWorkflowname(workflowid);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(new Date());
        new BaseBean().writeLog("进入入职登记Action，流程id" + requestid + "流程id" + workflowid);
        try {
            String baseUrl = "http://221.226.32.202:8081";
            //获取token
            ResultMessageUtil returnData = new Gson().fromJson(BaseUtil.getToken(), (Type) ResultMessageUtil.class);
            Map<String, String> data = (Map<String, String>) returnData.getData();
            String access_token = data.get("access_token");
            new BaseBean().writeLog("获取的token：" + access_token);
            Map<String, String> headermap = new HashMap<>();//请求头
            Map<String, String> paramsmap = new HashMap<>();
            //Step2：构造请求头参数。具体参数可参考接口文档中的“请求参数Headers”
            headermap.put("access_token", access_token);
            headermap.put("client_id", "OA");
            headermap.put("signature", "e786b064ce896096141bb9f5102167534d569d324b60cf98e56924b9926a5963");
            headermap.put("busi_id", "");
            headermap.put("repeat_check", "N");
            headermap.put("ucg_flag", "Y");
            //Step2.1：设置Content-Type。参考接口文档中的“请求参数Headers”中的Content-Type
            String mediaType = "application/json;charset=utf-8";
            Map<String, String> dataMap = SelectUtil.getDataMap(requestInfo);
            String zp1 = ReadImgUtil.getImg(Integer.parseInt(dataMap.get("zp") + ""));
            String requestBody = "{\n" +
                    "    \"ufinterface\":{\n" +
                    "        \"account\":\"Kingfriend\",\n" +
                    "        \"billtype\":\"bd_psndoc\",\n" +
                    "        \"groupcode\":\"G1\",\n" +
                    "        \"isexchange\":\"Y\",\n" +
                    "        \"replace\":\"Y\",\n" +
                    "        \"sender\":\"01\",\n" +
                    "        \"bill\":{\n" +
                    "            \"id\":\"12222233\",\n" +
                    "            \"billhead\":{\n" +
                    "                \"pk_org\":\"JY01\",\n" +
                    "                \"pk_hrorg\":\"JY01\",\n" +
                    "                \"pk_group\":\"G1\",\n" +
                    "                \"code\":\"220335\",\n" +
                    "                \"name\":\"张三01\",\n" +
                    "                \"idtype\":\"CN01\",\n" +
                    "                \"id\":\"340221199007182345\",\n" +
                    "                \"photo\":\"" + zp1 + "\",\n" +
                    "                \"sex\":\"1\",\n" +
                    "                \"birthdate\":\"1990-07-18\",\n" +
                    "                \"nativeplace\":\"320111\",\n" +
                    "                \"censusaddr\":\"天润城十三街区\",\n" +
                    "                \"characterrpr\":\"9\",\n" +
                    "                \"nationality\":\"01\",\n" +
                    "                \"polity\":\"01\",\n" +
                    "                \"health\":\"10\",\n" +
                    "                \"marital\":\"20\",\n" +
                    "                \"joinworkdate\":\"2022-02-17\",\n" +
                    "                \"mobile\":\"12345678903\",\n" +
                    "                \"email\":\"12334444@qq.com\",\n" +
                    "                \"addr\":\"江苏省南京市天润城十三街区\",\n" +
                    "                \"postalcode\":\"240001\",\n" +
                    "                \"country\":\"CN\",\n" +
                    "                \"permanreside\":\"320111\",\n" +
                    "                \"creator\":\"OA\",\n" +
                    "                \"creationtime\":\"2022-02-17 11:22:26\",\n" +
                    "                \"joinpolitydate\":\"2012-02-17\",\n" +
                    "                \"age\":\"31\",\n" +
                    "                \"workage\":\"7\",\n" +
                    "                \"glbdef1\":\"01\",\n" +
                    "                \"hi_psndoc_enc\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"encourdate\":\"2022-02-17\",\n" +
                    "                            \"encourtype\":\"随机奖励\",\n" +
                    "                            \"encourrank\":\"3\",\n" +
                    "                            \"encourorg\":\"公司\",\n" +
                    "                            \"lastflag\":\"N\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_cert\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"idtype\":\"CN01\",\n" +
                    "                            \"id\":\"340221199007182345\",\n" +
                    "                            \"begindate\":\"2022-02-17\",\n" +
                    "                            \"enddate\":\"2028-02-17\",\n" +
                    "                            \"iseffect\":\"Y\",\n" +
                    "                            \"isstart\":\"Y\",\n" +
                    "                            \"lastflag\":\"Y\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_pun\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"punishdate\":\"2022-02-17\",\n" +
                    "                            \"punishtype\":\"类型01\",\n" +
                    "                            \"punishorg\":\"集团\",\n" +
                    "                            \"punishmeas\":\"辞退\",\n" +
                    "                            \"punishmatter\":\"无理由\",\n" +
                    "                            \"lastflag\":\"Y\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_title\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2022-02-17\",\n" +
                    "                            \"pk_techposttitle\":\"223\",\n" +
                    "                            \"tiptop_flag\":\"N\",\n" +
                    "                            \"lastflag\":\"N\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_linkman\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"linkman\":\"张三他爸\",\n" +
                    "                            \"mobile\":\"13345678905\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_family\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"mem_relation\":\"5\",\n" +
                    "                            \"mem_name\":\"张三他爸\",\n" +
                    "                            \"mem_corp\":\"打工人\",\n" +
                    "                            \"relaphone\":\"13345678905\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_partylog\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2012-02-17\",\n" +
                    "                            \"partyname\":\"01\",\n" +
                    "                            \"partydate\":\"2012-02-17\",\n" +
                    "                            \"partyunit\":\"测试单位\",\n" +
                    "                            \"partypsn\":\"李四\",\n" +
                    "                            \"partyduedate\":\"2012-02-17\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"100\",\n" +
                    "                            \"glbdef2\":\"a\",\n" +
                    "                            \"glbdef3\":\"a\",\n" +
                    "                            \"glbdef4\":\"a\",\n" +
                    "                            \"glbdef5\":\"a\",\n" +
                    "                            \"glbdef6\":\"a\",\n" +
                    "                            \"glbdef7\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psnorg\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2020-02-17\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"indocflag\":\"Y\",\n" +
                    "                            \"psntype\":\"01\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_hrorg\":\"JY01\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_edu\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2018-02-17\",\n" +
                    "                            \"enddate\":\"2021-02-17\",\n" +
                    "                            \"school\":\"学校1\",\n" +
                    "                            \"major\":\"计算机\",\n" +
                    "                            \"studymode\":\"1\",\n" +
                    "                            \"education\":\"14\",\n" +
                    "                            \"pk_degree\":\"302\",\n" +
                    "                            \"degreedate\":\"2022-02-17\",\n" +
                    "                            \"lasteducation\":\"Y\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_work\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2021-02-17\",\n" +
                    "                            \"enddate\":\"2022-02-15\",\n" +
                    "                            \"workcorp\":\"单位1\",\n" +
                    "                            \"workdept\":\"部门1\",\n" +
                    "                            \"workpost\":\"研发1\",\n" +
                    "                            \"certifier\":\"王二01\",\n" +
                    "                            \"certiphone\":\"12345678907\",\n" +
                    "                            \"lastflag\":\"N\",\n" +
                    "                            \"dimission_reason\":\"主动离职\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_train\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"act_name\":\"活动1\",\n" +
                    "                            \"begindate\":\"2021-02-17\",\n" +
                    "                            \"enddate\":\"2022-02-17\",\n" +
                    "                            \"tra_type\":\"TrmActItem0102002\",\n" +
                    "                            \"tra_mode\":\"002\",\n" +
                    "                            \"tra_content\":\"技术培训01\",\n" +
                    "                            \"tra_result\":\"90\",\n" +
                    "                            \"tra_cost\":\"1000\",\n" +
                    "                            \"contcode\":\"test0001\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"glbdef1\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psnjob\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"clerkcode\":\"456789\",\n" +
                    "                            \"pk_org\":\"JY01\",\n" +
                    "                            \"pk_dept\":\"05\",\n" +
                    "                            \"pk_post\":\"050001\",\n" +
                    "                            \"pk_postseries\":\"01\",\n" +
                    "                            \"pk_jobgrade\":\"\",\n" +
                    "                            \"trnsevent\":\"1\",\n" +
                    "                            \"trnstype\":\"0104\",\n" +
                    "                            \"ismainjob\":\"Y\",\n" +
                    "                            \"begindate\":\"2021-02-17\",\n" +
                    "                            \"enddate\":\"2022-05-17\",\n" +
                    "                            \"poststat\":\"Y\",\n" +
                    "                            \"lastflag\":\"Y\",\n" +
                    "                            \"pk_group\":\"G1\",\n" +
                    "                            \"pk_hrgroup\":\"G1\",\n" +
                    "                            \"pk_hrorg\":\"JY01\",\n" +
                    "                            \"endflag\":\"N\",\n" +
                    "                            \"pk_org_v\":\"JY01\",\n" +
                    "                            \"pk_dept_v\":\"\",\n" +
                    "                            \"pk_psncl\":\"01\",\n" +
                    "                            \"jobglbdef1\":\"N\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_glbdef1\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2021-02-17\",\n" +
                    "                            \"enddate\":\"2022-02-17\",\n" +
                    "                            \"glbdef1\":\"测试1\",\n" +
                    "                            \"glbdef2\":\"测试2\",\n" +
                    "                            \"glbdef3\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"hi_psndoc_glbdef2\":{\n" +
                    "                    \"item\":[\n" +
                    "                        {\n" +
                    "                            \"begindate\":\"2021-02-17\",\n" +
                    "                            \"enddate\":\"2022-02-17\",\n" +
                    "                            \"glbdef1\":\"测试岗位\",\n" +
                    "                            \"glbdef2\":\"测试\",\n" +
                    "                            \"glbdef3\":\"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            new BaseBean().writeLog("输出的数据" + requestBody);

            JSONObject requestData = JSON.parseObject(UniversalUtil.doPost("http://221.226.32.202:8081" + "/nccloud/api/hrhi/manage/personnelmgt/add", paramsmap, mediaType, headermap, requestBody));
            //   JSONObject requestData = null;
            new BaseBean().writeLog("返回的数据" + requestData);
            if (requestData.getBoolean("success")) {
                JSONObject dataObject = requestData.getJSONObject("data");
                JSONObject ufinterfaceObject = dataObject.getJSONObject("ufinterface");
                if ("Y".equals(ufinterfaceObject.get("successful"))) {
                    JSONArray sendresultObject = ufinterfaceObject.getJSONArray("sendresult");
                    JSONObject object = sendresultObject.getJSONObject(0);
                    requestInfo.getRequestManager().setMessageid(requestid);
                    requestInfo.getRequestManager().setMessagecontent(object.get("resultdescription").toString());
                    return SUCCESS;
                } else {
                    JSONArray sendresultObject = ufinterfaceObject.getJSONArray("sendresult");
                    JSONObject object = sendresultObject.getJSONObject(0);
                    requestInfo.getRequestManager().setMessageid(requestid);
                    requestInfo.getRequestManager().setMessagecontent(object.get("resultdescription").toString());
                    return FAILURE_AND_CONTINUE;
                }
            } else {
                JSONObject dataObject = requestData.getJSONObject("data");
                JSONObject ufinterfaceObject = dataObject.getJSONObject("ufinterface");
                JSONArray sendresultObject = ufinterfaceObject.getJSONArray("sendresult");
                JSONObject object = sendresultObject.getJSONObject(0);
                requestInfo.getRequestManager().setMessageid(requestid);
                requestInfo.getRequestManager().setMessagecontent(object.get("resultdescription").toString());
                return FAILURE_AND_CONTINUE;
            }
        } catch (Exception e) {
            //异常处理操作
            new BaseBean().writeLog("流程附加操作执行失败ERROR:" + e.getMessage());
            //流程提交失败提示
            requestInfo.getRequestManager().setMessageid(requestid);
            requestInfo.getRequestManager().setMessagecontent("报错信息：入职登记失败,报错内容：" + e.getMessage() + "，请联系系统管理员！");
            return FAILURE_AND_CONTINUE;
        }
    }
}
