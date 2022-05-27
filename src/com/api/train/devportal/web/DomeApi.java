package com.api.train.devportal.web;

import com.api.jysh.portal.util.ResponseVO;
import com.api.jysh.portal.util.RestfulResponse;
import oracle.jdbc.proxy.annotation.Post;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/portal/GMP")
public class DomeApi {

    /**
     * getDoList可用来进行代办抓取
     *
     * @param request
     * @param response
     * @return
     */
    @Post
    @Path("/todolist")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDoList(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        new BaseBean().writeLog("进入门户api..............");
        try {
            new BaseBean().writeLog("开始抓取待办.........");
            User user = HrmUserVarify.getUser(request, response);
            //工号
            String loginid = user.getLoginid();
            //获取待办中间表
            RecordSetDataSource rsd = new RecordSetDataSource("第三方数据源名称");
            if (user.isAdmin()) {
                rsd.execute("select title,nowLink,url,todoMan,createTime from GMP_OA where valid = 1 and systemType = 'gmp' and userCode= '" + loginid + "'");
            } else {
                rsd.execute("select title,nowLink,url,todoMan,createTime from GMP_OA where valid = 1 and systemType = 'gmp' and userCode= '" + loginid + "'");
            }
            //
            List<ResponseVO> finalList = new ArrayList<ResponseVO>();
            while (rsd.next()) {
                ResponseVO responseVO = new ResponseVO();
                responseVO.setTitle(rsd.getString("title"));
                responseVO.setCreater(rsd.getString("todoMan"));
                responseVO.setCurrentnode(rsd.getString("nowLink"));
                responseVO.setReceiveDate(rsd.getString("createTime"));
                responseVO.setLinkurl(rsd.getString("url"));
                finalList.add(responseVO);
            }
            return new RestfulResponse(finalList).toString();
        } catch (Exception e) {
            e.printStackTrace();
            new BaseBean().writeLog("获取失败，" + e.getMessage());
            return RestfulResponse.returnNULL();
        }
    }

    @POST
    @Path("/todocount")
    @Produces(MediaType.APPLICATION_JSON)
    public String getToDoCount(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            User user = HrmUserVarify.getUser(request, response);
            //工号
            String loginid = user.getLoginid();
            RecordSetDataSource rsd = new RecordSetDataSource("GMP");
            if (user.isAdmin()) {
                rsd.execute("select count(*) total from GMP_OA where valid = 1 and systemType = 'gmp' and userCode= '170033'");
            } else {
                rsd.execute("select count(*) total from GMP_OA where valid = 1 and systemType = 'gmp' and userCode= '" + loginid + "'");
            }
            if (rsd.next()) {
                return new RestfulResponse(Integer.parseInt(rsd.getString("total"))).toString();
            }
        } catch (Exception e) {
            new BaseBean().writeLog("获取失败，" + e.getMessage());
        }
        return RestfulResponse.returnNULL();
    }
}
