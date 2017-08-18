package com.toto.control;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
@WebServlet(name="KindControl",urlPatterns = "/kindupload")
public class KindControl extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    Map<String,String>  types=new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pic=this.upload(req);
        PrintWriter out=resp.getWriter();
        out.print(pic);
        out.flush();
        out.close();
    }
    public String upload(HttpServletRequest request){
        CommonsMultipartResolver cmr=new CommonsMultipartResolver(request.getServletContext());
        cmr.setResolveLazily(true);
        cmr.setDefaultEncoding("utf-8");
        cmr.setMaxUploadSizePerFile(1024*1024*10);
        cmr.setMaxInMemorySize(1024*1024*10);
        cmr.setMaxUploadSizePerFile(1024*1024);
        if(cmr.isMultipart(request)){
            MultipartHttpServletRequest req=cmr.resolveMultipart(request);
            String src=this.getClass().getClassLoader().getResource("").getPath();
            MultipartFile file=req.getFile("imgFile");
            String id= UUID.randomUUID().toString();
            String dir=req.getParameter("dir");
            String picType=types.get(file.getContentType());
            String newFileName=src+"static/editor/upload/"+dir+"/"+id+picType;
            newFileName =newFileName.substring(1);
            File file1=new File(newFileName);
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String url=req.getRequestURL().toString();
            url=url.substring(0,url.lastIndexOf("/"));
            url=url+"/editor/upload/"+dir;
            JSONObject json=new JSONObject();
            json.put("error",0);
            json.put("url" ,url+"/"+id+picType);
            return json.toJSONString();
        }
        return "";
    }

 public KindControl(){
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }
    /*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        uploadPic(req);
    }
    //上传图片
    public String uploadPic(HttpServletRequest request){
        CommonsMultipartResolver cmr=new CommonsMultipartResolver(request.getServletContext());
        cmr.setDefaultEncoding("utf-8");//可以上传中文
        cmr.setResolveLazily(true);//设置延迟加载，以便获取大小不合适产生的错误
        cmr.setMaxInMemorySize(1024*1024*10);//设置上传的缓存的大小
        cmr.setMaxUploadSize(1024*1024*10);//设置最大的上传的大小
        cmr.setMaxUploadSizePerFile(1024*1024);//上传单个的大小
        if(cmr.isMultipart(request)){//是流的形式也就是图片之类的
            MultipartHttpServletRequest req=cmr.resolveMultipart(request);//设置成流的形式
            MultipartFile file=req.getFile("imgFile");//上传组件的名字
            System.out.println(file+"imgFile======="+file.getName());
            //图片的类型
            String picType=types.get(file.getContentType());
            String dir=req.getParameter("dir");//得到文件存在于那个子目录
            String id= UUID.randomUUID().toString();
            //得到该项目的src目录
            String src=KindControl.class.getClassLoader().getResource("").getPath();
            String newFileName=src+"static/editor/upload/"+dir+"/"+id+picType;
            System.out.println("newFileName======"+newFileName);
            newFileName=newFileName.substring(1);
            System.out.println("newFileName======"+newFileName);
            File  file1=new File(newFileName);
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
             String url=req.getRequestURL().toString();//获取http://localhost:8080/kindupload路径
            url=url.substring(0,url.lastIndexOf("/"));//把8080后边的过滤掉
            url=url+"/editor/upload/"+dir;//改成我自己设计的路径
            JSONObject json=new JSONObject();//通过JSON传到前端页
            json.put("error",0);
            json.put("url" ,url+"/"+id+picType);
            return json.toJSONString();

        }
        return "";
    }*/
}
