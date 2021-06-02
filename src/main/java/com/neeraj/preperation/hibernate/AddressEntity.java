package com.neeraj.preperation.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import java.io.*;

@Entity
public class AddressEntity {

    @Id
    private long id;
    private String line1;
    private String line2;
    private String city;
    private String zip;
}
