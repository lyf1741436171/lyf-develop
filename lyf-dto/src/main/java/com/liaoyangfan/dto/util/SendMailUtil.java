package com.liaoyangfan.dto.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述: 邮件工具类
 * @version 1.0
 * @date 2019/3/4  14:40
 * JDK版本：sun jdk 1.8
 */
public class SendMailUtil {
    private String region;
    private String accessKeyId;
    private String accessKeySecret;

    private String accountName;
    private String tagName;
    private String alias;

    Logger logger = LoggerFactory.getLogger(SendMailUtil.class);

    public SendMailUtil() {
    }

    public SendMailUtil(String region, String accessKeyId, String accessKeySecret, String accountName, String tagName, String alias) {
        this.region = region;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.accountName = accountName;
        this.tagName = tagName;
        this.alias = alias;
    }

    private Session loadMailSession(String smtp_host, String fromEmail, String sendPasswd) {
        try {
            // 配置发送邮件的环境属性
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", smtp_host);
            // props.put("mail.smtp.port", ALIDM_SMTP_PORT);
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.port", "465");
            // 发件人的账号
            props.put("mail.user", fromEmail);
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", sendPasswd);
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            return Session.getInstance(props, authenticator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送邮件 带附件， 使用SMTP指定账号来发送，容易被封
     *
     * @param toEmail    收件人邮箱地址
     * @param subject    邮件标题
     * @param content    邮件内容 可以是html内容
     * @param attachPath 附件路径
     */
    public void sendByAttachment(String smtp_host, String fromEmail, String sendPasswd,
                                 String toEmail, String subject, String content, String attachPath) {
        Session session = loadMailSession(smtp_host, fromEmail, sendPasswd);
        MimeMessage mm = new MimeMessage(session);
        try {
            //发件人
            mm.setFrom(new InternetAddress(fromEmail));
            //收件人
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // 设置收件人
            //标题
            mm.setSubject(subject);
            //内容
            Multipart multipart = new MimeMultipart();
            //body部分
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            //附件部分
            BodyPart attachPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(attachPath);
            attachPart.setDataHandler(new DataHandler(fileDataSource));
            attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
            multipart.addBodyPart(attachPart);
            mm.setContent(multipart);

            Transport.send(mm);
        } catch (Exception e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }
    }

    /**
     * 发送邮件 带多个附件， 使用SMTP指定账号来发送
     *
     * @param toEmail     收件人邮箱地址
     * @param subject     邮件标题
     * @param contentMap  邮件内容 可以是html内容
     * @param attachments 多个附件路径
     */
    public boolean sendByAttachments(String smtp_host, String fromEmail, String sendPasswd,
                                     String toEmail, String subject, Map<String, String> contentMap, List<MultipartFile> attachments) {
        Session session = loadMailSession(smtp_host, fromEmail, sendPasswd);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("session时间-------------" + formatter.format(new Date()) + "-------------");
        MimeMessage mm = new MimeMessage(session);
        try {
            //发件人
            mm.setFrom(new InternetAddress(fromEmail));
            //收件人
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // 设置收件人
            //标题
            mm.setSubject(subject);
            //内容
            Multipart multipart = new MimeMultipart();
            //body部分
            BodyPart contentPart = new MimeBodyPart();
            StringBuffer content = new StringBuffer();
            content.append("反馈时间: " + contentMap.get("time") + " \n");
            content.append("反馈人: " + contentMap.get("name") + " \n");
            content.append("联系方式: " + contentMap.get("phoneNumber") + " \n");
            content.append("操作系统: " + contentMap.get("system") + " \n");
            content.append("手机型号: " + contentMap.get("phoneModel") + " \n");
            content.append("版本号: " + contentMap.get("version") + " \n");
            content.append("反馈内容: " + contentMap.get("content") + " \n");
            contentPart.setText(content.toString());
            multipart.addBodyPart(contentPart);
            logger.info("邮件内容时间-------------" + formatter.format(new Date()) + "-------------");
            //附件部分
            if (attachments != null && attachments.size() > 0) {
                for (MultipartFile attachment : attachments) {
                    BodyPart attachmentPart = new MimeBodyPart();
                    DataSource source = new DataSource() {
                        @Override
                        public InputStream getInputStream() throws IOException {
                            if (attachment != null) {
                                return attachment.getInputStream();
                            }
                            return null;
                        }

                        @Override
                        public OutputStream getOutputStream() throws IOException {
                            return null;
                        }

                        @Override
                        public String getContentType() {
                            if (attachment != null) {
                                return attachment.getContentType();
                            }
                            return null;
                        }

                        @Override
                        public String getName() {
                            if (attachment != null) {
                                return attachment.getOriginalFilename();
                            }
                            return null;
                        }
                    };
                    attachmentPart.setDataHandler(new DataHandler(source));
                    //避免中文乱码的处理
                    String fileName = attachment.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    attachmentPart.setFileName(MimeUtility.encodeWord(UUID.randomUUID().toString() + prefix));
                    multipart.addBodyPart(attachmentPart);
                }
            }
            logger.info("邮件附件时间-------------" + formatter.format(new Date()) + "-------------");
            mm.setContent(multipart);
            //保存并且生成邮件对象
            mm.saveChanges();

            //建立发送邮件的对象
            Transport sender = session.getTransport("smtp");
            sender.connect();
            sender.sendMessage(mm, mm.getRecipients(Message.RecipientType.TO));
            sender.close();
            logger.info("发送邮件时间-------------" + formatter.format(new Date()) + "-------------");
            logger.info("send finish: 发送邮件成功");
            return true;
        } catch (Exception e) {
            logger.info("send finish: 发送邮件失败" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) throws AddressException, MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("1741436171@qq.com"));
        // 设置收件人邮箱地址
        // message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("liaoyangfan@freedotech.com")});
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("liaoyangfan@freedotech.com"));//一个收件人
        // 设置邮件标题
        message.setSubject("xmqtest");
        // 设置邮件内容
        message.setText("邮件内容邮件内容邮件内容xmqtest");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect("1741436171@qq.com", "clfhryebvfdbcgci");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }


}
