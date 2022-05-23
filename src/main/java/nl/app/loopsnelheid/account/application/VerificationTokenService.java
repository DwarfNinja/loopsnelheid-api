package nl.app.loopsnelheid.account.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.data.VerificationTokenRepository;
import nl.app.loopsnelheid.account.domain.User;
import nl.app.loopsnelheid.account.domain.VerificationToken;
import nl.app.loopsnelheid.account.domain.exception.VerificationTokenExpiredException;
import nl.app.loopsnelheid.account.domain.exception.VerificationTokenNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationToken createVerificationToken(User user, String token, List<Integer> digitalCode) {
        return VerificationToken
                .builder()
                .user(user)
                .token(token)
                .digitalCode((digitalCode.toString().substring(1, digitalCode.toString().length() -1)).replace(", ", ""))
                .expiryDate(VerificationToken.generateExpiryDate())
                .build();
    }

    public void saveVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    public void determineVerificationToken(VerificationToken verificationToken)
    {
        if (verificationToken.isExpired() || verificationToken.isVerified()) throw new VerificationTokenExpiredException();
    }

    public void verifyToken(Long userId, String token)
    {
        VerificationToken verificationToken = verificationTokenRepository.findByTokenAndUserId(token, userId)
                .orElseThrow(VerificationTokenNotFoundException::new);

        determineVerificationToken(verificationToken);
        verificationToken.setVerificationDate(LocalDateTime.now());

        saveVerificationToken(verificationToken);
    }

    public void verifyDigitalCode(Long userId, String digitalCode)
    {
        VerificationToken verificationToken = verificationTokenRepository.findByDigitalCodeAndUserId(digitalCode, userId)
                .orElseThrow(VerificationTokenNotFoundException::new);

        determineVerificationToken(verificationToken);
        verificationToken.setVerificationDate(LocalDateTime.now());

        saveVerificationToken(verificationToken);
    }
}
