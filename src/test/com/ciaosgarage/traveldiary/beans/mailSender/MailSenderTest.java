package com.ciaosgarage.traveldiary.beans.mailSender;


import com.ciaosgarage.traveldiary.context.BeansContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class MailSenderTest {

    @Autowired
    MailSender mailSender;


    @Before
    public void setup() {
        assertNotNull(mailSender);
    }

    @Test
    public void sendMail() {
        mailSender.sendMail("temporary","<h2> HI </h2>","tie@mail.com","ciaolee87@gmail.com");
    }
}
