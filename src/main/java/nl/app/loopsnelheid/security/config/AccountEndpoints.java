package nl.app.loopsnelheid.security.config;

public class AccountEndpoints
{
    // Security endpoints
    public static final String LOGIN_PATH = "/auth/login";
    public static final String REGISTER_PATH = "/auth/register";
    public static final String VERIFY_DIGITAL_CODE_PATH = "/auth/verify/code/{userId}/{digitalCode}";
    public static final String VERIFY_TOKEN_PATH = "/auth/verify/token/{userId}/{token}";

    // Privacy endpoints
    public static final String PRIVACY_PATH = "/privacy";
}
