package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.UserRepository;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnRegistrationCompleteEvent;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
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
                .roles(roles)
                .isResearchCandidate(userDto.isResearchCandidate)
                .length(userDto.length)
                .weight(userDto.weight);

        if (userDto.id != null)
        {
            userBuilder.id(userDto.id);
        }

        return userBuilder.build();
    }

    public Long registerUser(UserDto userDTO, String password)
    {
        String encodedPassword = this.passwordEncoder.encode(password);
        User user = createUser(userDTO, encodedPassword);

        User savedUser = saveUser(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

        return savedUser.getId();
    }

    public User saveUser(User user)
    {
        return this.userRepository.save(user);
    }
}
