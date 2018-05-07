package org.misha;

import org.apache.log4j.Logger;
import org.misha.book.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookXMLApp {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Jaxb2Marshaller jaxbMarshaller = (Jaxb2Marshaller) context.getBean("jaxbMarshaller");
        Logger log = (Logger) context.getBean("log");
        Map<String, Boolean> marshallerProperties = new HashMap<>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setMarshallerProperties(marshallerProperties);
        marshallBook(jaxbMarshaller, log);
        unMarshallBook(jaxbMarshaller, log);
        marchallEmployee(jaxbMarshaller, log);
        unMarshallEmployee(jaxbMarshaller, log);
    }
    
    static void marshallBook(final Jaxb2Marshaller jaxbMarshaller, final Logger log) {
        Book book = new Book();
        book.setTitle("Test-Driven Development by Example");
        try (OutputStream out = new ByteArrayOutputStream()) {
            StreamResult result = new StreamResult(out);
            jaxbMarshaller.marshal(book, result);
            log.debug("***\n" + out.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    private static void unMarshallBook(final Jaxb2Marshaller jaxb2Marshaller, final Logger log) {
        try (FileInputStream inputStream = new FileInputStream(new File("model/book.xml"))) {
            StreamSource source = new StreamSource(inputStream);
            Book book = (Book) jaxb2Marshaller.unmarshal(source);
            log.debug("*******");
            log.debug("Title: " + book.getTitle());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    static void unMarshallEmployee(final Jaxb2Marshaller jaxbMarshaller, final Logger log) {
        Employee employee = null;
        try (FileInputStream inputStream = new FileInputStream(new File("model/employee.xml"))) {
            StreamSource source = new StreamSource(inputStream);
            employee = (Employee) jaxbMarshaller.unmarshal(source);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        if (employee == null) {
            return;
        }
        log.debug("Emp Id is " + employee.getId());
        log.debug("Emp Name is " + employee.getName());
        Department department = employee.getDepartment();
        log.debug("Dept Id is " + department.getId());
        log.debug("Dept Name is " + department.getName());
    }
    
    static void marchallEmployee(final Jaxb2Marshaller jaxbMarshaller, final Logger log) {
        Department department = new Department();
        department.setId("IT");
        department.setName("IT Department");
        Employee employee = new Employee("12345", "Test Employee");
        employee.setDepartment(department);
        try (FileOutputStream outputStream = new FileOutputStream(new File("model/employee.xml"))) {
            StreamResult result = new StreamResult(outputStream);
            jaxbMarshaller.marshal(employee, result);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
