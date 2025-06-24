package com.urise.webapp.util;



import java.io.Reader;
import java.io.Writer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;



public class XMLParser {
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XMLParser(Class... classesToBounded) {
        try {
            JAXBContext context = JAXBContext.newInstance(classesToBounded);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }


    public <T> T unmarshal(Reader r) {
        try {
            return (T) unmarshaller.unmarshal(r);
        } catch (JAXBException error) {
            throw new IllegalStateException(error);
        }

    }

    public void marshal(Object instance, Writer writer) {
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException error) {
            throw new IllegalStateException(error);
        }
    }


}
