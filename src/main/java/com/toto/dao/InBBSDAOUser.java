package com.toto.dao;

import com.toto.po.Bbsuser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public interface InBBSDAOUser extends CrudRepository<Bbsuser,Integer>{
    @Query("select c from Bbsuser  c where username=:u and password=:p")
    Bbsuser login(@Param(value = "u") String username, @Param(value = "p") String password);

    Bbsuser save(Bbsuser user);

    @Query("select c from Bbsuser c where userid=:id")
    Bbsuser findpicById(@Param(value = "id") Integer userid);

    @Query("select c from Bbsuser c where username=:un")
    Bbsuser checkUser(@Param("un") String username);



}
