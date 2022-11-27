package nl.app.loopsnelheid.security.config;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter()
    {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.ALL_MEASURES).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.LOGIN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.RESET_PASSWORD_PATH).permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.RESET_PASSWORD_VERIFICATION_PATH).permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.VERIFY_TOKEN_PATH).permitAll()
                .antMatchers(HttpMethod.POST, AccountEndpoints.VERIFY_DIGITAL_CODE_PATH).permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.RESET_EMAIL_VERIFICATION_PATH).permitAll()
                .antMatchers(HttpMethod.GET, AccountEndpoints.DEFAULT_MEASURES).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
