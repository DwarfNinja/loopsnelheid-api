package nl.app.loopsnelheid.privacy.presentation;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.PrivacyService;
import nl.app.loopsnelheid.privacy.domain.DataRequestContent;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.privacy.presentation.dto.DataRequestDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AccountEndpoints.PRIVACY_PATH)
@RequiredArgsConstructor
public class PrivacyController {

    private final UserService userService;
    private final PrivacyService privacyService;

    @PostMapping
    public DataRequestDto submitRequest()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DataRequest dataRequest = privacyService.saveDataRequest(authenticatedUser);

        return new DataRequestDto(
                dataRequest.getId(),
                dataRequest.getEmail(),
                dataRequest.getDataRequestStatus().toString(),
                dataRequest.getRequestedAt()
        );
    }
}
