package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.config.JwtUtils;
import nl.app.loopsnelheid.security.domain.JwtResponse;
import nl.app.loopsnelheid.security.domain.UserDetailsImp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService
{
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private UserDetails userDetails;

    public JwtResponse authenticateUser(String email, String password)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        userDetails = (UserDetailsImp) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }

    public UserDetails getUserDetails()
    {
        return userDetails;
    }
}
