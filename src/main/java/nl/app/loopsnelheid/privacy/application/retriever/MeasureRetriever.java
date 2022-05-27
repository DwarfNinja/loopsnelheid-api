package nl.app.loopsnelheid.privacy.application.retriever;

import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.security.domain.User;

import java.util.*;

public class MeasureRetriever implements UserRetriever
{
    private List<Measure> measureList = new ArrayList<>();

    @Override
    public void retrieve(User user)
    {
        List<Measure> measures = user.getMeasures();
        Comparator<Measure> comparator = (c1, c2) -> (int) c2.getRegisteredAt().compareTo(c1.getRegisteredAt());
        measures.sort(comparator);
        measureList = measures;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }
}
