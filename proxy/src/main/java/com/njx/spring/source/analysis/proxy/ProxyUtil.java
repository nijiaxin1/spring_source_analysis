package com.njx.spring.source.analysis.proxy;


import com.njx.spring.source.analysis.dao.Dao;
import com.njx.spring.source.analysis.dao.IndexDao;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyUtil {

    public static Object newInstance(Object target) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class targetInf = target.getClass().getInterfaces()[0];

        //获取类名
        String infName = targetInf.getSimpleName();
        Method[] methods = targetInf.getDeclaredMethods();

        StringBuilder sb = new StringBuilder();

        //package com.google;
        sb.append("package com.njx.spring.source.analysis.proxy;").append(System.lineSeparator());
        //import com.xx.xx;
        sb.append("import ").append(targetInf.getName()).append(";").append(System.lineSeparator());
        // public class $proxy implements infName {
        sb.append("public class $Proxy implements ").append(infName).append("{").append(System.lineSeparator());
        //private infName dao;
        sb.append("private ").append(infName).append(" target;").append(System.lineSeparator());
        //public $Proxy(infName target){this.target = target}
        sb.append(" public $Proxy ( ").append(infName).append(" target){").append(System.lineSeparator());
        sb.append("this.target=target;").append(System.lineSeparator());
        sb.append("}").append(System.lineSeparator());

        for (Method method : methods) {
            /**
             * public void query(int i, int j){
             *      target,query(i, j);
             * }
             */
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            int flag = 0;
            sb.append(" public ").append(returnTypeName).append(" ").append(methodName).append(" ( ");
            for (Class parameterType : parameterTypes) {
                String parameterTypeSimpleName = parameterType.getSimpleName();
                sb.append(parameterTypeSimpleName).append(" ").append(parameterTypeSimpleName).append(flag++).append(" ,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" ){ ").append(System.lineSeparator());
            sb.append("System.out.println(\"").append("log").append("\"); ").append(System.lineSeparator());
            sb.append("target.").append(methodName).append("( ");
            flag = 0;
            for (Class parameterType : parameterTypes) {
                String parameterTypeSimpleName = parameterType.getSimpleName();
                sb.append(parameterTypeSimpleName).append(flag++).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(");").append(System.lineSeparator());
            sb.append("}").append(System.lineSeparator());
        }
        sb.append("}").append(System.lineSeparator());
        System.out.println(sb.toString());

        File file = new File("C:\\workspace\\source\\spring_source_analysis\\proxy\\src\\main\\java\\com\\njx\\spring\\source\\analysis\\proxy\\$Proxy.java");
        if (!file.exists()) {
            try {
                file.createNewFile();

                FileWriter fw = new FileWriter(file);

                fw.write(sb.toString());

                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //.class
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable units = standardFileManager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, units);
        task.call();
        standardFileManager.close();

        URL[] urls = new URL[]{new URL("file:C:\\\\")};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class<?> clazz = urlClassLoader.loadClass("com.njx.spring.source.analysis.proxy.$Proxy");
        Constructor constructor = clazz.getConstructor(targetInf);
        Object proxy = constructor.newInstance(target);

        return proxy;
    }

    public static void main(String[] args) {
        try {
            Dao o = (Dao) newInstance(new IndexDao());
            o.query(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
