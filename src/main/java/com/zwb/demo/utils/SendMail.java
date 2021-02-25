package com.zwb.demo.utils;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author zhouw
 * @title: SendMail
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/2/2516:07
 */
public class SendMail {
    static Logger log = Logger.getLogger("这个是测试发邮件的");

    public SendMail() {
    }

    public static void send(String HOST, String nick, String FROM, String PORT, String ssl, String USER, String PWD, String SUBJECT, ArrayList TOS, String context) {
        Session session = createSession(HOST, PORT, ssl);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(new String(nick.getBytes(), "UTF-8") + " <" + FROM + ">"));
            InternetAddress[] sendTo = new InternetAddress[TOS.size()];

            for(int i = 0; i < TOS.size(); ++i) {
                sendTo[i] = new InternetAddress(TOS.get(i).toString());
            }

            message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            message.addRecipients(Message.RecipientType.TO, sendTo);
            message.setSubject(SUBJECT, "UTF-8");
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(context);
            multipart.addBodyPart(contentPart);
            message.setContent(context, "text/html;charset=UTF-8");
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            log.info("------------发邮件----------");
            transport.connect(HOST, Integer.parseInt(PORT), USER, PWD);
            transport.sendMessage(message, message.getAllRecipients());
            log.info("------------已经发送成功----------");
            transport.close();
        } catch (Exception var16) {
            log.info("邮箱推送异常：" + var16);
            var16.printStackTrace();
            throw new RuntimeException(var16);
        }
    }

    public static Session createSession(String HOST, String PORT, String ssl) {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.enable", ssl);
        props.put("mail.smtp.auth", "true");
        log.info("发邮件的参数：" + props.toString());
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        return session;
    }

    public static void sendFile(String HOST, String nick, String FROM, String PORT, String ssl, String USER, String PWD, String SUBJECT, ArrayList TOS, ArrayList CCList, String context, Map<String, byte[]> byteFileName) {
        Session session = createSession(HOST, PORT, ssl);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(new String(nick.getBytes(), "UTF-8") + " <" + FROM + ">"));
            InternetAddress[] sendTo = new InternetAddress[TOS.size()];

            for(int i = 0; i < TOS.size(); ++i) {
                sendTo[i] = new InternetAddress(TOS.get(i).toString());
            }

            message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            message.addRecipients(Message.RecipientType.TO, sendTo);
            if (CCList != null) {
                InternetAddress[] ccTo = new InternetAddress[CCList.size()];

                for(int i = 0; i < CCList.size(); ++i) {
                    ccTo[i] = new InternetAddress(CCList.get(i).toString());
                }

                message.addRecipients(Message.RecipientType.CC, ccTo);
            }

            message.setSubject(SUBJECT, "UTF-8");
            MimeMultipart multipart = new MimeMultipart();
            new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(context, "text/html;charset=UTF-8");
            MimeBodyPart attachment = new MimeBodyPart();
            if (byteFileName != null) {
                byteFileName.forEach((k, v) -> {
                    DataHandler doc = new DataHandler(new ByteArrayDataSource(v, "application/octet-stream"));

                    try {
                        attachment.setDataHandler(doc);
                        attachment.setFileName(MimeUtility.encodeText(k));
                        multipart.addBodyPart(attachment);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }

                });
            }

            multipart.addBodyPart(contentPart);
            multipart.setSubType("mixed");
            message.setContent(multipart);
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception var20) {
            var20.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        String HOST = "smtp.greaconsulting.com";
        String FROM = "knclms@greaconsulting.com";
        String PORT = "465";
        String SSL = "true";
        String USER = "knclms@greaconsulting.com";
        String PWD = "Knclms2020";
        String nick = "KN CLMS SYSTEM";
        String SUBJECT = "测试发送附件";
        String content = "<h1>RT</h1>";
        ArrayList TOS = new ArrayList();
        TOS.add("august.zhou@greaconsulting.com");
        ArrayList CCList = new ArrayList();
        CCList.add("august.zhou@greaconsulting.com");
        send(HOST, nick, FROM, PORT, SSL, USER, PWD, SUBJECT, TOS, content);
    }
}
