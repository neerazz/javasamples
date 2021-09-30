package com.neeraj.preperation.java.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on:  Sep 29, 2021
 * Ref: https://dzone.com/articles/reading-a-file-while-file-being-written-at-the-sam
 */
public class ReadingFileWhileWrite extends Thread {

    BufferedReader reader = null;
    DataOutputStream writer = null;
    AtomicInteger integer = new AtomicInteger();

    static final String fileName = "app.log";

    public static void main(String[] args) throws IOException {
        ReadingFileWhileWrite tw = new ReadingFileWhileWrite();
        tw.writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        tw.reader = Files.newBufferedReader(Path.of(fileName));
        tw.start();
    }

    public void startReading() {
        System.out.println("Started Reading from File");
        try {
            String strLine;
            while (true) {
                strLine = reader.readLine();
                if (strLine == null) {
                    System.out.println("Sleeping.");
                    sleep(1000);
                } else {
                    /* parse strLine to obtain what you want */
                    System.out.println(LocalDateTime.now() + ": " +  strLine);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startWriting() {
        System.out.println("Started Writing into File");
        try {
            String strLine = "Line ";
            while (true) {
                writer.writeUTF(strLine + integer.getAndIncrement() + "\n");
                writer.flush();
                sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Thread t1 = new Thread(this::startWriting);
        Thread t2 = new Thread(this::startReading);
        t1.start();
        t2.start();
    }
}
