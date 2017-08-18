package com.javangarda.fantacalcio.mail.infrastructure.adapter.outcome.mail;

import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.internal.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@AllArgsConstructor
@Slf4j
public class DefaultEmailSender implements EmailSender {

    private final JavaMailSender javaMailSender;
    private final String mailHostname;

    @Override
    public void send(SendMailCommand sendMailCommand) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(sendMailCommand.getRecipient());
            helper.setFrom(mailHostname);
            helper.setSubject(sendMailCommand.getTitle());
            helper.setText(sendMailCommand.getContent(), true);
            InputStreamSource imageSource = new ByteArrayResource(IOUtils.toByteArray(getClass().getResourceAsStream(getHeaderImage())));
            helper.addInline("header.jpg", imageSource, "image/jpg");
        } catch (Exception e) {
            log.error("!!!ERROR WHILE SENDING MAIL: "+ sendMailCommand.toString(), e);
        }

        javaMailSender.send(mail);
    }

    private String getHeaderImage(){
        Random random = new Random();
        int x = random.nextInt(4) + 1;
        return "/images/header"+x+".png";
    }

}
