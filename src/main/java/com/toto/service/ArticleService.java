package com.toto.service;

import com.alibaba.fastjson.JSONObject;
import com.toto.dao.ArticleImp;
import com.toto.dao.InArticle;
import com.toto.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    InArticle dao;

    @Autowired
    ArticleImp adao;
    public Page<Article> findAll(Pageable pageable, Integer rootid){
        return dao.findAll(pageable,rootid);
    }
    public void delete( Integer id , Integer rootid){
        dao.delete(id,rootid);
    }

    public String queryContie(Integer id){
        return JSONObject.toJSONString(adao.queryContie(id));
    }
    public  Article  save(Article article){
        return dao.save(article);
    }

    public void deletect( Integer id ){
        dao.deletect(id);
    }

}
