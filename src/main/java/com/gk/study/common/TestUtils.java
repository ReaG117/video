package com.gk.study.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TestUtils {

    public static List<Class<?>> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

    public static String convert(String camelCase) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);

            // 大写字母转小写并在其前面加下划线，除非是第一个字符
            if (Character.isUpperCase(currentChar)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String packageName = "com.gk.study.entity"; // 替换为你的包名

        List<Class<?>> classes = getClasses(packageName);
        for (Class<?> aClass : classes) {
            String sql = "create table if not exists [table](\n[filed]\n);";
            TableName annotation = aClass.getAnnotation(TableName.class);
            String table_name = "";
            if (annotation != null) {
                table_name = annotation.value();
                sql = sql.replace("[table]", table_name);

            }
            Field[] declaredFields = aClass.getDeclaredFields();
            //            获取类中所有的私有属性
            StringBuilder filed_sql = new StringBuilder();
            for (Field declaredField : declaredFields) {
                String filed_name = declaredField.getName();

                TableField tableFieldAnnotation = declaredField.getAnnotation(TableField.class);
                TableId idAnnotation = declaredField.getAnnotation(TableId.class);
                // 判断是否存在@TableField注解，并检查exist属性的值
                if (idAnnotation != null) {
//                    System.out.println(declaredField.getType());
                    filed_sql.append("\t").append(filed_name).append(" BIGINT PRIMARY KEY AUTO_INCREMENT").append(",\n");
                }
                if (tableFieldAnnotation != null && tableFieldAnnotation.exist()) {
                    String type_name = declaredField.getType().getSimpleName();

//                    if (type_name.equalsIgnoreCase("String") && )
                    if (type_name.equalsIgnoreCase("String")) {

                        filed_sql.append("\t").append(convert(filed_name)).append(" VARCHAR(255)  DEFAULT '',\n");


                    } else if (type_name.equalsIgnoreCase("Long")) {

                        filed_sql.append("\t").append(convert(filed_name)).append(" BIGINT ").append(",\n");
                    } else if (type_name.equalsIgnoreCase("Integer")) {
                        filed_sql.append("\t").append(convert(filed_name)).append("  INT").append(",\n");
                    }

                }

            }
            filed_sql.replace(filed_sql.lastIndexOf(","), filed_sql.lastIndexOf(",") + 1, "");
            System.out.println(sql.replace("[filed]", filed_sql));

        }
    }
}
