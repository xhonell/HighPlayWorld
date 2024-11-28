package com.xhonell.utils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * day4.w1.FileUtils
 * User: hly
 * Date: 2024/10/21 8:58
 * motto:   逆水行舟不进则退
 * Description: 文件工具类
 *  方法可以直接被类调用
 * Version: v1.0
 */
public class FileUtils {
    private  FileUtils(){}//为了不能创建对象

    /**
     *  根据文件路径获取文件全称
     * @param path  文件的路径
     * @return  文件全称
     */
    public  static  String  getFullFileName(String path){
        //最后一个斜杠
        int index=path.lastIndexOf("/");
        //最后一个斜杠
        if(index==-1){
            index=path.lastIndexOf("\\");
        }
        if(index==-1){
            //是文件还是文件夹  ==后面有知识点再补充
            //index=path.lastIndexOf(".");
            //return  index!=-1?path:null;
            return  path;
        }
        return  path.substring(index+1);
    }

    /**
     *
     *  根据文件路径获取文件,名字
     * @param path  文件的路径
     * @return  文件名字
     */
    public  static  String  getFileName(String path){
        //全称
       String  fileName=getFullFileName(path);
       //找出最后一个点所在位置
       int index=fileName.lastIndexOf(".");
       if(index!=-1)
           return  fileName.substring(0,index);//截取
       return fileName;//返回全称
    }

    /**
     *  返回文件的后缀
     * @param path  文件路径
     * @return  后缀
     */
    public  static  String  getSuffix(String path){
        String fullFileName = getFullFileName(path);
        //找出最后一个点所在位置
        int index=fullFileName.lastIndexOf(".");
        if(index!=-1)
            return  fullFileName.substring(index);//截取
        return "";//路径
    }
    /**
     *  返回文件的后缀
     * @param file  文件对象
     * @return  后缀
     */
    public  static  String  getSuffix(File file){
        String fullFileName =file.getName();
        //找出最后一个点所在位置
        int index=fullFileName.lastIndexOf(".");
        if(index!=-1)
            return  fullFileName.substring(index);//截取
        return "";//路径
    }


    /**
     *   拷贝文件内容
     * @param src  源文件
     * @param target  目标文件
     * @return  拷贝成功
     */
    public  static  boolean  copyFile(File src, File target){
        //输入流
        InputStream  inputStream=null;
        //输出流
        OutputStream outputStream=null;
        try {
          inputStream=new FileInputStream(src);
          outputStream=new FileOutputStream(target);
          return copyFile(inputStream,outputStream);
        } catch (FileNotFoundException e) {
            return  false;
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    return  false;
                }finally {
                    if(inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            return  false;
                        }
                    }
                }
            }
        }
    }
    /**
     *   拷贝文件内容
     * @param srcPath  源文件地址
     * @param targetPath  目标文件地址
     * @return  拷贝成功
     */
    public  static  boolean  copyFile(String srcPath, String targetPath){
        return copyFile(new File(srcPath),new File(targetPath));
    }
    /**
     *   拷贝文件内容
     * @param srcInputStream  源文件输入流
     * @param targetOutputStream  目标文件输出流
     * @return  拷贝成功
     */
    public  static  boolean  copyFile(InputStream srcInputStream, OutputStream targetOutputStream){
        //得到流边读边写
        BufferedInputStream  bufferedInputStream=new BufferedInputStream(srcInputStream);
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(targetOutputStream);
        int c;
        byte  []  arr=new  byte[1024];
            try {
                while ((c=bufferedInputStream.read(arr))!=-1) {
                     bufferedOutputStream.write(arr,0,c);

                }
                bufferedOutputStream.flush();
            } catch (IOException e) {
               return  false;
            }finally {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    return  false;
                }finally {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        return  false;
                    }
                }
            }
            return  true;
    }

    /**
     *  交换文件内容
     * @param path1  文件1的路径
     * @param path2 文件2的路径
     * @return  是否交换成功 true成功  false失败
     */
    public   static   boolean  fileExchange(String path1, String path2){
      return  fileExchange(new File(path1),new File(path2));
    }
    /**
     *  交换文件内容
     * @param file1  文件对象1
     * @param file2 文件对象2
     * @return  是否交换成功 true成功  false失败
     */
    public   static   boolean  fileExchange(File file1, File file2){
        if(!getSuffix(file1).equals(getSuffix(file2))){
            //FileTypeException
          return  false;
        }
        //文件存在
        if(file1.exists() &&  file2.exists() ){
            //如果文件不在同一个目录中 可以直接交换名字
            if(file1.getAbsoluteFile().getParent().equals(file2.getAbsoluteFile().getParent())){
                 //说明同一个文件下
                //时间戳
                String newFileName=System.currentTimeMillis()+getSuffix(file1);
                //UUID 随机唯一编码
                newFileName= UUID.randomUUID().toString()+getSuffix(file1);
                //  创建新的文件  临时文件  最后删除
                File  temp=new File(file1.getParent(),newFileName);
                //  File1 ->  temp   文本1内容拷贝零时文件中
                //  file2->file1      文本2内容拷贝文本1文件中  文本1的内容被改变
                //temp -> file2         零时文件的内容拷贝到2中      (想改改变2)错误 文本1和文本2的内容一致 还远文本原来的内容
                if (copyFile(file1,temp)){
                    if(  copyFile(file2,file1)){
                        if(  copyFile(temp,file2)){
                             temp.delete();
                            return  true;
                        }else{
                            //还原文本1
                            copyFile(temp,file1);
                            return false;
                        }
                    }
                }
                return false;
            }else{
                //记录1的名字
                String  fileName1=file1.getName();
                //改1的名字                获取1的父路径        2文件的名字
                file1.renameTo(new File(file1.getParent(),file2.getName()));
                file2.renameTo(new File(file2.getParent(),fileName1));
                return  true;
            }
        }
        return  false;
    }

    /**
     * 备份
     * 2).把worker.txt 文件备份,该文件拷贝到指定位置并在文件名后加入日期信息进行备份。
     * (源文件名为worker.txt 备份名为 worker2017-8-30.txt)
     * 例如:worker1.doc 备份名为 worker12017-8-30.doc
     * 例如: worker2.png 备份名为 worker22017-8-30.png
     * @param file  源文件
     * @return   备份成功 为true 失败false
     */
    public   static   boolean  backupDate(File file){
        String suffix= getSuffix(file);
        String  fileName=file.getName().replace(suffix,"")+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+suffix;
        System.out.println(fileName);
        File file1=new File(file.getParent(),fileName);
        return  copyFile(file,file1);
    }
    public   static   boolean  backupDate(String path){
        return  backupDate(new File(path));
    }
}
