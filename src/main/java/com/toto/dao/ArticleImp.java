package com.toto.dao;

import com.toto.po.Article;
import com.toto.po.Bbsuser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
@Repository//dao层实现类使用
public class ArticleImp {
    @PersistenceContext//用于将数据库里的数据提出来 然后再存进去  存储过程
    private EntityManager entityManager;//联合存储过程
    public Map<String,Object> queryContie(Integer id){
        StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("a_p");
        storedProcedureQuery.registerStoredProcedureParameter("in_id",Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("out_title",String.class,ParameterMode.OUT);
        storedProcedureQuery.setParameter("in_id",id);
        storedProcedureQuery.execute();
        Map<String,Object> map=new HashMap<>();
        List<Object[]> os=storedProcedureQuery.getResultList();
        List<Article> list=new ArrayList<>();
        for(Object[] o:os){
            Article  a=new Article();
            a.setId(Integer.parseInt(o[0].toString()));
            a.setRootid(Integer.parseInt(o[1].toString()));
            a.setTitle(o[2].toString());
            a.setContent(o[3].toString());
            Bbsuser user=new Bbsuser();
            user.setUserid(Integer.parseInt(o[4].toString()));
            a.setBbsuser(user);
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                a.setDatetime(new Date(sf.parse(o[5].toString()).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
           list.add(a);
        }
        String title=storedProcedureQuery.getOutputParameterValue("out_title").toString();
        map.put("list",list);
        map.put("title",title);
        return map;
    }


}
