package nl.app.loopsnelheid.privacy.application.handler;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.Measure;
import nl.app.loopsnelheid.privacy.application.retriever.MeasureRetriever;
import nl.app.loopsnelheid.privacy.application.retriever.PersonalDataRetriever;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.DataRequestContent;
import nl.app.loopsnelheid.privacy.domain.PersonalData;

import java.util.List;

@RequiredArgsConstructor
public class DataRequestHandler implements Handler
{
    private final DataRequest dataRequest;
    private DataRequestContent dataRequestContent;

    @Override
    public void handle()
    {
        PersonalDataRetriever personalDataRetriever = new PersonalDataRetriever();
        personalDataRetriever.retrieve(dataRequest.getUser());
        PersonalData personalData = personalDataRetriever.getPersonalData();

        MeasureRetriever measureRetriever = new MeasureRetriever();
        measureRetriever.retrieve(dataRequest.getUser());
        List<Measure> measures = measureRetriever.getMeasureList();

        dataRequestContent = new DataRequestContent(personalData, measures);
    }

    public DataRequestContent getDataRequestContent()
    {
        return dataRequestContent;
    }
}
