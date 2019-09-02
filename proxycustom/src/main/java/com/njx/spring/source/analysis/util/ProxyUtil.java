package com.njx.spring.source.analysis.util;

import java.lang.reflect.Method;

public class ProxyUtil {
    /**
     * .java
     * .class
     * .new
     *
     * @return
     */
    public static String NEW_LINE = System.getProperty("line.separator");

    public static Object newInstance(Class targetInf) {
        String infName = targetInf.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("package com.google;").append(NEW_LINE);
        sb.append("import ").append(targetInf.getName()).append(";").append(NEW_LINE);
        sb.append("public class $Proxy implements ").append(infName).append(" {").append(NEW_LINE);
        sb.append("private ").append(infName).append(" dao;").append(NEW_LINE);
        sb.append("public $Proxy(").append(infName).append(" target){").append(NEW_LINE);
        sb.append("this.target=target;").append(NEW_LINE);
        sb.append("}").append(NEW_LINE);

        for (Method method : targetInf.getMethods()) {
            String returnTypeName = method.getReturnType().getSimpleName();

            String methodName = method.getName();

            Class<?>[] args = method.getParameterTypes();

            int flag = 0;

            sb.append("public ").append(returnTypeName).append(" ").append(methodName).append("( ").append(NEW_LINE);

            for (Class arg : args) {
                sb.append(arg.getSimpleName()).append(" ").append(flag++).append(", ");
            }
            sb.substring(0, sb.length());
            sb.append("){").append(NEW_LINE);

        }


        return null;
    }
}
