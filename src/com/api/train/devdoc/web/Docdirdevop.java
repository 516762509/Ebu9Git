package com.api.train.devdoc.web;

import com.alibaba.fastjson.JSONObject;
import com.engine.train.doc.web.DocdirdevopAction;
import com.tenpay.business.entpay.sdk.model.B2BGatewayAccountBalance;
import weaver.general.BaseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取文档权限目录并更改部门全部路径
 */
@Path("/api/docdirdevop")
public class Docdirdevop {
    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "Hello World");
        String name = request.getParameter("name");
        new BaseBean().writeLog("name" + name);
        return JSONObject.toJSONString(res);
    }
}
