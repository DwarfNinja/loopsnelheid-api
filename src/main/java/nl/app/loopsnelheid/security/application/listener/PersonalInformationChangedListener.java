package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnPersonalInformationChangeEvent;
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
import java.text.SimpleDateFormat;

@Component
@RequiredArgsConstructor
public class PersonalInformationChangedListener implements ApplicationListener<OnPersonalInformationChangeEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(PersonalInformationChangedListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void onApplicationEvent(OnPersonalInformationChangeEvent onPersonalInformationChangeEvent)
    {
        this.sendConfirmationEmail(onPersonalInformationChangeEvent);
    }

    private void sendConfirmationEmail(OnPersonalInformationChangeEvent onPersonalInformationChangeEvent)
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        User user = onPersonalInformationChangeEvent.getUser();

        Context context = new Context();
        context.setVariable("birth_date", simpleDateFormat.format(user.getDateOfBirth()));
        context.setVariable("sex", user.getSex().toString().equals("MALE") ? "Man" : "Vrouw");
        context.setVariable("length", user.getLength());
        context.setVariable("weight", user.getWeight());
        context.setVariable("is_research_candidate", user.isResearchCandidate() ? "Ja" : "Nee");

        String process = templateEngine.process("successfully_changed_personal_information", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setFrom(from);
            helper.setSubject("Uw persoonlijke gegevens zijn gewijzigd");
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
