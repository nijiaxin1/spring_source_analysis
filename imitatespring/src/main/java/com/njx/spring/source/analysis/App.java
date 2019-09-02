package com.njx.spring.source.analysis;

import com.njx.spring.source.analysis.service.UserService;
import com.njx.spring.source.analysis.util.BeanFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        BeanFactory beanFactory = new BeanFactory("spring.xml");
        UserService service = (UserService) beanFactory.getBean("service");
        service.query();
    }
}
