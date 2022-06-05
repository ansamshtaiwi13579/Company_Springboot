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
public class DepartmentService {

    @Autowired
    AerospikeClient client;

    public Department addDepartment(Department department) {
        Key depKey = new Key("test", "department", department.getName());
        Bin depName = new Bin("depName", department.getName());
        Bin depEmployees = new Bin("depEmployees", department.getEmployeesIds());
        client.put(null, depKey, depName, depEmployees);
        return department;
    }

    public Department getDepartment(String depName) {
        Key depKey = new Key("test", "department", depName);
        if (client.exists(null, depKey)) {
            Record depRecord = client.get(null, depKey, "depName", "depEmployees");
            return new Department(depRecord.getString("depName"), (List<Long>) depRecord.getList("depEmployees"));
        }
        return null;
    }

    public boolean deleteDepartment(String depName) {
        Key depKey = new Key("test", "department", depName);
        if (client.exists(null, depKey)) {
            client.delete(null, depKey);
            return true;
        }
        return false;
    }

    public List<Department> getAllDepartments() {
        Statement statement = new Statement();
        statement.setNamespace("test");
        statement.setSetName("department");
        RecordSet recordSet = client.query(null, statement);
        List<Department> departments = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            Department department = new Department();
            department.setName(currentRecord.getString("depName"));
            department.setEmployeesIds((List<Long>) currentRecord.getList("depEmployees"));
            departments.add(department);
        }
        return departments;
    }
}
