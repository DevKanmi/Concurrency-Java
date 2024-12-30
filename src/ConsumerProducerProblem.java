/*
Consumer Producer problem is a common concurrency problem that involves multiple threads that are communicating or
exchanging data, consumer/producer there are two tasks, producers which produce data and put into a shared buffer, the
consumers which consumes data from the shared buffer, the problem is
to design a synchronization mechanism that allows Producers and consumers to communicate and
exchange data in a safe and predictable manner without losing or corrupting data
 */

import java.nio.Buffer;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.LinkedList.*;

public class ConsumerProducerProblem {
    private static Buffer buffer = new Buffer(); // Create a buffer

    //Main Method
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2); //Create 2 threads
        executor.execute(new ProducerTask());
        executor.execute(new ConsumerTask());
        executor.shutdown();
    }

    //Producer task : Task is to add an int to the buffer

    public static class ProducerTask implements Runnable{
        @Override
        public void run() {
            try{
                int i = 1;
                while(true){
                    System.out.println("Producer writes " + i);
                    buffer.write(i++); // Adds a value to the buffer

                    //Put thread to sleeep
                    Thread.sleep((int)(Math.random() * 10000));
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    //Consumer task: A task for reading and deleting an int from the buffer
    private static class ConsumerTask implements Runnable{
        @Override
        public void run() {
            try{
                while(true){
                    System.out.println("\t\t\tConsumer reads " + buffer.read());

                    //put thread into sleep
                    Thread.sleep((int)(Math.random() * 10000));
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    //An inner class for the buffer
    private static  class Buffer {
        private static final int CAPACITY = 1; //Size of buffer
        private LinkedList<Integer> queue = new LinkedList<>();

        //Create a new Lock
        private static Lock lock = new ReentrantLock();

        //Create conditions
        private static Condition notEmpty = lock.newCondition(); //Create Conditions
        private static Condition notFull = lock.newCondition();

        public void write(int value){
            lock.lock();

            try{
                while(queue.size() == CAPACITY){
                    System.out.println("wait for notfull condition");
                    notFull.await(); //Signal notFull
                }

                queue.offer(value);
                notEmpty.signal();  //Signal notEmpty
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            finally {

                lock.unlock();
            }
        }

        public int read(){
            int value = 0;
            lock.lock(); //Acquire a lock

            try{
                while(queue.isEmpty()){
                    System.out.println(("\t\t\tWait for notEmpty Condition"));
                    notEmpty.await(); //Wait fot notEmpty
                }

                value = queue.remove();
                notFull.signal();  // Notify notFull condition
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            finally {
                lock.unlock();
                return value;
            }

        }
    }
}
