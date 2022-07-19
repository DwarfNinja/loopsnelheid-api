package nl.app.loopsnelheid.measurement.application.handler;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.encoder.ResearchJsonEncoder;
import nl.app.loopsnelheid.privacy.application.handler.Handler;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;

@RequiredArgsConstructor
public class FileJsonHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FileJsonHandler.class);

    private final ResearchJsonEncoder researchJsonEncoder;
    private File file;

    @Override
    public void handle()
    {
        researchJsonEncoder.encode();
        try
        {
            String path;

            do {
                String fileName = TokenGenerator.generateToken() + ".json";
                path = "src/main/resources/assets/research/" + fileName;
                file = new File(path);

            } while (!file.createNewFile());

            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(researchJsonEncoder.getWrapper().toJSONString());
            fileWriter.close();
        }
        catch (Exception e)
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
