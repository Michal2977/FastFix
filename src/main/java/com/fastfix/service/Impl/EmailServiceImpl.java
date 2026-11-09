package com.fastfix.service.Impl;

import com.fastfix.entity.User;
import com.fastfix.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.swing.text.html.HTML;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    @Async
    public void updateUserAccountDataEmail(User user,String siteUrl) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        String verifyUrl = siteUrl + "/api/auth/verify?code=" + user.getVerificationCode();

        Context context = new Context();
        context.setVariable("verify",verifyUrl);

        String htmlContent = templateEngine.process("email/user_change_email.html",context);

        helper.setSubject("Account Verification");
        helper.setTo(user.getAlternativeEmail());
        helper.setFrom("mkoszalka0@gmail.com");
        helper.setText(htmlContent,true);


        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendForgotPasswordEmail(User user,String siteUrl) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        String verifyUrl = siteUrl + "/api/auth/reset-password?token=" + user.getVerificationToken();

        Context context = new Context();
        context.setVariable("verify",verifyUrl);

        String htmlContent = templateEngine.process("email/reset_password_email.html",context);

        helper.setFrom("mkoszalka0@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Reset Password");
        helper.setText(htmlContent,true);

        javaMailSender.send(message);
    }
}
