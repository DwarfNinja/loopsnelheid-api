package nl.app.loopsnelheid.privacy.application.handler;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
public class ArchiveHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(ArchiveHandler.class);
    private final File file;
    private String path;
    @Override
    public void handle()
    {
        try
        {
            String fileName = TokenGenerator.generateToken() + ".zip";
            String path = "src/main/resources/assets/" + fileName;

            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry("data.json");
            zipOutputStream.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;

            while ((length = fileInputStream.read(bytes)) >= 0)
            {
                zipOutputStream.write(bytes, 0, length);
            }

            zipOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();

            this.path = path;
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
    }

    public String getPath()
    {
        return path;
    }
}
