/*
Thread states are said to be the current state of a thread
new --> this State is initialized once a thread is created
ready --> this state is assigned once the start() method is invoked
running --> assigned when a cpu has assigned time to it and it begins execution, goes back to ready
if cpu time expires or yield method is called
blocked --> enters block state is the join(), sleep() or wait method is invoked
finished --> enters state once an execution of run() method is completed.
 */



/*
Parallel programming involves the approach of divide and conquer, where
a particular problem is broken down into sub problems "forks" and then they run parallel and then when done
they are combined back to a solution "join" hence the name fork/join

forkjointask and forkjoinpool can be used.


 */


import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.*;
import java.util.concurrent.RecursiveTask;

public class parallelProgramming {
    public static void main(String[] args) {
         // Create a list
         final int N = 9000000;
         int[] list = new int[N];
         for (int i = 0; i < list.length; i++)
             list[i] = i;

        long startTime = System.currentTimeMillis();
        System.out.println("\nThe maximal number is " + max(list));
         long endTime = System.currentTimeMillis();
         System.out.println("The number of processors is " +
                Runtime.getRuntime().availableProcessors());
         System.out.println("Time is " + (endTime - startTime)
                 + " milliseconds");
         }

         public static int max(int[] list) {
         RecursiveTask<Integer> task = new MaxTask(list, 0, list.length);
         ForkJoinPool pool = new ForkJoinPool();
         return pool.invoke(task);
         }

         private static class MaxTask extends RecursiveTask<Integer> {
            private final static int THRESHOLD = 1000;
            private int[] list;
            private int low;
            private int high;

        public MaxTask(int[] list, int low, int high) {
             this.list = list;
             this.low = low;
             this.high = high;
             }

        @Override
        public Integer compute() {
             if (high - low < THRESHOLD) {
                 int max = list[0];
                 for (int i = low; i < high; i++)
                    if (list[i] > max)
                        max = list[i];
                 return new Integer(max);
            }
            else {
                 int mid = (low + high) / 2;
                 RecursiveTask<Integer> left = new MaxTask(list, low, mid);
                 RecursiveTask<Integer> right = new MaxTask(list, mid, high);

                 right.fork();
                 left.fork();
                 return new Integer(Math.max(left.join().intValue(),
                        right.join().intValue()));

 }
 }
    }
}

