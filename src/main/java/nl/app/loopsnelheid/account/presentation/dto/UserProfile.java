package nl.app.loopsnelheid.account.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class UserProfile {
    private final String email;
    private final Date dateOfBirth;
    private final String sex;
}
