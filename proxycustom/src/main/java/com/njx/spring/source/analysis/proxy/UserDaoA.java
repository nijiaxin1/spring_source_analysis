package com.njx.spring.source.analysis.proxy;

import com.njx.spring.source.analysis.dao.UserDaoImpl;

public class UserDaoA extends UserDaoImpl {
    @Override
    public void query() {
        System.out.println("静态代理之继承");
    }
}
