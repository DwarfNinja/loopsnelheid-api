package nl.app.loopsnelheid.measurement.application.handler;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.handler.Handler;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
public class ArchiveHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(ArchiveHandler.class);
    private final Set<File> files;
    private String path;
    @Override
    public void handle()
    {
        String archiveName = TokenGenerator.generateToken() + ".zip";
        String path = "src/main/resources/assets/research/" + archiveName;

        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            for (File file : files)
            {
                FileInputStream fileInputStream = new FileInputStream(file);

                zipOutputStream.putNextEntry(new ZipEntry("onderzoeksgegevens.json"));

                int length;

                byte[] buffer = new byte[1024];
                while ((length = fileInputStream.read(buffer)) > 0)
                {
                    zipOutputStream.write(buffer, 0, length);
                }

                zipOutputStream.closeEntry();
                fileInputStream.close();
            }

            zipOutputStream.close();
            this.path = path;
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }

    public String getPath()
    {
        return path;
    }
}
