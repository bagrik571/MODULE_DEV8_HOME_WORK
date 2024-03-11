package org.example.elements;

import java.math.BigDecimal;

public class MaxSalaryCountWorker {
    private String name;
    private String maxSalary;

    public MaxSalaryCountWorker(String name, String maxSalary) {
        this.name = name;
        this.maxSalary = maxSalary;
    }
    @Override
    public String toString(){
        return "Max Salary Count Worker {" +
                "NAME=" + name +
                ", MAX SALARY='" + maxSalary + '\'' +
                '}';
    }
}
