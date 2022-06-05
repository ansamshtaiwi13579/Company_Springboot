package com.SampleProject.Company.Model;


import org.luaj.vm2.ast.Str;

public class Employee extends User {

    private Role role;
    private String depName;
    public Employee() {
    }

    public Employee(long empID, String empName, Role role, String  depName){
        super(empID, empName);
        this.role = role;
        this.depName = depName;
    }

    public Role getRole(){
        return role;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
