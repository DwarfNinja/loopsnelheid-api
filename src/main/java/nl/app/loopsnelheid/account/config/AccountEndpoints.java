package nl.app.loopsnelheid.account.config;

public class AccountEndpoints {
    // Token prefixes and headers
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECURITY_HEADER = "Authorization";
    public static final String TOKEN_RESPONSE_PREFIX = "access_token";

    // Security endpoints
    public static final String LOGIN_PATH = "/auth/login";
    public static final String REGISTER_PATH = "/auth/register";
    public static final String PROFILE_PATH = "/auth/profile";

    // Claims
    public static final String ROLE_CLAIM = "role";
    public static final String FIRSTNAME_CLAIM = "firstName";
    public static final String LASTNAME_CLAIM = "lastName";
}
