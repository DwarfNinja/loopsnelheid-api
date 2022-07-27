package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnEmailChangeCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;

@Component
@RequiredArgsConstructor
public class EmailChangedConfirmedListener implements ApplicationListener<OnEmailChangeCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(EmailChangedConfirmedListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    @Override
    public void onApplicationEvent(OnEmailChangeCompleteEvent onEmailChangeCompleteEvent)
    {
        this.sendConfirmationEmail(onEmailChangeCompleteEvent);
    }

    private void sendConfirmationEmail(OnEmailChangeCompleteEvent event)
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        User user = event.getUser();

        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("birth_date", simpleDateFormat.format(user.getDateOfBirth()));
        context.setVariable("sex", user.getSex().toString().equals("MALE") ? "Man" : "Vrouw");
        context.setVariable("length", user.getLength());
        context.setVariable("weight", user.getWeight());
        context.setVariable("is_research_candidate", user.isResearchCandidate() ? "Ja" : "Nee");

        String process = templateEngine.process("successfully_changed_email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setSubject("Uw e-mailadres is succesvol gewijzigd");
            helper.setText(process, true);
            helper.setTo(user.getEmail());
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
