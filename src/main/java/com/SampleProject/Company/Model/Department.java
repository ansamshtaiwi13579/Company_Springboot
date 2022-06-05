package com.SampleProject.Company.Model;

import java.util.List;

public class Department {

    private String name;
    private List<Long> employeesIds;

    public Department() {
    }

    public Department(String name, List<Long> employeesIds){
        this.name = name;
        this.employeesIds = employeesIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Long> getEmployeesIds() {
        return employeesIds;
    }

    public void setEmployeesIds(List<Long> employeesIds) {
        this.employeesIds = employeesIds;
    }
}
