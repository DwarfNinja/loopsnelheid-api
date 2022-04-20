package nl.app.loopsnelheid.account.presentation;

import nl.app.loopsnelheid.account.config.AccountEndpoints;
import nl.app.loopsnelheid.account.presentation.dto.UserProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountEndpoints.PROFILE_PATH)
public class UserController {
    @GetMapping
    public UserProfile userProfile(@AuthenticationPrincipal UserProfile userProfile) {
        return userProfile;
    }
}
