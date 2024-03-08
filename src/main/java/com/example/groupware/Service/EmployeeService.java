package com.example.groupware.Service;

import com.example.groupware.Model.Employee;
import com.example.groupware.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {

    @Autowired

    private EmployeeRepository employeeRepository;

    // Create
    public Employee saveEmployee(Employee employee) {
        try {

            Optional<Employee> existingEmployee = employeeRepository.findByEmployeeName(employee.getEmployeeName());
            System.out.println(existingEmployee);
            if (existingEmployee.isPresent()) {
                throw new IllegalArgumentException("Employee with the same name already exists.");
            }

            // Generate unique UUID for the employee
            String employeeId = UUID.randomUUID().toString();
            employee.setEmployeeId(employeeId);

            employeeRepository.save(employee);

            return employee;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get by id
    public Optional<Employee> getEmployeeById(String id) {

        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get by name
    public Optional<Employee> getEmployee(String name) {
        try {

            Optional<Employee> existingEmployee = employeeRepository.findByEmployeeName(name);

            if (existingEmployee.isPresent()) {
                throw new IllegalArgumentException("Employee with the same name already exists.");
            }

            return existingEmployee;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees(int pageNum, int pageSize, String sortBy) {
        try {
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
            Page<Employee> employees = employeeRepository.findAll(pageable);
            return employees.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee updateEmployeeById(String employeeId, Employee employee){
        try {
            // Check if the employee with the given ID exists
            Optional<Employee> existingEmployeeOptional = employeeRepository.findById(employeeId);
            if (existingEmployeeOptional.isEmpty()) {
                throw new IllegalArgumentException("Employee with ID " + employeeId + " not found.");
            }

            Employee existingEmployee = existingEmployeeOptional.get();

            // Check if the employee name is being updated to an existing employee's name
            Optional<Employee> existingEmployeeWithUpdatedName = employeeRepository.findByEmployeeName(employee.getEmployeeName());
            if (existingEmployeeWithUpdatedName.isPresent() && !existingEmployeeWithUpdatedName.get().getEmployeeId().equals(employeeId)) {
                throw new IllegalArgumentException("Another employee with the same name already exists.");
            }

            // Update the existing employee fields

            if(employee.getEmployeeName() != null){
                existingEmployee.setEmployeeName((employee.getEmployeeName()));
            }

            if (employee.getPhoneNumber() != null) {
                existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            }
            if (employee.getEmail() != null) {
                existingEmployee.setEmail(employee.getEmail());
            }
            if (employee.getReportsTo() != null) {
                existingEmployee.setReportsTo(employee.getReportsTo());
            }
            if (employee.getProfileImage() != null) {
                existingEmployee.setProfileImage(employee.getProfileImage());
            }

            // Save the updated employee to the database
            return employeeRepository.save(existingEmployee);
        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException here
            e.printStackTrace(); // Log the exception for debugging purposes
            throw e; // Re-throw the exception to propagate it
        } catch (Exception e) {
            // Catch any other exceptions
            e.printStackTrace(); // Log the exception for debugging purposes
            return null;
        }
    }

    // Delete
    public void deleteEmployeeById(String id) {
        try{
            employeeRepository.deleteById(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public Employee managerWithNthLevel(String employeeId,int level){
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
            if (employeeOptional.isEmpty()) {
                throw new IllegalArgumentException("Employee doesn't have manager at level "+(level+4) );
            }

            Employee employee = employeeOptional.get();
            String managerId = employee.getReportsTo();

            // Base case: if the employee is at the top of the hierarchy or level is 0
            if (managerId == null || level == 0) {
                return employee;
            }

            // Recursively find the nth level manager
            return managerWithNthLevel(managerId, level - 1);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
