package com.njx.spring.source.analysis.dao;

import com.njx.spring.source.analysis.annotation.AopAnnotation;
import org.springframework.stereotype.Repository;

@Repository
public class IndexDao implements Dao{

    public void query(Integer args) {
        System.out.println("query");
    }

}
