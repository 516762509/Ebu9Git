package com.engine.train.portal.web;

import com.alibaba.fastjson.JSONObject;
import com.engine.common.util.ParamUtil;
import com.engine.train.portal.service.DomeDevPoartService;
import com.engine.train.portal.service.impl.DomeDevPoartServiceImpl;

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
        try {
            DomeDevPoartService devPortalService = new DomeDevPoartServiceImpl();
            result.put("api_status", 1);
            result.put("data", devPortalService.getTestData(parms));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("api_status", 0);
            result.put("msg", e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }
}
