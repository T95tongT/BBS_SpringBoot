package com.toto.config;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class FreemarkerUtils {

    private  static Configuration configuration=null;


    public static Configuration  buildConfiguration(){
        if(configuration==null){
            configuration=new Configuration(Configuration.VERSION_2_3_25);
            String path=FreemarkerUtils.class.getResource("/").getPath();

            File file=new File(path+File.separator+"templates");

            configuration.setDefaultEncoding("utf-8");
            try {
                configuration.setDirectoryForTemplateLoading(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return configuration;
    }

    public static Template getTemplate(String ftlName){
        Template template= null;
        try {
            template = buildConfiguration().getTemplate(ftlName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

    public static void forward(
            HttpServletResponse response,
            String target,
            HashMap vmap
    ){
        Template template=getTemplate(target);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out=null;
        try {
            out=response.getWriter();
            template.process(vmap,out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();

    }
}
