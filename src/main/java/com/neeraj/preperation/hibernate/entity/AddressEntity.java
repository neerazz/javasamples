package com.neeraj.preperation.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table
public class AddressEntity {

    @Id
    private long id;
    private String line1;
    private String line2;
    private String city;
    private String zip;
}
