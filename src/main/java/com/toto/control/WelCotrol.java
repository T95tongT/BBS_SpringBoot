package com.toto.control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
@WebServlet( name="WelCotrol",urlPatterns = "/wel",initParams = {
        @WebInitParam(name="index",value = "article?action=queryall&cur=0")
})
public class WelCotrol extends HttpServlet {
    Map<String,String> map;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       System.out.println("OK");
        //FreemarkerUtils.forward(resp,map.get("index"),null);只能用于ftl页
        RequestDispatcher dispatcher=req.getRequestDispatcher(map.get("index"));
        dispatcher.forward(req,resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        map=new HashMap<String,String>();
        map.put("index",config.getInitParameter("index"));

    }
}
