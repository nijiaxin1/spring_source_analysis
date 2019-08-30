package com.njx.spring.source.analysis;

import com.njx.spring.source.analysis.config.AopConfig;
import com.njx.spring.source.analysis.dao.IndexDao;
import com.njx.spring.source.analysis.entity.CityEntity;
import com.njx.spring.source.analysis.util.CommUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

//        CityEntity cityEntity = new CityEntity();
//        String value = CommUtil.parseAnno(cityEntity);
//        System.out.println(value);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);

        IndexDao dao = context.getBean(IndexDao.class);
        dao.query(20);
    }
}
