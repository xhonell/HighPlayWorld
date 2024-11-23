package com.xhonell.utils.utils;

/**
 * day4.w1.StringUtils
 * User: hly
 * Date: 2024/10/21 9:19
 * motto:   逆水行舟不进则退
 * Description:
 * Version: v1.0
 */
public class StringUtils {

    private  StringUtils(){}

    /**
     *   是否包含数字
     * @param str  字符串
     * @return 是否包含 true包含  false不包含
     */
    public  static  boolean isContainsNumber(String  str){
        //return str.matches("[0-9]{1}");
        //把所以字符串转换为字符数组
        char  []  arr=str.toCharArray();
        //for
    //        for (int i = 0; i <arr.length ; i++) {
    //            char  c=arr[i];
            // 改变了c是改变了元素内容么?(基本数据类型)没改变  引用数据类型呢?分情况
            //Student  s=arr[i];
            // s.name="亮亮"  ;  s=new  Student()
    //            if(c>='0' && c<='9')
    //                return  true;
    //        }
        //简洁for循环
        for (char c:arr){
            //改变了c是改变了元素内容么?
            if(c>='0' && c<='9')
                  return  true;
        }
        return  false;
    }
    /**
     *   是否包含大写字母
     * @param str  字符串
     * @return 是否包含 true包含  false不包含
     */
    public  static  boolean isContainsUpperCase(String  str){
        char  []  arr=str.toCharArray();
        for (char c:arr){
            //改变了c是改变了元素内容么?
            if(c>=65 && c<='Z')
                return  true;
        }
        return  false;
    }
    /**
     *   是否包含小写字母
     * @param str  字符串
     * @return 是否包含 true包含  false不包含
     */
    public  static  boolean isContainsLowCase(String  str){
        char  []  arr=str.toCharArray();
        for (char c:arr){
            //改变了c是改变了元素内容么?
            if(c>='a' && c<='z')
                return  true;
        }
        return  false;
    }
    /**
     *   是否包含_或者$
     * @param str  字符串
     * @return 是否包含 true包含  false不包含
     */
    public  static  boolean is_$(String  str){
        char  []  arr=str.toCharArray();
        for (char c:arr){
            //改变了c是改变了元素内容么?
            if(c=='_' || c=='$')
                return  true;
        }
        return  false;
    }
    /**
     *   是否包含特殊符号
     * @param str  字符串
     * @return 是否包含 true包含  false不包含
     */
    public  static  boolean isSpecial(String  str){
        char  []  arr=str.toCharArray();
        for (char c:arr){
            //改变了c是改变了元素内容么?
            if( c<'0'|| (c>'9' && c<'A') || (c>'Z' && c<'a') || c>'z')
                return  true;
        }
        return  false;
    }

}
