package nl.app.loopsnelheid.account.application.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TokenGenerator {
    public static String generateToken() {
        String randomUUID = UUID.randomUUID().toString();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(randomUUID.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
    }

    public static List<Integer> generateDigitalCode(int length) {
        List<Integer> integerList = new ArrayList();

        for(int i = 0; i < length; i++) {
            Random rnd = new Random();
            int number = rnd.nextInt(9);
            integerList.add(number);
        }

        return integerList;
    }
}
