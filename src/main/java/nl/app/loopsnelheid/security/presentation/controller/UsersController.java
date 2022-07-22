package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.RoleDto;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController
{
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<UserDto> getAllUsers()
    {
        return userService.getAllUsers().stream().map(user -> new UserDto(
                user.getId(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getSex().toString(),
                user.isResearchCandidate(),
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> new RoleDto(role.getName().toString())).collect(Collectors.toSet())
        )).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId)
    {
        User user = userService.loadUserById(userId);

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getSex().toString(),
                user.isResearchCandidate(),
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> new RoleDto(role.getName().toString())).collect(Collectors.toSet())
        );
    }

    
}
