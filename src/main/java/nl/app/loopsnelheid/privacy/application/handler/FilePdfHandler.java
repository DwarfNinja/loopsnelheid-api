package nl.app.loopsnelheid.privacy.application.handler;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.encoder.PrivacyPdfEncoder;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class FilePdfHandler implements Handler
{
    private static final Logger logger = LoggerFactory.getLogger(FilePdfHandler.class);

    private final TemplateEngine templateEngine;
    private PrivacyPdfEncoder privacyPdfEncoder;

    private File file;

    @Override
    public void handle()
    {
        String fileName = TokenGenerator.generateToken() + ".pdf";
        String path = "src/main/resources/assets/privacy/" + fileName;

        Context context = new Context();
        context.setVariables(privacyPdfEncoder.generate());

        String htmlContent = templateEngine.process("personal_information", context);
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();

            file = new File(path);
        }
        catch (FileNotFoundException | DocumentException e) {
            logger.error(e.getMessage());
        }
    }

    public File getFile()
    {
        return file;
    }

    public void setPrivacyPdfEncoder(PrivacyPdfEncoder privacyPdfEncoder)
    {
        this.privacyPdfEncoder = privacyPdfEncoder;
    }
}
