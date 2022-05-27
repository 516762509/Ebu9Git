package weaver.interfaces.action;

import ebu9.util.SelectUtil;
import weaver.general.BaseBean;
import weaver.hrm.User;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.Map;

public class DomeAction implements Action {
    @Override
    public String execute(RequestInfo requestInfo) {
        new BaseBean().writeLog("进入流程执行附加操作,流程名称：");
        try {
            /**
             * 设计业务逻辑
             */
            String requestid = requestInfo.getRequestid();//请求id
            String requestlevel = requestInfo.getRequestlevel();//请求等级
            String src = requestInfo.getRequestManager().getSrc();//当前操作类型
            String workflowid = requestInfo.getWorkflowid();//流程路径id
            String billTableName = requestInfo.getRequestManager().getBillTableName();//流程表单名称
            int billid = requestInfo.getRequestManager().getBillid();//表单数据id
            User user = requestInfo.getRequestManager().getUser();//获取当前操作用户
            String requestname = requestInfo.getRequestManager().getRequestname();//请求标题
            String remark = requestInfo.getRequestManager().getRemark();//当前用户提交的签字意见
            int formid = requestInfo.getRequestManager().getFormid();//表单id
            requestInfo.getRequestManager().getIsbill();//是否自定义表单
            new BaseBean().writeLog("流程信息......" + requestid + "......." + requestlevel + "......" + src + "........");
            //获取流程表单数据
            Map<String, String> dataMap = SelectUtil.getDataMap(requestInfo);



            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILURE_AND_CONTINUE;
        }
    }
}
