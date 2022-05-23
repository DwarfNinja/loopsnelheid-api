package nl.app.loopsnelheid.account.presentation;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.application.PrivacyService;
import nl.app.loopsnelheid.account.application.AuthService;
import nl.app.loopsnelheid.account.config.AccountEndpoints;
import nl.app.loopsnelheid.account.domain.DataRequest;
import nl.app.loopsnelheid.account.domain.User;
import nl.app.loopsnelheid.account.presentation.dto.DataRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(AccountEndpoints.PRIVACY_PATH)
@RequiredArgsConstructor
public class PrivacyController {

    private final AuthService authService;
    private final PrivacyService privacyService;

    @PostMapping
    public DataRequestDto submitRequest(Principal principal)
    {
        User authenticatedUser = authService.loadUserByUsername(principal.getName());
        DataRequest dataRequest = privacyService.saveDataRequest(authenticatedUser);

        return new DataRequestDto(
                dataRequest.getEmail(),
                dataRequest.getDataRequestStatus().toString(),
                dataRequest.getRequestedAt()
        );
    }
}
