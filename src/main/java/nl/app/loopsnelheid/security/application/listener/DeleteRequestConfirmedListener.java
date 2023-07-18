package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.event.OnDeleteRequestConfirmedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DeleteRequestConfirmedListener implements ApplicationListener<OnDeleteRequestConfirmedEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(DeleteRequestConfirmedListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void onApplicationEvent(OnDeleteRequestConfirmedEvent onDeleteRequestConfirmedEvent)
    {
        this.sendConfirmationEmail(onDeleteRequestConfirmedEvent);
    }

    private void sendConfirmationEmail(OnDeleteRequestConfirmedEvent event)
    {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");

        DeleteRequest deleteRequest = event.getDeleteRequest();

        Context context = new Context();
        context.setVariable("date", deleteRequest.getRequestedAt().format(formatDate));
        context.setVariable("time", deleteRequest.getRequestedAt().format(formatTime));

        String process = templateEngine.process("delete_request_confirmed", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setFrom(from);
            helper.setSubject("Verzoek tot vergetelheid bevestiging");
            helper.setText(process, true);
            helper.setTo(deleteRequest.getEmail());
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
