package com.toto.control;

import com.toto.po.Bbsuser;
import com.toto.service.BBSSerUser;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@WebServlet(name = "UserControl",urlPatterns = "/user",initParams = {
        @WebInitParam(name="show",value = "show.ftl")
})
public class UserControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
    @Autowired
    BBSSerUser service;
    HashMap<String,Object> vmap=null;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(ServletFileUpload.isMultipartContent(req)){//是二进制流的形式
            vmap=new HashMap<>();
            Bbsuser user=service.uploadpic(req);
            req.getSession().setAttribute("user",user);
            try(FileInputStream fis=new FileInputStream(user.getPicPath())){
                byte[] buffer=new byte[fis.available()];
                fis.read(buffer);
                user.setPic(buffer);
                user.setPagenum(5);

            }catch (Exception e){
                e.printStackTrace();
            }

            service.save(user);
            vmap.put("info",user.getUsername()+"注册成功");
            RequestDispatcher dispatcher=req.getRequestDispatcher("/wel");
            try {
                dispatcher.forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String action=req.getParameter("action");
            switch(action){
                case "login":  login(req, resp);
                    break;
                case "pic":
                    String id=req.getParameter("id");
                    Bbsuser user=service.findpicById(Integer.parseInt(id));
                    OutputStream os=resp.getOutputStream();
                    os.write(user.getPic());
                    os.flush();
                    os.close();
                    break;
                case "check":checkUser(req,resp);
                    break;
                case "out": out(req,resp);
                    break;
            }
        }

    }

    private void out(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        RequestDispatcher d=req.getRequestDispatcher("/wel");
        try {
            d.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkUser(HttpServletRequest req, HttpServletResponse resp){
        String username=req.getParameter("username");
        Bbsuser user=new Bbsuser();
      //  req.getSession().setAttribute("user",user);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        try {

            PrintWriter out=resp.getWriter();
            if( service.checkUser(username)!=null){
                out.print("true");
            }else{
                out.print("false");
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void reg(HttpServletRequest req, HttpServletResponse resp){

    }
    private void login(HttpServletRequest req, HttpServletResponse resp) {

        String username=req.getParameter("username");
        String password=req.getParameter("password");
        vmap=new HashMap<>();
        Bbsuser user=service.login(username,password);

        if(user!=null){//登陆成功
            String sun=req.getParameter("sun");
            if(sun!=null){//勾上了一星期
                Cookie cookie=new Cookie("papaoku",username);
                cookie.setMaxAge(3600*24*7);
                Cookie cookie1=new Cookie("papaokp",password);
                cookie1.setMaxAge(3600*24*7);
                resp.addCookie(cookie);
                resp.addCookie(cookie1);
            }


            vmap.put("info","登陆成功,欢迎"+username);
            req.getSession().setAttribute("user",user);
            RequestDispatcher dispatcher=req.getRequestDispatcher("/wel");
            try {
                dispatcher.forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {//登录失败

            req.getSession().removeAttribute("user");
            RequestDispatcher d=req.getRequestDispatcher("/wel");
            req.getSession().removeAttribute("user");
            try {
                d.forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void destroy() {
        super.destroy();
    }
    Map<String,String> map=new HashMap<>();
    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
    }
}
