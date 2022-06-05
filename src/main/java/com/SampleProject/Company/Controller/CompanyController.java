package com.SampleProject.Company.Controller;

import com.SampleProject.Company.Model.Company;
import com.SampleProject.Company.Model.Department;
import com.SampleProject.Company.Model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @PostMapping("/createCompany")
    public Company createCompany(Company company){
        return null;
    }

    @PostMapping("/addDepartment")
    public void addDepartment(Company company, List<Department> departments){
        return;
    }

    @GetMapping("/getCompany")
    public Company getCompany(){ return null; }

    @GetMapping("/getComDepartments")
    public List<Department> getComDepartments(){
        return null;
    }

    @PutMapping("/editComDepartments")
    public void editComDepartments(Company company, List<Department> departments){ return; }

    @DeleteMapping("/deleteDepartment")
    public void deleteDepartment(Company company, Department department){
        return;
    }

    @DeleteMapping("/deleteCompany")
    public void deleteCompany(Company company){
        return;
    }
}
