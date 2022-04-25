package nl.app.loopsnelheid.account.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.domain.event.OnRegistrationCompleteEvent;
import nl.app.loopsnelheid.account.presentation.dto.UserDto;
import nl.app.loopsnelheid.account.data.UserRepository;
import nl.app.loopsnelheid.account.domain.Sex;
import nl.app.loopsnelheid.account.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public User userCreate(UserDto userDto, String encodedPassword) {
        User.UserBuilder userBuilder = User.builder()
                .email(userDto.email)
                .password(encodedPassword)
                .dateOfBirth(userDto.dateOfBirth)
                .sex(Sex.valueOf(userDto.sex));

        if(userDto.id != null) {
            userBuilder.id(userDto.id);
        }

        return userBuilder.build();
    }

    public void userRegister(UserDto userDTO, String password)  {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = userCreate(userDTO, encodedPassword);

        userSave(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    }

    public void userSave(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
