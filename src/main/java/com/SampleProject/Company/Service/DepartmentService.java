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
public class DepartmentService {

    @Autowired
    AerospikeClient client;

    public Department addDepartment(Department department) {
        Key depKey = new Key(NAME_SPACE, DEPARTMENT_SET, department.getName());
        Bin depName = new Bin(DEP_NAME, department.getName());
        Bin depEmployees = new Bin(DEP_EMPLOYEES, department.getEmployeesIds());
        client.put(null, depKey, depName, depEmployees);
        return department;
    }

    public Department getDepartment(String depName) {
        Key depKey = new Key(NAME_SPACE, DEPARTMENT_SET, depName);
        if (client.exists(null, depKey)) {
            Record depRecord = client.get(null, depKey, DEP_NAME, DEP_EMPLOYEES);
            return new Department(depRecord.getString(DEP_NAME), (List<Long>) depRecord.getList(DEP_EMPLOYEES));
        }
        return null;
    }

    public boolean deleteDepartment(String depName) {
        Key depKey = new Key(NAME_SPACE, DEPARTMENT_SET, depName);
        if (client.exists(null, depKey)) {
            client.delete(null, depKey);
            return true;
        }
        return false;
    }

    public List<Department> getAllDepartments() {
        Statement statement = new Statement();
        statement.setNamespace(NAME_SPACE);
        statement.setSetName(DEPARTMENT_SET);
        RecordSet recordSet = client.query(null, statement);
        List<Department> departments = new ArrayList<>();

        while (recordSet.next()) {
            Record currentRecord = recordSet.getRecord();
            Department department = new Department();
            department.setName(currentRecord.getString(DEP_NAME));
            department.setEmployeesIds((List<Long>) currentRecord.getList(DEP_EMPLOYEES));
            departments.add(department);
        }
        return departments;
    }
}
