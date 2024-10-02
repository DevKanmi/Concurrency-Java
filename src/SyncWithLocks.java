import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/*
Notes:
 Race Condition occurs when threads are not synchronized, leading to data corruption,
 non sychronized means more thn one thread certain part of the program concurrrently called the Critical region

 Thread Safe: A class is said to be thread safe if an object of the class does not cause a race condition
 in the presence of multiple thread.

 Synchronized: WHen a method is synchronized only one thread is able to access the thread at a time.
 it is a 'keyword' used to make a thread safe

 Synchronized method acquires a lock before it executes
 basically once a thread enters a method, it is locked no other thread can access the method until
 whatever task is completed, then the lock is released and the next thread enters.

 "LOCK" : is a mechanism for exclusive use of a resource.

 is there a way to control which threads access a method ? YES and NO
 - the use of reentrant lock makes use of a fairness policy which ensures the longest waiting thread gets access to the
 lock first, might lead poorer overall performance than default setting.
 ReentrantLock(true)
 -False fairness policy grants lock to a waiting thread arbitrarily.
 */

public class SyncWithLocks {
    private static  Account  account = new Account();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        //Create and Launch 100 threads
        for(int i =0; i<100; i++){
            executor.execute(new AddAPennyTask());
        }

        executor.shutdown();

        //Waiting till tasks are finished
        while ((!executor.isTerminated())){
        }

        System.out.println("What is the balance? " + account.getBalance());
    }

    //Thread that adds penny to the account
    public static class  AddAPennyTask implements Runnable {
        public void run() {
            account.deposit(1);
        }
    }


    //Inner Class for the Account
    public static class Account{
        private static Lock lock =  new ReentrantLock(); //Lock is Created.
        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void deposit(int amount){
            lock.lock(); // Acquires lock

            try{
                int newBalance = balance+ amount;

                //Setting  delay to magnify data corruption, makes it easier to see
                Thread.sleep(5);

                balance = newBalance;
            }
            catch(InterruptedException ex){
            }

            finally {
                lock.unlock(); //Releases lock to next thread.
                }
            }
        }
    }