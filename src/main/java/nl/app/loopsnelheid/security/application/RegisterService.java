package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.event.OnRegistrationCompleteEvent;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService
{
    private final UserRepository userRepository;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    public User createUser(UserDto userDto, String encodedPassword)
    {
        Set<Role> roles = roleService.provideUserRoles(List.of("ROLE_USER"));

        User.UserBuilder userBuilder = User.builder()
                .email(userDto.email)
                .password(encodedPassword)
                .dateOfBirth(userDto.dateOfBirth)
                .sex(Sex.valueOf(userDto.sex))
                .roles(roles);

        if (userDto.id != null)
        {
            userBuilder.id(userDto.id);
        }

        return userBuilder.build();
    }

    public void registerUser(UserDto userDTO, String password)
    {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = createUser(userDTO, encodedPassword);

        saveUser(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    }

    public void saveUser(User user)
    {
        this.userRepository.save(user);
    }
}
