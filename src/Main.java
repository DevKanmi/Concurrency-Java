public class Main extends PrintLetters {

    public Main(char a, int nums) {
        super(a, nums);
    }

    public static void main(String[] args) {

        //Task Object Created here
        Runnable task1 = new PrintLetters('b',40);
        Runnable task2 = new PrintLetters('g',30);

        //Thread that faciliates execution of a task
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }
}

