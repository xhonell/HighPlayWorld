package com.xhonell.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * com.gxa.util.WebParameterUtils
 * User: hly
 * Date: 2024/12/02 16:28
 * motto:   逆水行舟不进则退
 * Description:
 * Version: v1.0
 */
public class WebParameterUtils {
    /**
     * 获取  json数据格式的请求数据
     * @param request 请求对象
     * @param clazz     返回的类型
     * @return
     * @param <T>
     */
    public static <T> T receiveJsonToPojo(HttpServletRequest request, Class<T> clazz){
        try {
            BufferedReader bufferedReader = request.getReader();//获取请求对象中流
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {//读取第一行 json 数据
                sb.append(line);
            }
            return  JSONObject.parseObject(sb.toString(), clazz);//转换成实体
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     *  把 json  数据格式 转换成 集合  "[{},{},{}]"
     * @param request
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> List<T> receiveJsonList(HttpServletRequest request, Class<T> clazz){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(),"UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return JSONObject.parseArray(sb.toString(), clazz);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 把request请求的参数拷贝到javabean中
     * @param request
     * @param clazz
     */
    public static <T> T copyBean(HttpServletRequest request,Class<T> clazz){
        try{
            T bean = clazz.newInstance();
            //1.获取请求参数的map集合
            Map<String, String[]> map = request.getParameterMap();
            //2.注册日期转换器
            ConvertUtils.register(new DateConvertor(), Date.class);
            //3.拷贝数据
            BeanUtils.populate(bean,map);
            return bean;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("数据拷贝失败!");
        }
    }


    /**
     * 两个javabean之间的数据拷贝
     * @param ori
     * @param clazz
     */
    public static  <T> T copBean(Object ori,Class<T> clazz){
        try{

            T bean = clazz.newInstance();

            //1.注册日期转换器
            ConvertUtils.register(new DateConvertor(), Date.class);

            //2.拷贝数据
            BeanUtils.copyProperties(bean,ori);

            return bean;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("数据拷贝失败!");
        }
    }
}
