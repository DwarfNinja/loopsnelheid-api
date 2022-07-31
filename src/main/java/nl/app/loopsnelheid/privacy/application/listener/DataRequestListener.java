package nl.app.loopsnelheid.privacy.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.PrivacyService;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.event.OnDataRequestCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DataRequestListener implements ApplicationListener<OnDataRequestCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(DataRequestListener.class);
    private final PrivacyService privacyService;
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void onApplicationEvent(OnDataRequestCompleteEvent event)
    {
        confirmDataRequest(event);
    }

    public void confirmDataRequest(OnDataRequestCompleteEvent event)
    {
        DataRequest dataRequest = event.getDataRequest();
        privacyService.markRequestAsFinished(dataRequest);

        sendDataRequestMail(dataRequest.getAuthorEmail(), dataRequest.getFilePath(), dataRequest.getRequestedAt());
    }

    public void sendDataRequestMail(String email, String filePath, LocalDateTime requestedAt)
    {
        try
        {
            File file = new File(filePath);
            FileSystemResource fileSystemResource = new FileSystemResource(file);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Context context = new Context();
            context.setVariable("requestedAt", requestedAt.format(dateTimeFormatter));

            String process = templateEngine.process("data_request", context);

            javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(from);
            helper.setSubject("Verzoek tot gegevensoverdraagbaarheid");
            helper.setText(process, true);
            helper.setTo(email);
            helper.addAttachment("persoonsdocument.pdf", fileSystemResource);
            javaMailSender.send(mimeMessage);

            file.delete();
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
