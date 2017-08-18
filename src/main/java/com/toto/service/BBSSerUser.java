package com.toto.service;

import com.toto.dao.InBBSDAOUser;
import com.toto.po.Bbsuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Service
@Transactional
public class BBSSerUser {
    @Autowired
    InBBSDAOUser dao;
    public Bbsuser login(String username, String password){
        return dao.login(username,password);
    }
    private Map<String,String> map=new HashMap<>();
    public BBSSerUser(){
        map.put("image/jpeg",".jpg");
        map.put("image/gif",".gif");
        map.put("image/x-ms-bmp",".bmp");
        map.put("image/png",".png");
    }
    public Bbsuser uploadpic(HttpServletRequest request){
        //Spring提供的上传文件的半成品
        CommonsMultipartResolver cmr=new CommonsMultipartResolver(request.getSession().getServletContext());
        cmr.setDefaultEncoding("utf-8");//防止上传的图片名有中文会出现乱码
        //设置上传文件的大小
        cmr.setMaxInMemorySize(1024*1024*10);
        cmr.setMaxUploadSizePerFile(1024*1024);
        cmr.setMaxUploadSize(1024*1024*10);

        MultipartHttpServletRequest mhsr=cmr.resolveMultipart(request);//把request转换成流的形式
        MultipartFile mf= mhsr.getFile("file0");
        System.out.println(mf.getOriginalFilename());
        String filename=mf.getOriginalFilename();
        File file=new File("uploadpic"+File.separator+filename);
        String picPath="uploadpic"+File.separator+filename;
        Bbsuser user=new Bbsuser();
        try {

            mf.transferTo(file);
            String username=mhsr.getParameter("reusername");
            String password=mhsr.getParameter("repassword");
            user.setUsername(username);
            user.setPassword(password);
            user.setPicPath(picPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
    public  Bbsuser save(Bbsuser user){
        return dao.save(user);
    }
    public   Bbsuser findpicById(@Param(value = "id") int userid){
       return  dao.findpicById(userid);
    }
    public Bbsuser checkUser(@Param("un") String username){
        return dao.checkUser(username);
    }

}
