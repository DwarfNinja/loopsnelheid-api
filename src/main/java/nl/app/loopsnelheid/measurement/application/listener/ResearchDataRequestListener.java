package nl.app.loopsnelheid.measurement.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.event.OnResearchDataRequestCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.File;

@Component
@RequiredArgsConstructor
public class ResearchDataRequestListener implements ApplicationListener<OnResearchDataRequestCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(ResearchDataRequestListener.class);
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnResearchDataRequestCompleteEvent event)
    {
        confirmResearchDataRequest(event);
    }

    public void confirmResearchDataRequest(OnResearchDataRequestCompleteEvent event)
    {
        ResearchData researchData = event.getResearchData();

        sendResearchDataRequest(researchData.getExportedBy(), event.getPath());
    }

    public void sendResearchDataRequest(String email, String filePath)
    {
        try
        {
            File file = new File(filePath);
            FileSystemResource fileSystemResource = new FileSystemResource(file);

            Context context = new Context();

            String process = templateEngine.process("research_data_request", context);

            javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject("Het onderzoeksrapport");
            helper.setText(process, true);
            helper.setTo(email);
            helper.addAttachment("onderzoeksarchief.zip", fileSystemResource);
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
