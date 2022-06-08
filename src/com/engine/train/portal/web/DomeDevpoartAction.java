package com.engine.train.portal.web;

import com.alibaba.fastjson.JSONObject;
import com.engine.common.util.ParamUtil;
import com.engine.train.portal.service.DomeDevPoartService;
import com.engine.train.portal.service.impl.DomeDevPoartServiceImpl;
import weaver.general.BaseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class DomeDevpoartAction {
    @Path("/getdata")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getData(@Context HttpServletResponse response, @Context HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> parms = ParamUtil.request2Map(request);
        String name = request.getParameter("name");
        new BaseBean().writeLog("name"+name);
        result.put("name","nihao");
        try {
            result.put("api_status",1);
            new BaseBean().writeLog("parms参数"+parms.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }
}
