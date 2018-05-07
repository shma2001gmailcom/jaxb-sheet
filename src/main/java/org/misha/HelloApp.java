package org.misha;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

public class HelloApp {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Jaxb2Marshaller jaxbMarshaller = (Jaxb2Marshaller) context.getBean("jaxbMarshaller");
        Map<String, Boolean> marshallerProperties = new HashMap<>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setMarshallerProperties(marshallerProperties);
        final Logger log = (Logger) context.getBean("log");
        BookXMLApp.marchallEmployee(jaxbMarshaller, log);
        BookXMLApp.unMarshallEmployee(jaxbMarshaller, log);
        BookXMLApp.marshallBook(jaxbMarshaller, log);
        
    }
}
