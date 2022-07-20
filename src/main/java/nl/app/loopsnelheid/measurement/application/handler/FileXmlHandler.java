package nl.app.loopsnelheid.measurement.application.handler;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.encoder.ResearchXmlEncoder;
import nl.app.loopsnelheid.privacy.application.handler.Handler;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class FileXmlHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FileXmlHandler.class);
    private final ResearchXmlEncoder researchXmlEncoder;

    private File file;

    @Override
    public void handle()
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            researchXmlEncoder.write(outputStream);
            String xml = outputStream.toString(StandardCharsets.UTF_8);
            String prettyPrintXML = researchXmlEncoder.format(xml);
            String fileName = TokenGenerator.generateToken() + ".xml";
            String path = "src/main/resources/assets/research/" + fileName;
            Files.writeString(Paths.get(path), prettyPrintXML, StandardCharsets.UTF_8);

            file = new File(path);
        }
        catch (TransformerException | XMLStreamException | IOException e)
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
