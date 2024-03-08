package com.example.groupware.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    @Id
    private Integer employeeId = UUID.randomUUID().hashCode();

    private String employeeName;

    private Long phoneNumber;

    private String  email;

    private Integer  reportsTo;

    private String  profileImage;


}
