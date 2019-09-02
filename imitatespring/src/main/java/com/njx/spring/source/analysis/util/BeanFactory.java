package com.njx.spring.source.analysis.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    Map<String, Object> map = new HashMap();

    public BeanFactory(String xml) {
        parseXml(xml);
    }

    public void parseXml(String xml) {
        File file = new File(this.getClass().getResource("/").getPath() + "//" + xml);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();
            Attribute attribute = rootElement.attribute("default-autowire");
            boolean flag = false;
            if (attribute == null) {
                flag = true;
            }

            for (Iterator<Element> it = rootElement.elementIterator(); it.hasNext(); ) {
                Element elementFirstChild = it.next();
                Attribute idAttribute = elementFirstChild.attribute("id");
                String beanName = idAttribute.getValue();

                Attribute classAttribute = elementFirstChild.attribute("class");
                String className = classAttribute.getValue();

                Class clazz = Class.forName(className);

                /**
                 * 判断是否有属性
                 */
                Object object = null;
                for (Iterator<Element> itSecond = elementFirstChild.elementIterator(); itSecond.hasNext(); ) {
                    //获取ref value
                    //value获取对象
                    //
                    Element elementSecondChild = itSecond.next();
                    if ("property".equals(elementSecondChild.getName())) {
                        object = clazz.newInstance();
                        String refValue = elementSecondChild.attribute("ref").getValue();
                        Object injectObject = map.get(refValue);
                        String nameValue = elementSecondChild.attribute("name").getValue();

                        Field field = clazz.getDeclaredField(nameValue);
                        field.setAccessible(true);
                        field.set(object, injectObject);
                    } else {
                        String refValue = elementSecondChild.attribute("ref").getValue();
                        Object injectObject = map.get(refValue);
                        Class injectObjectClass = injectObject.getClass();
                        Constructor constructor = clazz.getConstructor(injectObjectClass.getInterfaces()[0]);
                        object = constructor.newInstance(injectObject);
                    }

                }
                if (!flag) {
                    if (attribute.getValue().equals("byType")) {
                        Field[] fileds = clazz.getDeclaredFields();
                        for (Field field : fileds) {
                            //获取属性Type
                            Class injectObjectClass = field.getType();
                            /**
                             * bytype,遍历map中所有的对象
                             * 如果相同就注入
                             */
                            int count = 0;
                            Object injectObject = null;
                            for (String key : map.keySet()) {
                                Class temp = map.get(key).getClass().getInterfaces()[0];
                                if (temp.getName().equals(injectObjectClass.getName())) {
                                    //找到多个
                                    injectObject = map.get(key);
                                    count++;
                                }
                            }
                            if (count > 1) {
                                throw new MyException("需要一个对象，找到两个对象！！");
                            } else {
                                object = clazz.newInstance();
                                field.setAccessible(true);
                                field.set(object, injectObject);
                            }

                        }

                    }
                }

                if (object == null) {
                    object = clazz.newInstance();
                }
                map.put(beanName, object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object getBean(String beanName) {
        return map.get(beanName);
    }
}
