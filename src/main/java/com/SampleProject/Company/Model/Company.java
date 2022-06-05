package com.SampleProject.Company.Model;

import java.util.List;

public class Company {

    private final String compName;
    private List<Department> departments;

    Company(String compName, List<Department> departments){
        this.compName = compName;
        this.departments = departments;
    }

    String getCompName(){
        return compName;
    }
    List<Department> getDepartments(){
        return departments;
    }
}
