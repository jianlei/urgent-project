package com.daren.middleware.couchdb.model;

/**
 * Created by dell on 14-3-20.
 */

/*
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lightcouch.CouchDbClient;*/
public class CouchDbClientLoadTest {
    /*private static CouchDbClient dbClient;

    private static final int NUM_THREADS = 1;
    private static final int NUM_DOCS = 1;

    @BeforeClass
    public static void setUpClass() {
        dbClient = new CouchDbClient();
    }

    @AfterClass
    public static void tearDownClass() {
        dbClient.shutdown();
    }

    @Test
    public void clientsLoadTest() {
        // init
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // start
        System.out.println("--------------- Load Test Running ...");
        StopWatch.start();
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(new MyRunnable());
        }
        executor.shutdown();
        do { *//* waiting *//* }
        while (!executor.isTerminated());

        // result
        long elapsed = StopWatch.stop();
        long seconds = elapsed / 1000;
        int totalDocs = NUM_THREADS * NUM_DOCS;

        StringBuilder sb = new StringBuilder();
        sb.append("--------------- Load Test Ended:");
        sb.append("\n* Thread count: " + NUM_THREADS);
        sb.append(", Docs per thread: " + NUM_DOCS);
        sb.append("\n* Saved total new documents: " + totalDocs);
        sb.append(String.format("\n* Elapsed time: %s seconds, %s ms.", seconds, elapsed - (seconds * 1000)));
        sb.append("\n* Average persisting time: " + (elapsed / totalDocs) + " ms per Document.");
        System.out.println(sb);
    }

    private class MyRunnable implements Runnable {

        public void run() {
            for (int i = 0; i < NUM_DOCS; i++) {
                dbClient.save(new Foo());
            }
        }
    }*/

}