package nl.app.loopsnelheid.privacy.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.domain.DataRequestContent;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;

@RequiredArgsConstructor
public class FileHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private final DataRequestContent dataRequestContent;
    private File file;

    @Override
    public void handle()
    {
        try
        {
            String path;

            do {
                String fileName = TokenGenerator.generateToken() + ".json";
                path = "src/main/resources/assets/" + fileName;
                file = new File(path);

            } while (!file.createNewFile());

            ObjectMapper objectMapper = new ObjectMapper();

            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataRequestContent));
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
