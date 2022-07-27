package nl.app.loopsnelheid.privacy.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.DataRequestJobService;
import nl.app.loopsnelheid.privacy.application.PrivacyService;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.presentation.dto.DataRequestDto;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AccountEndpoints.PRIVACY_PATH)
@RequiredArgsConstructor
public class PrivacyController {

    private final UserService userService;
    private final PrivacyService privacyService;
    private final DataRequestJobService dataRequestJobService;

    @GetMapping
    public List<DataRequestDto> getDataRequests()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        return privacyService.getDataRequests(authenticatedUser.getId()).stream()
                .map(dataRequest -> new DataRequestDto(
                        dataRequest.getId(),
                        dataRequest.getEmail(),
                        dataRequest.getDataRequestStatus().toString(),
                        dataRequest.getRequestedAt()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DataRequestDto getDataRequest(@PathVariable Long id)
    {
        DataRequest dataRequest = privacyService.getDataRequestById(id);

        return new DataRequestDto(
                dataRequest.getId(),
                dataRequest.getEmail(),
                dataRequest.getDataRequestStatus().toString(),
                dataRequest.getRequestedAt()
        );
    }

    @PostMapping
    public DataRequestDto submitRequest()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DataRequest dataRequest = privacyService.saveDataRequest(authenticatedUser);
        dataRequestJobService.initJob(dataRequest);

        return new DataRequestDto(
                dataRequest.getId(),
                dataRequest.getEmail(),
                dataRequest.getDataRequestStatus().toString(),
                dataRequest.getRequestedAt()
        );
    }
}
