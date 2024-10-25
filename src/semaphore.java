/*
A semaphore is an object used to control the number of threads that can access a shared resource.

The thread basically acquires permit from the semaphore then accesses the shared resource and returns the permit
back to the semaphore.

the flow is
 Thread --> Semaphore Permit ---> Resource

 Every semaphore has a number of permit assigned to it and an optional fairness policy.

 We have two methods
 .acquire()  -> If a thread acquires a permit the no of permit decreases by 1
 .release() -> Once a thread is done accessing the resource and releases the thread the no of permit increases by 1

This flow allows ONLY A CERTAIN NUMBER OF THREADS `specified in the no of permits field` TO ACCESS A SHARED RESOURCE
at a given time.

 */


import java.util.concurrent.Semaphore;

public class semaphore {
    private  static Semaphore semaphore1 = new Semaphore(1); // A permit of `1`
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public  void deposit(int Amount){
        try{
            semaphore1.acquire(); // A permit is acquired here
            int newBalance = balance + amount;

            Thread.sleep(5);

            balance = newBalance;
        }
        catch (InterruptedException ex){
        }
        finally {
            semaphore1.release(); //Permit is released here
        }
    }

}
