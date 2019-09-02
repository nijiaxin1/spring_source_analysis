package com.njx.spring.source.analysis.proxy1;

import com.njx.spring.source.analysis.dao.UserDao;

public class UserDaoB implements UserDao {
    UserDao userDao;

    public UserDaoB(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void query() {
        System.out.println("聚合");
        userDao.query();
    }
}
