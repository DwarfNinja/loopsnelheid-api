package nl.app.loopsnelheid.account.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_requests")
public class DataRequest
{
    @Id
    @GeneratedValue
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

    public DataRequest(String email, LocalDateTime requestedAt, User user) {
        this.email = email;
        this.dataRequestStatus = DataRequestStatus.CONFIRMED;
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
