import java.util.concurrent.*;

//AN Executor is used for execution of tasks in a Thread Pool
//Thread Pools are ways of running lots of tasks concurrently and efficiently

public class ExecutorDemo extends PrintLetters {

    public ExecutorDemo(char a, int nums) {
        super(a, nums);
    }

    public static void main(String[] args) {

        //Creates a Fixed Thread pool maximum of "number entered as argument"
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //Pass tasks to executor as anonymous objects to '3' threads we created
        executor.execute(new PrintLetters('a',10));
        executor.execute(new PrintLetters('b', 5));
        executor.execute(new PrintLetters('d', 5));

        //In a case the executor was set to a fixedthreadpool of '1' and we created 3 tasks above
        //The tasks would be ran sequentially not concurrently due to only one thread been available

        //Shutdown executor
        //Shutdown: Executor is shut down after all the tasks have been ran
        //ShutdownNow: Executor is shutdown forcefully, does not wait for all tasks to finish, returns a list of unifinished tasks

        executor.shutdown();
    }
}
