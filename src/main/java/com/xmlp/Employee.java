package com.xmlp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {

    private long empId;
    private String empName;
    private String bankAccount;
    private double salary;


    public Employee(){

    }

    public Employee(long empId, String empName, String bankAccount, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.bankAccount = bankAccount;
        this.salary = salary;
    }

    public long getEmpId() {
        return empId;
    }

    @XmlElement
    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    @XmlElement
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    @XmlElement
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getSalary() {
        return salary;
    }

    @XmlElement
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID " + empId);
        builder.append(", NAME " + empName);
        builder.append(", ACC " + bankAccount);
        builder.append(", SALARY " + salary);
        return builder.toString();
    }
}
