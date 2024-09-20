public class Main{
    public static void main(String[] args) {

        //Task Object Created here
        Printnum task1 = new Printnum('r',40);
        Printnum task2 = new Printnum('g',30);

        //Thread that faciliates execution of a task
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }
}

//Our Tasks Class Created here
class Printnum implements Runnable{
    char letter;
    int no;

    public Printnum(char a, int nums){
        letter = a;
        no = nums;
    }

    @Override
    //What the class is to do
    public void run() {
        for(int i =0; i< no; i++){
            System.out.println(letter);
        }

    }
}