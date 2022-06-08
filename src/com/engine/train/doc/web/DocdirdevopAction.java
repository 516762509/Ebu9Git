package com.engine.train.doc.web;

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


/**
 * 获取权限信息，
 * 逐步获取上级部门
 * 两个参数：对象类型；对象
 * 其中对象就是全部的上级路径地址
 *
 */

public class DocdirdevopAction {

    @Path("/getdataw")
    @GET
    @Produces({"test/plain"})
    public String getData(@Context HttpServletResponse response, @Context HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> parms = ParamUtil.request2Map(request);
        try {
            result.put("api_status", 1);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("api_status", 0);
            result.put("msg", e.getMessage());
        }
        //返回全部的部门路径
        return JSONObject.toJSONString(result);
    }
}
