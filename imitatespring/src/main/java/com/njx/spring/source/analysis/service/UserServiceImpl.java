package com.njx.spring.source.analysis.service;

import com.njx.spring.source.analysis.dao.UserDao;

public class UserServiceImpl implements UserService {

    UserDao dao;



    /*  public UserServiceImpl(UserDao dao) {
          this.dao = dao;
      }
  */

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void query() {
        System.out.println("query　service");
        dao.query();
    }
}
