package com.schoolrecruit.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String subject, String content) throws MailException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(from); // 邮件发送者
                    message.setTo(to); // 邮件接受者
                    message.setSubject(subject); // 主题
                    message.setText(content); // 内容
                    mailSender.send(message);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
