package com.xhonell.utils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * org.example.t2.DBHelper
 * User: hly
 * Date: 2024/11/07 16:07
 * motto:   逆水行舟不进则退
 * Description:
 * Version: v1.0
 */
public class DBHelper {
    JDBCUtils  jdbcUtils=new JDBCUtils();

    /**
     * 修改 包含新增  删除 修改
     * @param sql
     * @param parameters
     * @return
     */
    public int update(String  sql,Object ...parameters){
        return  jdbcUtils.update(sql,parameters);
    }

    /**
     * 查询单条数据
     * @param cl   想要返回的类型
     * @param sql    查询的slq语句
     * @param parameters  sql参数
     * @return    返回对象
     * @param <T>  泛型 Student  User   Course 可以是任意类型
     */
    public  <T> T getBean(Class<T> cl,String  sql,Object ...parameters){
        List<Map<String, Object>> select = jdbcUtils.select(sql, parameters);
        if(select.size()==1){
            return  getBean(cl,select.get(0));
        }
        return null;
    }

    /**
     *  查询多条
     * @param cl  想要返回的类型
     * @param sql  查询的slq语句
     * @param parameters  sql参数
     * @return  返回集合对象
     * @param <T>  泛型 Student  User   Course 可以是任意类型
     */
    public   <T> List<T> getBeanList(Class<T> cl,String  sql,Object ...parameters){
        List<Map<String, Object>> select = jdbcUtils.select(sql, parameters);
        return  getBeanList(cl,select);
    }

    /**
     *   把数据进行转换 转换单条数据
     * @param cl  类型
     * @param row  这一行的数据
     * @return  返回这个对象
     * @param <T>  泛型类型
     */
    private <T> T getBean(Class<T> cl, Map<String,Object> row){
        //创建对象
        T   t=null;
        try {
            t=cl.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("== 没有无参构造器 或者没有权限");
        }
        if(t!=null) {
            //获得所有属性
            Field[] declaredFields = cl.getDeclaredFields();
            //循环所有字段
            for (Field field : declaredFields) {
                //获取属性名  就是 数据库字段名字  map里面key  map里面的key装数据库字段名字
                String fieldName = field.getName();
                //找出字段对应数值
                if (row.containsKey(fieldName)) {
                    Object fieldValue = row.get(fieldName);
                    //给属性赋值
                    field.setAccessible(true);
                    try {
                        field.set(t, fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return  t;
    }

    /**
     *   转换多条数据  把数据库查询的数据进行转换成 实体类型
     * @param cl  实体的类型
     * @param rowList   数据库查询的数据
     * @return
     * @param <T>
     */
    private  <T> List<T> getBeanList(Class<T> cl, List<Map<String,Object>> rowList){
        List<T>  list=new ArrayList<>();
        for (Map<String, Object> map : rowList) {
            T  t=getBean(cl,map);
            if(t!=null)
                list.add(t);
        }
        return  list;
    }


}
