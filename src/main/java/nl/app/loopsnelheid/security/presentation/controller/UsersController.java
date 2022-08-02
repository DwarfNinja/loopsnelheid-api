package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.RoleService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.ChangeUserDto;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController
{
    private final UserService userService;
    private final RoleService roleService;

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
                true,
                true,
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet())
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
                true,
                true,
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}")
    public ChangeUserDto updateUser(@PathVariable Long userId, @Validated @RequestBody ChangeUserDto dto)
    {
        Set<Role> roles = dto.roles.stream().map(roleService::getRoleByName).collect(Collectors.toSet());

        User user = dto.password == null
                ?
                userService.updateUserById(
                    userId,
                    dto.email,
                    dto.dateOfBirth,
                    dto.sex,
                    dto.isResearchCandidate,
                    dto.length,
                    dto.weight,
                    roles
                )
                :
                userService.updateUserById(
                        userId,
                        dto.password,
                        dto.email,
                        dto.dateOfBirth,
                        dto.sex,
                        dto.isResearchCandidate,
                        dto.length,
                        dto.weight,
                        roles
                );

        return new ChangeUserDto(
                user.getId(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getSex().toString(),
                user.isResearchCandidate(),
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId)
    {
        userService.deleteUserById(userId);
    }
}
