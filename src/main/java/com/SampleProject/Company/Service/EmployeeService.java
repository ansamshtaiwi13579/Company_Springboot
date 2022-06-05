package com.SampleProject.Company.Service;

import com.SampleProject.Company.Model.Department;
import com.SampleProject.Company.Model.Employee;
import com.SampleProject.Company.Model.Role;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    AerospikeClient client;

    public Employee addEmployee(Employee employee) {
        Key employeeKey = new Key("test", "employee", employee.getId());
        Bin employeeId = new Bin("employeeId", employee.getId());
        Bin employeeName = new Bin("employeeName", employee.getName());
        Bin employeeRole = new Bin("employeeRole", employee.getRole());
        Bin employeeDepartment = new Bin("empDepName", employee.getDepName());
        client.put(null, employeeKey, employeeId, employeeName,employeeRole, employeeDepartment);
        return employee;
    }

    public Employee getEmployee(Long id) {
        Key employeeKey = new Key("test", "employee", id);
        if (client.exists(null, employeeKey)) {
            Record employeeRecord = client.get(null, employeeKey, "employeeId", "employeeName", "employeeRole", "empDepName");
            return new Employee(employeeRecord.getLong("employeeId"), employeeRecord.getString("employeeName"), (Role) employeeRecord.getValue("employeeRole"), employeeRecord.getString("empDepName"));
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        Key employeeKey = new Key("test", "employee", id);
        if (client.exists(null, employeeKey)) {
            client.delete(null, employeeKey);
            return true;
        }
        return false;
    }

    public List<Employee> getAllEmployees() {
        Statement statement = new Statement();
        statement.setNamespace("test");
        statement.setSetName("employee");
        RecordSet recordSet = client.query(null, statement);
        List<Employee> employees = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            Employee employee = new Employee();
            employee.setId(currentRecord.getLong("employeeId"));
            employee.setName(currentRecord.getString("employeeName"));
            String role = currentRecord.getString("employeeRole");
            if (role != null) {
                employee.setRole(Role.valueOf(role));
            }
            employee.setDepName(currentRecord.getString("empDepName"));
            employees.add(employee);
        }
        return employees;
    }
}
