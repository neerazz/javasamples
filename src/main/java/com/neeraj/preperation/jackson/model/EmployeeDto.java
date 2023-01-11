package com.neeraj.preperation.jackson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.neeraj.preperation.hibernate.AddressEntity;
import com.neeraj.preperation.hibernate.DepartmentEntity;
import lombok.Data;

@Data
public class EmployeeDto {


    String firstName;
    String lastName;

    /* To Convert Object to string while converting to Json. */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("departmentName")
    DepartmentEntity departmentEntity;
    AddressEntity addressEntity;
    String state;
}
