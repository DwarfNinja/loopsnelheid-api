package nl.app.loopsnelheid.security.domain;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "delete_requests")
@Getter
public class DeleteRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID jobId;

    private LocalDateTime requestedAt;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public DeleteRequest() {}

    public DeleteRequest(User user)
    {
        this.user = user;
        this.requestedAt = LocalDateTime.now();
    }

    public void setJobId(UUID jobId)
    {
        this.jobId = jobId;
    }

    public String getEmail()
    {
        return this.user.getEmail();
    }
}
