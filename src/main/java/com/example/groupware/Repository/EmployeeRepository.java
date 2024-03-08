package com.example.groupware.Repository;

import com.example.groupware.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,Integer> {

}
