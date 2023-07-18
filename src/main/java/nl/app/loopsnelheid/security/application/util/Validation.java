package nl.app.loopsnelheid.security.application.util;

import java.util.regex.Pattern;

public class Validation
{
    public static boolean validEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;

        return pat.matcher(email).matches();
    }
}
