package nl.app.loopsnelheid.security.domain;

import java.util.Set;

public class JwtResponse {
    private final String jwt;
    private final String email;
    private final Set<String> roles;

    public JwtResponse(String jwt, String email, Set<String> roles) {
        this.jwt = jwt;
        this.email = email;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
