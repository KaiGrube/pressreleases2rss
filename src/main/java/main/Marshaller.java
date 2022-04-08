package main;

import jakarta.xml.bind.JAXBContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Optional;

public class Marshaller {

    public static <E> void marshallToFile (E element, File file) {
        try {
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(element.getClass());
            jakarta.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(element, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <E> Optional<String> marshallToString (E element) {
        try {
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(element.getClass());
            jakarta.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(element, outputStream);
            return Optional.of(outputStream.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
