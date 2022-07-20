package nl.app.loopsnelheid.measurement.application.handler;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.encoder.ResearchCsvEncoder;
import nl.app.loopsnelheid.privacy.application.handler.Handler;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class FileCsvHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FileCsvHandler.class);
    private final ResearchCsvEncoder researchCsvEncoder;
    private File file;

    @Override
    public void handle()
    {
        List<String[]> csvData = researchCsvEncoder.create();

        String fileName = TokenGenerator.generateToken() + ".csv";
        String path = "src/main/resources/assets/research/" + fileName;

        try (CSVWriter writer = new CSVWriter(new FileWriter(path)))
        {
            writer.writeAll(csvData);
            file = new File(path);
        } catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }

    public void removeFile()
    {
        file.delete();
    }

    public File getFile()
    {
        return file;
    }
}
