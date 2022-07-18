package nl.app.loopsnelheid.measurement.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.handler.Handler;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;

@RequiredArgsConstructor
public class FileJsonHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FileJsonHandler.class);
    private final JSONObject content;
    private File file;

    @Override
    public void handle()
    {
        try
        {
            String path;

            do {
                String fileName = TokenGenerator.generateToken() + ".json";
                path = "src/main/resources/assets/research/" + fileName;
                file = new File(path);

            } while (!file.createNewFile());

            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content.toJSONString());
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
