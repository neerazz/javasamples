package com.neeraj.preperation.java.concurrency;

import org.junit.jupiter.api.Assertions;

import java.util.concurrent.*;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://www.baeldung.com/java-completablefuture
 */

public class Executors {

//    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        simpleCompletableFutures();
//        processingResultFromCompletableFuture();
//    }

    static void simpleCompletableFutures() throws ExecutionException, InterruptedException, TimeoutException {
//        Traditional Future approach.
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");
//        String result = completableFuture.get();
        String result = completableFuture.get(1, TimeUnit.SECONDS);
        Assertions.assertEquals("Hello", result);

//        Similar to traditional Future approach, but by using a CompletableFuture.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        Assertions.assertEquals("Hello", future.get());
    }

    static void processingResultFromCompletableFuture() throws ExecutionException, InterruptedException {
//        Now the joining is abstracted out, you just have to say what needs to be done after the completion.
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future1 = future.thenApply(preResult -> preResult + " World");
        Assertions.assertEquals("Hello World", future1.get());

//        If it is not required to return any thing, but want to consume the response, then use the method thenAccept.
        CompletableFuture<Void> future2 = future.thenAccept(s -> System.out.println("Computation returned: " + s));
        future2.get();

//        If it is not required to return any thing, and don't worry on what was returned, then use the method thenRun.
        CompletableFuture<Void> future3 = future.thenRun(() -> System.out.println("Computation finished."));
        future3.get();

//        You can also compose, another CompletableFuture, from the previous result. like chaining.
        CompletableFuture<String> future4 = future.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        Assertions.assertEquals("Hello World", future4.get());
    }
}
