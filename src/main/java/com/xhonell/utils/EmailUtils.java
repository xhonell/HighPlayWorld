package com.xhonell.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * <p>Project:test_01 - EmailUtils
 * <p>POWER by xhonell on 2024-11-27 08:42
 * <p>description：
 * <p>idea：
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */public class EmailUtils {
    /**
     * 发件⼈邮箱密码 - 登录邮件开启 SMTP 服务后，邮件服务商⽣成的“授权码”
     */
    public static final String authorizeCode = "bhpmdfatdqgibdfd";  //这个账号的的授权码

    /**
     * 发件⼈SMTP服务器地址，⼀般的格式为：smtp.xxx.com，其中xxx为邮件服务商名称
     */
    public static final String smtpHost = "smtp.qq.com";

    public  static  final  String  sendEmail="549847214@qq.com";//自己或公司 邮箱账号
    public  static  final  String  sendName="犇腾文化有限科技";//自己或公司 邮箱账号

    /**
     * 协议
     */
    public static final String protocol = "smtp";

    //发送邮件代码
    public static boolean sendAuthCodeEmail(String email,String title,String context) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器
            mail.setAuthentication(sendEmail,authorizeCode);//刚刚记录的授权码，是开启SMTP的密码
            mail.setFrom(sendEmail);  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            //System.out.println("email"+email);
            mail.setSubject(title);//设置邮件的主题
            mail.setMsg("您好，" +
                    "\n感谢您注册 [犇腾文化有限应用]。为了完成邮箱验证，请使用以下验证码：" +
                    "\n验证码： "+context+
                    "\n验证码有效期为10分钟，请尽快输入并完成验证。" +
                    "\n如果您没有发起此操作，请忽略此邮件。" +
                    "\n祝您使用愉快！" +
                    "\n此邮件由系统自动发出，请勿回复。");//设置邮件的内容
                    mail.send();//发送
            return   true;
        } catch (EmailException e) {
            e.printStackTrace();
            return  false;
        }
    }

}
