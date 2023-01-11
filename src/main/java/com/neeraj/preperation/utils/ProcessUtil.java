package com.neeraj.preperation.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class ProcessUtil {
    public static String OS_NAME = "os.name";

    private ProcessUtil() {
    }

    public static boolean isWindows() {
        String osName = System.getProperty(OS_NAME);
        log.debug("Operating system is : {}", osName);
        return osName.toLowerCase().startsWith("windows");
    }

    public static boolean isLinux() {
        String osName = System.getProperty(OS_NAME);
        log.debug("Operating system is : {}", osName);
        return osName.toLowerCase().startsWith("linux");
    }

    public static boolean isMac() {
        String osName = System.getProperty(OS_NAME);
        log.debug("Operating system is : {}", osName);
        return osName.toLowerCase().startsWith("mac");
    }

    public static Integer runCommandWithResponseCode(String cmd, File baseDir) {
        var response = runCommandWithProcessResponse(cmd, baseDir);
        return response.statusCode;
    }

    public static List<String> runCommandWithRawResponse(String cmd, File baseDir) {
        var response = runCommandWithProcessResponse(cmd, baseDir);
        return response.responseLines;
    }

    public static ProcessResponse runCommandWithProcessResponse(String cmd, File baseDir) {
        log.info("Running Command : {} at Directory : {}", cmd, baseDir);
        Process process = getProcess(cmd, baseDir);
        ProcessResponse response = new ProcessResponse();
        if (process == null) return response;
        try {
            StreamGobbler streamGobbler = new StreamGobbler(process, response.responseConsumer);
            streamGobbler.run();
            response.statusCode = process.waitFor();
            log.info("Process exitValue: {}", response.statusCode);
            log.debug("Command Response : ");
            var responseLines = response.responseLines;
            responseLines.forEach(log::debug);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static Process getProcess(String cmd, File baseDir) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if (baseDir == null) {
                baseDir = Path.of(System.getProperty("user.home")).toFile();
            }
            pb.command(buildCommand(cmd));
            pb.directory(baseDir);
            return pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> buildCommand(String cmd) {
        List<String> commands = new ArrayList<>();
        if (isMac() || isLinux()) {
            commands.add("sh");
            commands.add("-c");
        } else if (isWindows()) {
            commands.add("cmd");
            commands.add("/c");
        }
        commands.add(cmd);
        return commands;
    }

    @Data
    public static class ProcessResponse {
        int statusCode = Integer.MAX_VALUE;
        List<String> responseLines = new ArrayList<>();
        Consumer<String> responseConsumer = responseLines::add;
        Supplier<String> getResponse = () -> String.join("\n", responseLines);

        public boolean isValid() {
            return statusCode == 0;
        }
    }

    private static class StreamGobbler implements Runnable {
        private final Process process;
        private final Consumer<String> outputConsumer;
        private final Consumer<String> errorConsumer;

        public StreamGobbler(Process process, Consumer<String> consumer) {
            this.process = process;
            this.outputConsumer = consumer;
            this.errorConsumer = consumer;
        }

        @Override
        public void run() {
            processStream(process.getInputStream(), outputConsumer);
            processStream(process.getErrorStream(), errorConsumer);
        }

        private void processStream(InputStream inputStream, Consumer<String> consumer) {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }

    }
}
