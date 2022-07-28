package nl.app.loopsnelheid.security.config;

public class AccountEndpoints
{
    // Security endpoints
    public static final String LOGIN_PATH = "/auth/login";
    public static final String REGISTER_PATH = "/auth/register";

    public static final String RESET_PASSWORD_PATH = "/auth/reset/password";
    public static final String RESET_PASSWORD_VERIFICATION_PATH = "/auth/reset/password/{userId}/{token}";

    public static final String VERIFY_DIGITAL_CODE_PATH = "/auth/verify/code/{userId}/{digitalCode}";
    public static final String VERIFY_TOKEN_PATH = "/auth/verify/token/{userId}/{token}";

    public static final String RESET_EMAIL_VERIFICATION_PATH = "/auth/verify/{userId}/{token}";

    // Measure endpoints
    public static final String DEFAULT_MEASURES = "/measures/default";

    // Privacy endpoints
    public static final String PRIVACY_PATH = "/privacy";
}
