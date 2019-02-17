package com.xmlp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import java.util.Scanner;

/**
 * This class will read large XML line by line and then identify XML block to continue with business function
 *<employee>
 *     <bankAccount>1211-1221-1234-2201</bankAccount>
 *     <empId>323</empId>
 *     <empName>Marvin Satterfield</empName>
 *     <salary>8094.9622262787325</salary>
 * </employee>
 *
 * let say above is the structure of the XML, then programe will seek end and start tags to identify a
 * business entry
 */
public class XMLReader {

    private static final String fileName = "E:\\xml\\acc.xml";
    private static final String startTag = "<employee>";
    private static final String endTag = "</employee>";
    private static int processedRecords = 0;

    public static void main(String[] args) {
        readFile();
    }

    private static String readFile(){
        long start = System.currentTimeMillis();
        try(Scanner scanner = new Scanner(new File(fileName))){
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.equals(startTag)){
                    builder = new StringBuilder();
                }
                //System.out.println(line);
                builder.append(line);
                if(line.equals(endTag)){
                    paySalary(builder.toString());
                    processedRecords++;
                    if(processedRecords % 100 == 0){
                        System.out.println("Process Records " + processedRecords + " by " + (System.currentTimeMillis() - start) + " ms.");
                    }
                }



            }
            long end = System.currentTimeMillis();
            System.out.println("Processing Time " + (end - start));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static void paySalary(String xml){
        System.out.println(" ********************************************************************** ");
        try {
            Employee employee = getEmployeeObject(xml);
            if(transferMoney(employee.getEmpName(),employee.getBankAccount(),employee.getSalary())){
                System.out.println("SALARY PAYMENT FOR " + employee.getEmpName() + " OF " + employee.getSalary() +
                        " IS SUCCESS.");
            }else {
                System.out.println("FAIL TO TRANSFE MONEY TO " + employee.getEmpName() + " TO ACC " + employee.getBankAccount() +
                        " AMOUNT ." + employee.getSalary());
            }
            System.out.println(" ********************************************************************** ");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method will be adding some delay and randomly return failures with less probablity
     * to simulate money transfer function.
     * @param name
     * @param account
     * @param amount
     * @return
     */
    private static boolean transferMoney(String name,String account, double amount)  {
        Random random = new Random();
        int val = random.nextInt(15600);
        await();
        if(val > 15000){
            return false;
        }
        return true;
    }

    /**
     * The MAX delay assumed was 500 ms for money transfer action.
     */
    private static void await(){
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Employee getEmployeeObject(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Employee employee = (Employee) unmarshaller.unmarshal(new StringReader(xml));
        return employee;
    }

}
