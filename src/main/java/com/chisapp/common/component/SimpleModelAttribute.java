package com.chisapp.common.component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: Tandy
 * @Date: 2019/12/28 14:43
 * @Version 1.0
 */
public class SimpleModelAttribute {

    /**
     * 将 源Bean 中属性的值 对 目标Bean 中对应属性的值进行更新
     * <p>
     * 获得getter方法
     * Method m = model.getClass().getMethod("get"+name);
     * <p>
     * 执行getter方法
     * String value = (String) m.invoke(model);
     * <p>
     * 获得setter方法
     * 如果是带参数的setter方法,把参数的类型做封装成一个Class<?>泛型数组传入getMethod方法的第二个参数
     * 例如参数是String类型的setter方法如下
     * Method m = model.getClass().getMethod("set"+name, new Class[] {String.class});
     * <p>
     * 执行setter方法
     * m.invoke(model,new Object[] {new String("new value")});
     *
     * @param source
     * @param target
     */
    public synchronized static void updateValues(Map<String, Object> source, Object target) {
        Class targetClass = target.getClass();
        Field[] targetFields = targetClass.getDeclaredFields();

        for (int i = 0; i < targetFields.length; i++) {
            Field targetField = targetFields[i];
            String targetFieldName = targetField.getName();
            Object sourceVal = source.get(targetFieldName);

            if (sourceVal == null) {
                continue;
            }

            String targetFieldTypeName = targetField.getGenericType().getTypeName();
            String methodName = "set" + targetFieldName.substring(0, 1).toUpperCase() + targetFieldName.substring(1);
            Method method;

            try {
                switch (targetFieldTypeName) {
                    case "java.lang.String": {
                        method = targetClass.getMethod(methodName, String.class);
                        method.invoke(target, (String) sourceVal);
                    }
                    break;
                    case "java.lang.Character": {
                        method = targetClass.getMethod(methodName, Character.class);
                        method.invoke(target, (Character) sourceVal);
                    }
                    break;
                    case "java.lang.Long": {
                        try {
                            Long.parseLong(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Long.class);
                        method.invoke(target, (Long) sourceVal);
                    }
                    break;
                    case "java.lang.Integer": {
                        try {
                            Integer.parseInt(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Integer.class);
                        method.invoke(target, (Integer) sourceVal);
                    }
                    break;
                    case "java.lang.Short": {
                        try {
                            Short.parseShort(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Short.class);
                        method.invoke(target, (Short) sourceVal);
                    }
                    break;
                    case "java.lang.Byte": {
                        try {
                            Byte.parseByte(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Byte.class);
                        method.invoke(target, (Byte) sourceVal);
                    }
                    break;
                    case "java.lang.Double": {
                        try {
                            Double.parseDouble(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Double.class);
                        method.invoke(target, (Double) sourceVal);
                    }
                    break;
                    case "java.lang.Float": {
                        try {
                            Float.parseFloat(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Float.class);
                        method.invoke(target, (Float) sourceVal);
                    }
                    break;
                    case "java.lang.Boolean": {
                        try {
                            Boolean.parseBoolean(sourceVal.toString());
                        } catch (Exception e) {
                            sourceVal = null;
                        }
                        method = targetClass.getMethod(methodName, Boolean.class);
                        method.invoke(target, (Boolean) sourceVal);
                    }
                    break;
                    case "java.util.Date": {
                        // yyyy-MM-dd HH:mm:ss
                        String timeRegex1 = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
                        // yyyy-MM-dd
                        String timeRegex2 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|" +
                                "((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|" +
                                "((0[48]|[2468][048]|[3579][26])00))-02-29)$";
                        SimpleDateFormat dateFormat = null;
                        if (Pattern.matches(timeRegex1, sourceVal.toString())) {
                            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        }
                        if (Pattern.matches(timeRegex2, sourceVal.toString())) {
                            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        }

                        method = targetClass.getMethod(methodName, Date.class);
                        method.invoke(target, dateFormat.parse(sourceVal.toString()));
                    }
                    break;
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("SimpleModelAttribute updateValues error:NoSuchMethodException");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("SimpleModelAttribute updateValues error:IllegalAccessException");
            } catch (InvocationTargetException e) {
                throw new RuntimeException("SimpleModelAttribute updateValues error:InvocationTargetException");
            } catch (ParseException e) {
                throw new RuntimeException("SimpleModelAttribute updateValues error:ParseException");
            }

        }
    }


}
