package ebu9.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *  alan
 */
public class RestfulResponse {
    public static final String ERROR = "500"; //服务错误
    public static final String SUCCESS = "200"; //成功
    public static final String NULL = "400"; //其他异常
    private String status;
    private String message;
    private Object data;
    private Integer allcount; // 待办数量

    public RestfulResponse(Object data){
        this.data = data;
    }
    public RestfulResponse(Integer allcount){
        this.allcount = allcount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 错误处理
     * @return
     */
    public static String returnError(String ...msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",ERROR);
        jsonObject.put("message",msg.length>0?msg:"服务出错，请检查参数");
        jsonObject.put("data","");
        jsonObject.put("allCount","");
        return JSON.toJSONString(jsonObject);
    }
    public static String returnNULL(String ...msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",NULL);
        jsonObject.put("message",msg.length>0?msg:"用户不存在或没有数据");
        jsonObject.put("data","");
        jsonObject.put("allCount","");
        return JSON.toJSONString(jsonObject);
    }

    @Override
    public String toString() {
        if(this.data!=null||this.allcount!=null){
            this.setStatus(SUCCESS);
            this.setMessage("success");
        }else{
            this.setStatus(NULL);
            this.setMessage("数据为空");
            this.setData("");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",this.status);
        jsonObject.put("message",this.message);
        jsonObject.put("data",this.data==null?"":this.data);
        jsonObject.put("allCount",this.allcount==null?"":this.allcount);
        return JSON.toJSONString(jsonObject);
    }
}
