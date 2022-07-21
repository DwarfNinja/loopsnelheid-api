package nl.app.loopsnelheid.privacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "data_requests")
@Getter
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

    @JsonIgnore
    @Transient
    private String filePath;

    public DataRequest() {}

    public DataRequest(String email, User user, DataRequestStatus dataRequestStatus, LocalDateTime requestedAt)
    {
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

    public LocalDateTime getRequestedAt()
    {
        return requestedAt;
    }

    public String getAuthorEmail()
    {
        return user.getEmail();
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public Date getDateOfBirth()
    {
        return this.user.getDateOfBirth();
    }

    public Sex getSex()
    {
        return this.user.getSex();
    }

    public int getLength()
    {
        return this.user.getLength();
    }

    public int getWeight()
    {
        return this.user.getWeight();
    }

    public int getAge()
    {
        return this.user.getAge();
    }

    public boolean isResearchCandidate()
    {
        return this.user.isResearchCandidate();
    }
}
