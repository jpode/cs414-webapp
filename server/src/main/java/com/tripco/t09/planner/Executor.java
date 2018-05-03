package com.tripco.t09.planner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Executor {

  // Get the number of cores allotted to the JVM.
  private final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

  /*
   * ExecutorServices are one of the easiest ways to multi-thread Java programs.
   * Rather than explicitly creating Thread objects as you did in CS 370,
   * the ExecutorService creates a thread pool. The programmer passes objects
   * that implement the Callable interface to the ExecutorService using either
   * the invokeAll method or the submit method, and the object's call method
   * will be scheduled for execution by one of the threads in the pool.
   *
   * If you've used other threading methods in Java before, Callable
   * objects are like Runnable objects but with a return type. Rather
   * than a run method, they have a call method. Runnable objects can be
   * converted to Callable objects with the Executors.callable method.
   */
  private final ExecutorService executor;
  private ArrayList<Place> places;

  /**
   * Starts up an ExecutorService with a fixed size thread pool.
   */
  public Executor(ArrayList<Place> places) {
    // Use one thread per core.
    executor = Executors.newFixedThreadPool(NUM_THREADS);
    this.places = places;
    // Fill the array with random integers
  }

  /**
   * Computes a 3-opt optimized trip for the given
   * list of places.
   * @return List of places in optimized order.
   */
  public ArrayList<Place> ThreeOpt() {

    // The invokeAll method of ExecutorService objects accepts an List of
    // Callable tasks that it schedules for execution.
    ArrayList<Callable<Route>> tasks = new ArrayList<>(NUM_THREADS);
    // Each thread should operate on roughly the same size of the array
    int tasksPerThread = places.size() / NUM_THREADS;
    for (int i = 0; i < NUM_THREADS; i++) {
      int startIndex = i * tasksPerThread;
      int endIndex;

      // If the length of the array doesn't divide evenly into NUM_THREADS,
      // assign the rest to the last thread.
      if (i == NUM_THREADS - 1) {
        endIndex = places.size();
      } else {
        endIndex = startIndex + tasksPerThread;
      }
      tasks.add(new ThreeOpt(places, startIndex, endIndex));
    }
        /*
         * Invoke the the call() method of each ArrayMinTask object in the tasks List.
         */
    List<Future<Route>> routes;
    try {
      routes = executor.invokeAll(tasks);
      int min = Integer.MAX_VALUE;
      ArrayList<Place> routePlaces = new ArrayList<Place>();

      for (int i = 0; i < NUM_THREADS; i++) {
        Future<Route> boxedRoutes = routes.get(i);
        int localMin = boxedRoutes.get().dist;

        if (localMin < min) {
          min = localMin;
          routePlaces = boxedRoutes.get().places;
        }
      }
      return routePlaces;
    } catch (InterruptedException | ExecutionException e) {
      System.err.println("Failed to compute optimal route");
      e.printStackTrace();
      return null;
    } finally {
      shutdown();
    }
  }

  /**
   * Stop the ExecutorService.
   */
  private void shutdown() {
    executor.shutdown();
  }
}