import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
The readers/writers problem is a classic synchronization challenge in multithreading
where multiple threads(readers and writers) access a shared resource goal is:
1. Allow concurrent access for readers (since reading doesn’t modify data).
2. Ensure exclusive access for writers (to prevent data corruption).
3. Avoid starvation (ensure fairness for both readers and writers).

Loc: protect critical region
Semaphores: to manage exclusive access
A read counter to track active readers

Rules:
--> If a writer is writing, no readers or other writers can access resource
--> if readers are active, new readers can join but writers must wait until all readers finish

 */


public class ReadersWritersProblem {
    private static int sharedData = 0;
    private static int readCount = 0;
    private static final Lock readLock = new ReentrantLock();  // Protects readCount
    private static final Lock writeLock = new ReentrantLock(); // Protects sharedData

    static class Reader implements Runnable {
        private final int id;

        public Reader(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            readLock.lock();
            try {
                readCount++;
                if (readCount == 1) {
                    writeLock.lock(); // First reader blocks writers
                }
            } finally {
                readLock.unlock();
            }

            // Read operation (non-critical section)
            System.out.println("Reader " + id + " reads: " + sharedData);

            readLock.lock();
            try {
                readCount--;
                if (readCount == 0) {
                    writeLock.unlock(); // Last reader unblocks writers
                }
            } finally {
                readLock.unlock();
            }
        }
    }

    static class Writer implements Runnable {
        private final int id;

        public Writer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            writeLock.lock(); // Exclusive access for writers
            try {
                sharedData++;
                System.out.println("Writer " + id + " writes: " + sharedData);
            } finally {
                writeLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        int numReaders = 3;
        int numWriters = 2;

        // Create reader threads
        for (int i = 0; i < numReaders; i++) {
            new Thread(new Reader(i)).start();
        }

        // Create writer threads
        for (int i = 0; i < numWriters; i++) {
            new Thread(new Writer(i)).start();
        }
    }

}
