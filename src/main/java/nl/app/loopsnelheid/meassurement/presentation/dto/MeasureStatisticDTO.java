package nl.app.loopsnelheid.meassurement.presentation.dto;

public class MeasureStatisticDTO
{
    private final String type;
    private final double averageSpeed;
    private final int amountOfMeasures;

    public MeasureStatisticDTO(String type, double averageSpeed, int amountOfMeasures)
    {
        this.type = type;
        this.averageSpeed = averageSpeed;
        this.amountOfMeasures = amountOfMeasures;
    }

    public String getType()
    {
        return type;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public int getAmountOfMeasures() {
        return amountOfMeasures;
    }
}
