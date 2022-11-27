package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.util.Validation;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnRegistrationCompleteEvent;
import nl.app.loopsnelheid.security.domain.exception.RegisterValidationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService
{
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public User createUser(
            Long id,
            String password,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            boolean termsAndConditions,
            boolean privacyStatement,
            int length,
            int weight
    )
    {
        if (!(termsAndConditions && privacyStatement))
        {
            throw new RegisterValidationException("U moet akkoord gaan met de algemene voorwaarden en privacy verklaring");
        }
        else if (password.length() < 7)
        {
            throw new RegisterValidationException("Het wachtwoord moet langer dan 7 karakters zijn");
        }
        else if (email == null || !Validation.validEmail(email) || userService.isEmailRegistered(email))
        {
            throw new RegisterValidationException("Het opgegeven e-mailadres is ongeldig of in gebruik");
        }
        else if (sex == null || !(sex.equals(Sex.MALE.toString()) || sex.equals(Sex.FEMALE.toString())))
        {
            throw new RegisterValidationException("U moet een geslacht invoeren");
        }
        else if (length < 100)
        {
            throw new RegisterValidationException("U moet uw lengte in centimeters opgeven");
        }
        else if (weight < 30)
        {
            throw new RegisterValidationException("U moet uw gewicht in kilogram opgeven");
        }

        Set<Role> roles;

        if (userService.getAllUsers().size() == 0) {
            roles = roleService.provideUserRoles(List.of("ROLE_ADMIN"));
        }
        else {
            roles = roleService.provideUserRoles(List.of("ROLE_USER"));
        }

        User.UserBuilder userBuilder = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .dateOfBirth(dateOfBirth)
                .sex(Sex.valueOf(sex))
                .roles(roles)
                .isResearchCandidate(isResearchCandidate)
                .length(length)
                .weight(weight);

        if (id != null)
        {
            userBuilder.id(id);
        }

        return userBuilder.build();
    }

    public Long registerUser(
            Long id,
            String password,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            boolean termsAndConditions,
            boolean privacyStatement,
            int length,
            int weight
    )
    {
        User user = createUser(id, password, email, dateOfBirth, sex, isResearchCandidate, termsAndConditions, privacyStatement, length, weight);

        User savedUser = saveUser(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

        return savedUser.getId();
    }

    public User saveUser(User user)
    {
        return userService.saveUser(user);
    }
}
