package nl.app.loopsnelheid.account.config;

public class AccountEndpoints {
    // Token prefixes and headers
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECURITY_HEADER = "Authorization";
    public static final String TOKEN_RESPONSE_PREFIX = "access_token";

    // Security endpoints
    public static final String LOGIN_PATH = "/auth/login";
    public static final String REGISTER_PATH = "/auth/register";
    public static final String VERIFY_DIGITAL_CODE_PATH = "/auth/verify/code/{userId}/{digitalCode}";
    public static final String VERIFY_TOKEN_PATH = "/auth/verify/token/{userId}/{token}";
    public static final String PROFILE_PATH = "/auth/profile";

    // Privacy endpoints
    public static final String PRIVACY_PATH = "/privacy";
}
