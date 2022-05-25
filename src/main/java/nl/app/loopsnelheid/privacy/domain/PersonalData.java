package nl.app.loopsnelheid.privacy.domain;

import nl.app.loopsnelheid.security.domain.Sex;

import java.util.Date;

public class PersonalData
{
    private final String email;
    private final Date dateOfBirth;
    private final Sex sex;

    public PersonalData(String email, Date dateOfBirth, Sex sex) {
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }
}
