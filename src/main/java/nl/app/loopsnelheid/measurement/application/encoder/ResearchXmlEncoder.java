package nl.app.loopsnelheid.measurement.application.encoder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidate;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidateMeasure;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ResearchXmlEncoder implements XmlEncoder
{
    private final ResearchData researchData;

    @Override
    public void write(OutputStream outputStream) throws XMLStreamException
    {
        String pattern = "yyyy.mm.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = xmlOutputFactory.createXMLStreamWriter(outputStream);

        writer.writeStartDocument("utf-8", "1.0");

        // <loopsnelheid>
        writer.writeStartElement("meting-loopsnelheid");

        writer.writeStartElement("aanvrager");
        writer.writeCharacters(researchData.getExportedBy());
        writer.writeEndElement();

        writer.writeStartElement("aanmaak-datum-bestand");
        writer.writeCharacters(simpleDateFormat.format(researchData.getExportedOn()));
        writer.writeEndElement();

        writer.writeStartElement("specificatie-nr");
        writer.writeCharacters("Specificatie80");
        writer.writeEndElement();

        // <gebruikers>
        writer.writeStartElement("gebruikers");
        for (ResearchDataCandidate candidate : researchData.getCandidates())
        {
            // <candidate>
            writer.writeStartElement("gebruiker");
            writer.writeAttribute("gebruiker-id", candidate.getId().toString());

            writer.writeStartElement("geslacht");
            writer.writeCharacters(candidate.getSex().toString().equals("MALE") ? "1" : "2");
            writer.writeEndElement();

            writer.writeStartElement("geboortejaar");
            writer.writeCharacters(candidate.getBirthYear());
            writer.writeEndElement();

            writer.writeStartElement("lengte");
            writer.writeAttribute("maateenheid", "cm");
            writer.writeCharacters(Integer.toString(candidate.getLength()));
            writer.writeEndElement();

            writer.writeStartElement("gewicht");
            writer.writeAttribute("gewichtseenheid", "kg");
            writer.writeCharacters(Integer.toString(candidate.getWeight()));
            writer.writeEndElement();

            // <measures>
            writer.writeStartElement("metingen");
            writer.writeAttribute("aantal", Integer.toString(candidate.getMeasures().size()));
            for (ResearchDataCandidateMeasure measure : candidate.getMeasures())
            {
                DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

                // <measure>
                writer.writeStartElement("meting");
                writer.writeAttribute("meting-id", measure.getId().toString());

                writer.writeStartElement("datum-meting");
                writer.writeCharacters(measure.getRegisteredAt().format(formatDate));
                writer.writeEndElement();

                writer.writeStartElement("tijd-meting");
                writer.writeCharacters(measure.getRegisteredAt().format(formatTime));
                writer.writeEndElement();

                writer.writeStartElement("gemiddelde-snelheid");
                writer.writeAttribute("snelheid", "km/u");
                writer.writeCharacters(Double.toString(measure.getWalkingSpeed()));
                writer.writeEndElement();

                writer.writeEndElement();
                // </measure>
            }
            writer.writeEndElement();
            // <measures>

            writer.writeEndElement();
            // </candidate>
        }
        writer.writeEndElement();
        // </gebruikers>

        writer.writeEndDocument();
        // </loopsnelheid>

        writer.flush();

        writer.close();

    }

    @Override
    public String format(String xml) throws TransformerException
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // add standalone="yes", add line break before the root element
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource streamSource = new StreamSource(new StringReader(xml));
        StringWriter stringWriter = new StringWriter();
        transformer.transform(streamSource, new StreamResult(stringWriter));

        return stringWriter.toString();
    }
}
