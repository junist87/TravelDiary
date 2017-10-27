package com.ciaosgarage.traveldiary.beans.mailSender;

import com.ciaosgarage.traveldiary.beans.mailSender.exceptions.CannotSendEmailException;
import org.springframework.stereotype.Component;

@Component
public class TestMailSender implements MailSender {
    @Override
    public void sendMail(String title, String context, String senderEmail, String targetEmail) throws CannotSendEmailException {

    }
}
