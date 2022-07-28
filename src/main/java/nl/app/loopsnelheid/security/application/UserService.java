package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.util.PasswordGenerator;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.*;
import nl.app.loopsnelheid.security.domain.event.OnEmailChangeEvent;
import nl.app.loopsnelheid.security.domain.event.OnPasswordChangeEvent;
import nl.app.loopsnelheid.security.domain.event.OnPersonalInformationChangeEvent;
import nl.app.loopsnelheid.security.domain.event.OnResetPasswordEvent;
import nl.app.loopsnelheid.security.domain.exception.EmailAlreadyUsedException;
import nl.app.loopsnelheid.security.domain.exception.OldPasswordIncorrectException;
import nl.app.loopsnelheid.security.domain.exception.UserNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;

    public User loadUserByUsername(String username)
    {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Opgegeven gebruiker bestaat niet."));
    }

    public User loadUserById(Long id)
    {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Opgegeven gebruiker bestaat niet."));
    }

    public List<User> getResearchCandidates()
    {
        return userRepository.findAllWhereIsResearchCandidate();
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User updateUserById(
            Long id,
            String password,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            int length,
            int weight,
            Set<Role> roles)
    {
        User user = loadUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        user.setSex(Sex.valueOf(sex));
        user.setResearchCandidate(isResearchCandidate);
        user.setLength(length);
        user.setWeight(weight);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User updateUserById(
            Long id,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            int length,
            int weight,
            Set<Role> roles)
    {
        User user = loadUserById(id);

        if (!user.getEmail().equals(email) && this.isEmailRegistered(email))
        {
            throw new EmailAlreadyUsedException("Het opgegeven e-mailadres wordt al gebruikt door een ander gebruiker");
        }

        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        user.setSex(Sex.valueOf(sex));
        user.setResearchCandidate(isResearchCandidate);
        user.setLength(length);
        user.setWeight(weight);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public void determineEmailUpdate(User user, String email)
    {
        if (user.getEmail().equals(email))
        {
            throw new EmailAlreadyUsedException("Dit e-mailadres wordt momenteel al door u gebruikt");
        }

        if (!user.getEmail().equals(email) && this.isEmailRegistered(email))
        {
            throw new EmailAlreadyUsedException("Het opgegeven e-mailadres wordt al gebruikt door een ander gebruiker");
        }
    }
    public void requestEmailUpdateById(ResetEmailVerification resetEmailVerification)
    {
        eventPublisher.publishEvent(new OnEmailChangeEvent(resetEmailVerification));
    }

    public User updateEmailById(Long id, String email)
    {
        User user = loadUserById(id);

        if (!user.getEmail().equals(email) && this.isEmailRegistered(email))
        {
            throw new EmailAlreadyUsedException("Het opgegeven e-mailadres wordt al gebruikt door een ander gebruiker");
        }

        user.setEmail(email);

        return userRepository.save(user);
    }

    public void updatePasswordById(Long id,String oldPassword, String password)
    {
        User user = loadUserById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
        {
            throw new OldPasswordIncorrectException("Het huidige wachtwoord is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(password));
        User updatedUser = userRepository.save(user);
        eventPublisher.publishEvent(new OnPasswordChangeEvent(updatedUser));
    }

    public String resetPasswordById(Long id)
    {
        User user = loadUserById(id);
        String password = PasswordGenerator.generatePassword(8);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return password;
    }

    public void requestPasswordUpdateById(ResetPasswordVerification resetPasswordVerification)
    {
        eventPublisher.publishEvent(new OnResetPasswordEvent(resetPasswordVerification));
    }

    public User updatePersonalInformationById(
            Long id,
            Date dateOfBirth,
            String sex,
            int length,
            int weight,
            boolean isResearchCandidate
    )
    {
        User user = loadUserById(id);
        user.setDateOfBirth(dateOfBirth);
        user.setSex(Sex.valueOf(sex));
        user.setLength(length);
        user.setWeight(weight);
        user.setResearchCandidate(isResearchCandidate);

        User updatedUser = userRepository.save(user);

        eventPublisher.publishEvent(new OnPersonalInformationChangeEvent(updatedUser));

        return updatedUser;
    }

    public boolean isEmailRegistered(String email)
    {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }

    public void deleteUserById(Long id)
    {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username)
    {
        userRepository.deleteByEmail(username);
    }
}
