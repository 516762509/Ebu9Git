package weaver.interfaces.formmodel;

import com.wbi.util.Util;
import weaver.formmode.customjavacode.AbstractModeExpandJavaCodeNew;
import weaver.general.BaseBean;
import weaver.soa.hrm.User;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

public class DomeModelAction extends AbstractModeExpandJavaCodeNew {
    @Override
    public Map<String, String> doModeExpand(Map<String, Object> param) {
        new BaseBean().writeLog("进入建模附加操作==============");
        Map<String, String> map = new HashMap<>();
        try {
            /**
             * 建模业务逻辑
             */
            User user = (User) param.get("user");
            int billid = -1;//数据id
            int modelid = -1;//模块id
            RequestInfo requestInfo = (RequestInfo) param.get("RequestInfo");
            if (requestInfo!=null){
                billid  = Util.getIntValue(requestInfo.getRequestid());
                modelid = Util.getIntValue(requestInfo.getWorkflowid());
                if (billid>0&&modelid>0){
                    new BaseBean().writeLog("模块自定义接口动作...........");

                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "自定义信息报错");
            map.put("flag", "false");
            return map;
        }
    }
}
