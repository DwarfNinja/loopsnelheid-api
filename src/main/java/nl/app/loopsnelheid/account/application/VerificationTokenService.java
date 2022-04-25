package nl.app.loopsnelheid.account.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.data.VerificationTokenRepository;
import nl.app.loopsnelheid.account.domain.User;
import nl.app.loopsnelheid.account.domain.VerificationToken;
import org.springframework.stereotype.Service;

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
}
