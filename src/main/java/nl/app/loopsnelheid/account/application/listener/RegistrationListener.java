package nl.app.loopsnelheid.account.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.application.AuthService;
import nl.app.loopsnelheid.account.application.VerificationTokenService;
import nl.app.loopsnelheid.account.application.util.TokenGenerator;
import nl.app.loopsnelheid.account.domain.User;
import nl.app.loopsnelheid.account.domain.VerificationToken;
import nl.app.loopsnelheid.account.domain.event.OnRegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String generatedToken = TokenGenerator.generateToken();
        List<Integer> generatedDigitalCodeList = TokenGenerator.generateDigitalCode(6);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user, generatedToken, generatedDigitalCodeList);
        verificationTokenService.saveVerificationToken(verificationToken);

        sendConfirmationEmail(user.getEmail(), verificationToken.getToken(), verificationToken.getDigitalCode());
    }

    private void sendConfirmationEmail(String email, String token, String digitalToken) {
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("digitalToken", digitalToken);

        String process = templateEngine.process("welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try{
            helper.setSubject("Welcome ");
            helper.setText(process, true);
            helper.setTo(email);
            javaMailSender.send(mimeMessage);
        }catch(MessagingException ignored) {}
    }
}
