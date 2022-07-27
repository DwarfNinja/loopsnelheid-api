package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.DeleteRequestRepository;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnDeleteRequestConfirmedEvent;
import nl.app.loopsnelheid.security.domain.event.OnDeleteRequestProcessedEvent;
import nl.app.loopsnelheid.security.domain.event.OnDeleteRequestRevokedEvent;
import nl.app.loopsnelheid.security.domain.exception.AlreadyRequestedException;
import nl.app.loopsnelheid.security.domain.exception.DeleteRequestNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteRequestService
{
    private final DeleteRequestRepository deleteRequestRepository;
    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    public DeleteRequest initDeleteRequest(User user)
    {
        if (hasOpenDeleteRequest(user))
        {
            throw new AlreadyRequestedException("U hebt al een openstaand verzoek tot vergetelheid ingediend.");
        }

        return new DeleteRequest(user);
    }

    public void submitDeleteRequest(UUID uuid, DeleteRequest deleteRequest)
    {
        deleteRequest.setJobId(uuid);

        eventPublisher.publishEvent(new OnDeleteRequestConfirmedEvent(deleteRequest));
        deleteRequestRepository.save(deleteRequest);
    }

    public DeleteRequest getDeleteRequestByUser(User user)
    {
        return deleteRequestRepository.findByUser(user).orElseThrow(() -> new DeleteRequestNotFoundException("U heeft nog geen verzoek tot vergetelheid ingediend."));
    }

    public void revokeDeleteRequest(DeleteRequest deleteRequest)
    {
        eventPublisher.publishEvent(new OnDeleteRequestRevokedEvent(deleteRequest));
        deleteRequestRepository.delete(deleteRequest);
    }

    public boolean hasOpenDeleteRequest(User user)
    {
        Optional<DeleteRequest> deleteRequest = deleteRequestRepository.findByUser(user);

        return deleteRequest.isPresent();
    }

    public void handleRequest(DeleteRequest deleteRequest)
    {
        User user = deleteRequest.getUser();

        eventPublisher.publishEvent(new OnDeleteRequestProcessedEvent(deleteRequest));
        userService.deleteUserById(user.getId());
    }
}
