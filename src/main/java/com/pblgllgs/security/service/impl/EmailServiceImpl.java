package com.pblgllgs.security.service.impl;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.exception.ApiException;
import com.pblgllgs.security.service.EmailService;
import com.pblgllgs.security.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "NEW_USER_ACCOUNT_VERIFICATION";
    public static final String PASSWORD_RESET_REQUEST = "PASSWORD_RESET_REQUEST";
    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try{
            var simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(EmailUtils.getEmailMessage(name, host,token));
            sender.send(simpleMailMessage);
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApiException("Unable to send welcome email");
        }
    }



    @Override
    @Async
    public void sendPasswordResetEmail(String name, String email, String token) {
        try{
            var simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(PASSWORD_RESET_REQUEST);
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(EmailUtils.getResetPasswordMessage(name, host,token));
            sender.send(simpleMailMessage);
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApiException("Unable to send welcome email");
        }
    }


}
