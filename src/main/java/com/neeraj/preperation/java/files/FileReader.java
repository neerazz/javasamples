package com.neeraj.preperation.java.files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created on:  Sep 29, 2021
 * Ref: https://stackoverflow.com/questions/13934818/reading-log-file-in-java
 */
public class FileReader {
    public static void main(String[] args) {
        readFile("app.log");
    }

    private static void readFile(String fileName) {
        try {
            FileInputStream stream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                /* parse strLine to obtain what you want */
                System.out.println(strLine);
            }
            stream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
