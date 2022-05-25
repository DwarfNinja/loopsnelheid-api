package nl.app.loopsnelheid.privacy.application;

import nl.app.loopsnelheid.privacy.domain.PersonalData;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;

import java.util.Date;

public class PersonalDataRetriever implements UserRetriever
{
    private PersonalData personalData;

    @Override
    public void retrieve(User user)
    {
        String email = user.getEmail();
        Date dateOfBirth = user.getDateOfBirth();
        Sex sex = user.getSex();

        personalData = new PersonalData(email, dateOfBirth, sex);
    }

    public PersonalData getPersonalData() {
        return personalData;
    }
}
