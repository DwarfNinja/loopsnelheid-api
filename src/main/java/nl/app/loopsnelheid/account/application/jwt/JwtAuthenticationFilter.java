package nl.app.loopsnelheid.account.application.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.app.loopsnelheid.account.application.SecurityResponseService;
import nl.app.loopsnelheid.account.domain.User;
import nl.app.loopsnelheid.account.presentation.dto.LoginDto;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String secret;
    private final int expirationMs;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(
            String path,
            String secret,
            int expirationMs,
            AuthenticationManager authenticationManager
    ) {
        super(new AntPathRequestMatcher(path));

        this.secret = secret;
        this.expirationMs = expirationMs;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        LoginDto login = new ObjectMapper()
                .readValue(request.getInputStream(), LoginDto.class);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.email, login.password)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setHeaderParam("typ", "JWT")
                .setIssuer("loopsnelheid-api")
                .setAudience("loopsnelheid")
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + this.expirationMs))
                .compact();

        JSONObject jwtTokenJson = new JSONObject(SecurityResponseService.generateTokenBody(token));

        response.getWriter().write(jwtTokenJson.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}
