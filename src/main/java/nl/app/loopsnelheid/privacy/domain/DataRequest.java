package nl.app.loopsnelheid.privacy.domain;

import lombok.Builder;
import lombok.Getter;
import nl.app.loopsnelheid.security.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_requests")
@Getter
@Builder
public class DataRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private DataRequestStatus dataRequestStatus;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public DataRequest() {}

    public DataRequest(Long id, String email, DataRequestStatus dataRequestStatus, LocalDateTime requestedAt, User user)
    {
        this.id = id;
        this.email = email;
        this.dataRequestStatus = dataRequestStatus;
        this.requestedAt = requestedAt;
        this.user = user;
    }

    public void markAsPending()
    {
        dataRequestStatus = DataRequestStatus.PENDING;
    }

    public void markAsFinished()
    {
        dataRequestStatus = DataRequestStatus.FINISHED;
    }
}
