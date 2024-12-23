//Our Tasks Class Created here
public class PrintLetters implements Runnable{  //Instead of Implement Runnable, you can extend Thread Class
    char letter;
    int no;

    public PrintLetters(char a, int nums){
        letter = a;
        no = nums;
    }

    @Override
    //What the class is to do
    public void run() {
        for(int i =0; i< no; i++){
            System.out.print(" "+ letter);
        }

    }
}



////Task Object Created here
//PrintLetters task1 = new PrintLetters('r',40);
//PrintLetters task2 = new PrintLetters('g',30);
//
////Thread that faciliates execution of a task
//Thread thread1 = new Thread(task1);
//Thread thread2 = new Thread(task2);
//        thread1.start();
//        thread2.start();
//    }