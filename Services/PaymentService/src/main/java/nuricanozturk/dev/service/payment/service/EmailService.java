package nuricanozturk.dev.service.payment.service;


import nuricanozturk.dev.service.payment.config.EmailTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Lazy
public class EmailService
{
    @Value("${spring.mail.username}")
    private String senderEmail;
    private final JavaMailSender m_javaMailSender;
    private final ExecutorService m_executorService;


    public EmailService(JavaMailSender javaMailSender, ExecutorService executorService)
    {
        m_javaMailSender = javaMailSender;
        m_executorService = executorService;
    }


    private void send(EmailTopic emailTopic)
    {
        var message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(emailTopic.toEmail());
        message.setSubject(emailTopic.topic());
        message.setText(emailTopic.content());
        m_javaMailSender.send(message);
    }


    public void sendEmail(EmailTopic emailTopic)
    {
        m_executorService.execute(() -> send(emailTopic));
    }
}