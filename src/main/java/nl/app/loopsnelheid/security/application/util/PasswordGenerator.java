package nl.app.loopsnelheid.security.application.util;

import java.util.Random;

public class PasswordGenerator
{
    public static String generatePassword(int length)
    {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }

        return sb.toString();
    }
}
