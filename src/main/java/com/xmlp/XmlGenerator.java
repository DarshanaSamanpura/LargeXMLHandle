package com.xmlp;

import com.github.javafaker.Faker;
import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * this class will generate large XML file with random populated data
 *
 */

public class XmlGenerator {

    private static Faker faker = new Faker();
    private static final String filePath = "E:\\xml\\acc.xml";

    public static void main(String[] args) throws JAXBException {

        File file = new File(filePath);
        CharSink chs = Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);

        for(int i = 0; i < 1000000; i++){

            String xml = cleanXml(getXml(getRandomEmp(i)));


            try {
                writeToFile(xml,chs);
                if(i % 500 == 0){
                    System.out.println("Loop value " + i);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static void writeToFile(String xml,CharSink chs) {
        try {
            chs.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Employee getRandomEmp(long id){

        String name = faker.name().fullName();
        String account = faker.business().creditCardNumber();
        double salary = ThreadLocalRandom.current().nextDouble(3500,35000);
        return new Employee(id,name,account,salary);
    }

    private static String getXml(Employee employee) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(employee,stringWriter);
        return stringWriter.toString();
    }

    private static String cleanXml(String xml){
        String[] arr = xml.split("\\n");
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < arr.length;i++){
            builder.append(arr[i]);
            builder.append("\n");
        }
        return builder.toString();
    }

}
