package com.SampleProject.Company.Controller;

import com.SampleProject.Company.Model.Employee;

import com.SampleProject.Company.Model.Department;
import com.SampleProject.Company.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("")
    public Department addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @GetMapping("/{depName}")
    public Department getDepartment(@PathVariable String depName){ return departmentService.getDepartment(depName); }

    @GetMapping("")
    public List<Department> getAllDepartments(){ return departmentService.getAllDepartments(); }

    @DeleteMapping("/{depName}")
    public boolean deleteDepartment(@PathVariable String depName){
        return departmentService.deleteDepartment(depName);
    }


    @PostMapping("/{depName}/employees/{id}")
    public void addEmployee(@PathVariable String depName, @PathVariable Long id){
        return;
    }

    @GetMapping("/{depName}/employees")
    public List<Employee> getDepEmployees(@PathVariable String depName){
        return null;
    }

    @DeleteMapping("/{depName}/employees/{id}")
    public void deleteEmployee(@PathVariable String depName, @PathVariable Long id){
        return;
    }


}
