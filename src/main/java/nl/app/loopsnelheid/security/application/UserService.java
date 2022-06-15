package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public User loadUserByUsername(String username) {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Gebruiker bestaat niet"));
    }
}
