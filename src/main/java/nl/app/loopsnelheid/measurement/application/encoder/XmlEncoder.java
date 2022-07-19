package nl.app.loopsnelheid.measurement.application.encoder;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.OutputStream;

public interface XmlEncoder
{
    void write(OutputStream outputStream) throws XMLStreamException;

    String format(String xml) throws TransformerException;
}
