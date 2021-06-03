package com.neeraj.preperation.java.oops.imutability;

import lombok.Getter;

import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 03, 2021
 * Ref: https://howtodoinjava.com/java/serialization/java-externalizable-example/
 */

@Getter
public class Address implements Externalizable {
    private String line1;
    private String line2;
    private String city;
    private String zip;

    public static final String delimiter = "\t";

//    Empty constructor is must because of Externalizable
    public Address() {
    }

    //    Create a private constructor.
    public Address(String line1, String line2, String city, String zip) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.zip = zip;
    }

    //    Create a clone Method.
    @Override
    protected Address clone() {
        return new Address(line1, line2, city, zip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(line1, address.line1)) return false;
        if (!Objects.equals(line2, address.line2)) return false;
        if (!Objects.equals(city, address.city)) return false;
        return Objects.equals(zip, address.zip);
    }

    @Override
    public int hashCode() {
        int result = line1 != null ? line1.hashCode() : 0;
        result = 31 * result + (line2 != null ? line2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
//        Store all the values as String by delimiter.
        List<String> fields = Arrays.asList(line1, line2, city, zip);
        out.writeObject(String.join(delimiter, fields));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //        Get all the values as String, split it by delimiter
        String input = (String) in.readObject();
        String[] fields = input.split(delimiter);
        line1 = fields[0];
        line2 = fields[1];
        city = fields[2];
        zip = fields[3];
    }
}
