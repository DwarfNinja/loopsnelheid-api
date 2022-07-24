package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username)
    {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Gebruiker bestaat niet"));
    }

    public User loadUserById(Long id)
    {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Gebruiker bestaat niet"));
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

    public void deleteUserById(Long id)
    {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username)
    {
        userRepository.deleteByEmail(username);
    }
}
