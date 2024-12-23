/* This is a simple Bank java program that solves the issue of dynamic deadlock
   Where two threads try to acquire locks that have been locked down by the other
   we make use of hashcode to ensure there is global ordering in place

 */

public class BankingDemo {
    static class Account {

        private  int balance;

        public Account(int balance){
            this.balance = balance;
        }

        public int getBalance (){
            return balance;
        }

        public void deposit(int amount){
            balance += amount;
        }

        public void withdraw(int amount){
            balance -= amount;
        }
    }

    static class Bank {
        public void transfer(Account from, Account to, int amount){
            //This is used to prevent deadlock from happening
            Account firstLock = System.identityHashCode(from) < System.identityHashCode(to) ? from : to;
            Account secondLock = System.identityHashCode(from) < System.identityHashCode(to) ? to : from;
            synchronized (firstLock){
                try{
                    Thread.sleep((10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            synchronized (secondLock){
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from " + from + " to " + to);
                    System.out.println("new balance for " + from + " is: " + from.getBalance());
                    System.out.println("new balance for " + to + " is: " + to.getBalance());
            }
            }
        }
    }

    public static void main(String[] args) {
        Account account1 = new Account(2000);
        Account account2 = new Account(4000);
        Bank bank = new Bank();

        Thread thread1 = new Thread(() -> bank.transfer(account1, account2, 500));
        Thread thread2 = new Thread(() -> bank.transfer(account2, account1, 1000));

        thread1.start();
        thread2.start();

        
    }

}
