import java.util.concurrent.*;

/* This is making use of the synchronize expression on an object instead of the synchronized keyword to be
 on the method, this helps to increase concurrency and is much better*/
public class syncExpression {
        private static Account account = new Account();

        public static void main(String[] args) {
            ExecutorService executor = Executors.newCachedThreadPool();

            // Create and launch 100 threads
            for (int i = 0; i < 100; i++) {
                executor.execute(new AddAPennyTask());
            }
            executor.shutdown();

            // Wait until all tasks are finished
            while (!executor.isTerminated()) {
            }

            System.out.println("What is balance? " + account.getBalance());
        }

        // A thread for adding a penny to the account
        private static class AddAPennyTask implements Runnable {
            public void run() {
                synchronized (account) { //Synchronized the object account
                    account.deposit(1);
                }
            }
        }

        // An inner class for account
        private static class Account {
            private int balance = 0;

            public int getBalance() {
                return balance;
            }
            // Or  public synchronized void deposit(int amount) -> This would also synchronize the method.
            public void deposit(int amount) {
                int newBalance = balance + amount;


                // data-corruption problem and make it easy to see.
                try {
                    Thread.sleep(5);
                }
                catch (InterruptedException ex) {
                }

                balance = newBalance;
            }
        }
    }

