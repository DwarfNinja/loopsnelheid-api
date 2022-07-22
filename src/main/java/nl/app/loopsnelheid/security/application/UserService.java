package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

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
}
