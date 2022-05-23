package nl.app.loopsnelheid.account.config;

import nl.app.loopsnelheid.account.application.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-in-ms}")
    private Integer jwtExpirationInMs;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure cors and csrf
        http = http.cors()
                .and()
                .csrf()
                .disable();

        // Make session stateless
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Endpoints
        http = http.authorizeRequests()
                .antMatchers(HttpMethod.POST, AccountEndpoints.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.VERIFY_TOKEN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.VERIFY_DIGITAL_CODE_PATH).permitAll()
                .anyRequest().authenticated()
                .and();

        // Exception handlers
        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN));

        // AddingJWT filters
        http.addFilterBefore(
                        new JwtAuthenticationFilter(
                                AccountEndpoints.LOGIN_PATH,
                                this.jwtSecret,
                                this.jwtExpirationInMs,
                                this.authenticationManager()
                        ),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
