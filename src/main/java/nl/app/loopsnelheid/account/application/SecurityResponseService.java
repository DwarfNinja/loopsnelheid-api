package nl.app.loopsnelheid.account.application;

import nl.app.loopsnelheid.account.config.AccountEndpoints;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityResponseService {
    public static Map<String, String> generateTokenBody(String token) {
        Map<String, String> body = new HashMap<>();
        body.put(AccountEndpoints.TOKEN_RESPONSE_PREFIX, token);
        return body;
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<>(map,status);
    }
}
