package com.toto.dao;

import com.toto.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public interface InArticle extends CrudRepository<Article,Integer>{
    @Query("select c from Article c where rootid=:rootid")
    Page<Article> findAll(Pageable pageable, @Param("rootid") Integer rootid);

    @Modifying
    @Query("delete  Article  where id=:id or rootid=:rootid")
    void delete(@Param("id") Integer id ,@Param("rootid") Integer rootid);

    Article  save(Article article);



    @Modifying
    @Query("delete  Article  where id=:id ")
    void deletect(@Param("id") Integer id );
}
