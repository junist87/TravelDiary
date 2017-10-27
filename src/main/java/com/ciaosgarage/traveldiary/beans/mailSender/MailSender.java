package com.ciaosgarage.traveldiary.beans.mailSender;

import com.ciaosgarage.traveldiary.beans.mailSender.exceptions.CannotSendEmailException;

public interface MailSender {
    public void sendMail(String title, String context, String senderEmail, String targetEmail) throws CannotSendEmailException;
}
