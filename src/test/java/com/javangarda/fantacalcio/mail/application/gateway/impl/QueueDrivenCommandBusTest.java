package com.javangarda.fantacalcio.mail.application.gateway.impl;

import com.javangarda.fantacalcio.mail.application.gateway.commands.ChangeEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ConfirmEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.ResetPasswordEmailCommand;
import com.javangarda.fantacalcio.mail.application.gateway.commands.SendMailCommand;
import com.javangarda.fantacalcio.mail.application.internal.MailDataPreparator;
import com.javangarda.fantacalcio.mail.application.internal.MailDataQueue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;

public class QueueDrivenCommandBusTest {

    private MailDataPreparator mailDataPreparator;
    private MailDataQueue mailDataQueue;

    private QueueDrivenCommandBus queueDrivenCommandBus;

    @Before
    public void init(){
        mailDataPreparator = Mockito.mock(MailDataPreparator.class);
        mailDataQueue = Mockito.mock(MailDataQueue.class);

        queueDrivenCommandBus = new QueueDrivenCommandBus(mailDataPreparator, mailDataQueue);
    }

    @Test
    public void should_handle_registration_email() {
        //given:
        ConfirmEmailCommand command = ConfirmEmailCommand.of("john@doe.com", "token-123", Optional.empty());
        Mockito.when(mailDataPreparator.prepare(Mockito.eq(command))).thenReturn(dummySendMailCommand());
        //when:
        queueDrivenCommandBus.sendRegistrationConfirmationMail(command);
        //then:
        ArgumentCaptor<SendMailCommand> sendMailCommandAC = ArgumentCaptor.forClass(SendMailCommand.class);
        Mockito.verify(mailDataQueue).add(sendMailCommandAC.capture());
        assertEquals("dummy email", sendMailCommandAC.getValue().getTitle());
    }

    @Test
    public void should_handle_confirmation_email() {
        //given:
        ChangeEmailCommand command = ChangeEmailCommand.of("John Doe", "johndoe@newdomain.com", "token-xyz", Optional.empty());
        Mockito.when(mailDataPreparator.prepare(Mockito.eq(command))).thenReturn(dummySendMailCommand());
        //when:
        queueDrivenCommandBus.sendConfirmationEmailMail(command);
        //then:
        ArgumentCaptor<SendMailCommand> sendMailCommandAC = ArgumentCaptor.forClass(SendMailCommand.class);
        Mockito.verify(mailDataQueue).add(sendMailCommandAC.capture());
        assertEquals("dummy email", sendMailCommandAC.getValue().getTitle());
    }

    @Test
    public void should_handle_resetpass_email() {
        //given:
        ResetPasswordEmailCommand command = ResetPasswordEmailCommand.of("john@doe.com", "John Doe", "token-xyz", Optional.empty());
        Mockito.when(mailDataPreparator.prepare(Mockito.eq(command))).thenReturn(dummySendMailCommand());
        //when:
        queueDrivenCommandBus.sendResetPasswordEmailMail(command);
        //then:
        ArgumentCaptor<SendMailCommand> sendMailCommandAC = ArgumentCaptor.forClass(SendMailCommand.class);
        Mockito.verify(mailDataQueue).add(sendMailCommandAC.capture());
        assertEquals("dummy email", sendMailCommandAC.getValue().getTitle());
    }

    private SendMailCommand dummySendMailCommand() {
        return SendMailCommand.of("john@doe.com", "dummy email", "<p>hello!</p>");
    }
}