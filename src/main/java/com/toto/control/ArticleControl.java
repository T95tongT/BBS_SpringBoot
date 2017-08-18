package com.toto.control;

import com.toto.config.FreemarkerUtils;
import com.toto.po.Article;
import com.toto.po.Bbsuser;
import com.toto.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
@WebServlet(name = "ArticleControl",urlPatterns = "/article",initParams = {
        @WebInitParam(name="show",value = "show.ftl")
})
public class ArticleControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
    @Autowired
    ArticleService service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        switch (action){

            case "queryall":
                HashMap vmap=new HashMap();
                int pagesize=0;
                Bbsuser  user=(Bbsuser) req.getSession().getAttribute("user");
                int  curpage=Integer.parseInt(req.getParameter("cur"));

                Sort sort=new Sort(Sort.DEFAULT_DIRECTION.DESC,"id");
                String  row=req.getParameter("row");
                if(user!=null){
                    if(row!=null){
                        pagesize = Integer.parseInt(row);
                    }else {
                        pagesize=user.getPagenum();
                    }

                }else {
                    pagesize=10;
                }
                Pageable    pageable=new PageRequest(curpage,pagesize,sort);
                Page<Article> page=service.findAll(pageable,0);
                vmap.put("page",page);
                if(user!=null){
                    vmap.put("info",user.getUsername()+"登陆成功");

                }

                vmap.put("user",user);
                System.out.println("user="+user);
                FreemarkerUtils.forward(resp,map.get("show"),vmap);
                break;


            case "delz":
                String id=req.getParameter("id");
                service.delete(Integer.parseInt( id),Integer.parseInt( id));
                RequestDispatcher dispatcher=req.getRequestDispatcher("/wel");
                dispatcher.forward(req,resp);
                break;
            case "queryid":
                id=req.getParameter("id");
                req.setCharacterEncoding("utf-8");
                String jnso=service.queryContie(Integer.parseInt(id));
                PrintWriter out=resp.getWriter();
                out.print(jnso);
                out.flush();
                out.close();
                break;
            case "add"://发布主贴
                String title=req.getParameter("title");
                req.setCharacterEncoding("utf-8");
                String content=req.getParameter("content");
                Article article=new Article();
                article.setTitle(title);
                article.setContent(content);
                article.setRootid(0);
                article.setDatetime(new Date(System.currentTimeMillis()));
                Bbsuser user1= (Bbsuser) req.getSession().getAttribute("user");
                article.setBbsuser(user1);
                service.save(article);
                dispatcher=req.getRequestDispatcher("/wel");
                dispatcher.forward(req,resp);
                break;
            case "reply":
                System.out.println("==================");
                title=req.getParameter("title");
                content=req.getParameter("content");
                String rootid=req.getParameter("rootid");
                req.setCharacterEncoding("utf-8");
                article=new Article();
                article.setRootid(Integer.parseInt(rootid));
                article.setTitle(title);
                article.setContent(content);
                article.setDatetime(new Date(System.currentTimeMillis()));
                user=(Bbsuser) req.getSession().getAttribute("user");
                article.setBbsuser(user);
                service.save(article);
                dispatcher=req.getRequestDispatcher("/article?action=queryid&id="+rootid);
                dispatcher.forward(req,resp);
                break;
            case "delc":
                id=req.getParameter("id");
                rootid=req.getParameter("rootid");
                service.deletect(Integer.parseInt(id));
                dispatcher=req.getRequestDispatcher("/article?action=queryid&id="+Integer.parseInt(rootid));
                dispatcher.forward(req,resp);
                break;

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
