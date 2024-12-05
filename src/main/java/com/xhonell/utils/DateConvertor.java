package com.xhonell.utils;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * convertor
 * User: hly
 * Date: 2024/04/30 11:40
 * Description:   转换器     把String 日期转换为 Date
 * Version: V1.0
 */
public class DateConvertor  implements Converter {

    public Object convert(Class aClass, Object value) {
         if(value instanceof  String[] && value!=null  && ((String[]) value).length==1){
             String  time=((String[]) value)[0];
             SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
             try {
                 Date date=simpleDateFormat.parse(time);
                 return  date;
             } catch (ParseException e) {
                 throw new RuntimeException(e);
             }
         }
         return  null;
    }
}
