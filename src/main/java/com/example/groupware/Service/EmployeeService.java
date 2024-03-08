package com.example.groupware.Service;

import com.example.groupware.Model.Employee;
import com.example.groupware.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveOrUpdateEmployee(Employee employee) {
        System.out.println(employee);
        return employeeRepository.save(employee);
    }

    // Read
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Delete
    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }
}
