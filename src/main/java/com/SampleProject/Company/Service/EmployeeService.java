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

import static com.SampleProject.Company.Const.Consts.*;

@Service
public class EmployeeService {
    @Autowired
    AerospikeClient client;

    public Employee addEmployee(Employee employee) {
        Key employeeKey = new Key(NAME_SPACE, EMP_SET, employee.getId());
        Bin employeeId = new Bin(EMP_ID, employee.getId());
        Bin employeeName = new Bin(EMP_NAME, employee.getName());
        Bin employeeRole = new Bin(EMP_ROLE, employee.getRole());
        Bin employeeDepartment = new Bin(EMP_DEP_NAME, employee.getDepName());
        client.put(null, employeeKey, employeeId, employeeName,employeeRole, employeeDepartment);
        return employee;
    }

    public Employee getEmployee(Long id) {
        Key employeeKey = new Key(NAME_SPACE, EMP_SET, id);
        if (client.exists(null, employeeKey)) {
            Record employeeRecord = client.get(null, employeeKey, EMP_ID, EMP_NAME, EMP_ROLE, EMP_DEP_NAME);
            return new Employee(employeeRecord.getLong(EMP_ID), employeeRecord.getString(EMP_NAME), (Role) employeeRecord.getValue(EMP_ROLE), employeeRecord.getString(EMP_DEP_NAME));
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        Key employeeKey = new Key(NAME_SPACE, EMP_SET, id);
        if (client.exists(null, employeeKey)) {
            client.delete(null, employeeKey);
            return true;
        }
        return false;
    }

    public List<Employee> getAllEmployees() {
        Statement statement = new Statement();
        statement.setNamespace(NAME_SPACE);
        statement.setSetName(EMP_SET);
        RecordSet recordSet = client.query(null, statement);
        List<Employee> employees = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            Employee employee = new Employee();
            employee.setId(currentRecord.getLong(EMP_ID));
            employee.setName(currentRecord.getString(EMP_NAME));
            String role = currentRecord.getString(EMP_ROLE);
            if (role != null) {
                employee.setRole(Role.valueOf(role));
            }
            employee.setDepName(currentRecord.getString(EMP_DEP_NAME));
            employees.add(employee);
        }
        return employees;
    }
}
