package com.njx.spring.source.analysis;

import com.njx.spring.source.analysis.entity.CityEntity;
import com.njx.spring.source.analysis.util.CommUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        CityEntity cityEntity = new CityEntity();
        String value = CommUtil.parseAnno(cityEntity);
        System.out.println(value);
    }
}
