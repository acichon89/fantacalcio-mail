package com.javangarda.fantacalcio.mail.infrastructure.port.adapter.mail;

import com.javangarda.fantacalcio.mail.application.data.MailData;
import com.javangarda.fantacalcio.mail.application.internal.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Slf4j
public class DefaultEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;
    private final String mailHostname;

    @Override
    public void send(MailData mailData) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(mailData.getRecipient());
            helper.setFrom(mailHostname);
            helper.setSubject(mailData.getTitle());
            helper.setText(mailData.getContent(), true);
            InputStreamSource imageSource = new ByteArrayResource(IOUtils.toByteArray(getClass().getResourceAsStream("/images/header.jpg")));
            helper.addInline("header.jpg", imageSource, "image/jpg");

        } catch (Exception e) {
            log.error("!!!ERROR WHILE SENDING MAIL: "+mailData.toString(), e);
        }

        javaMailSender.send(mail);
    }


}
