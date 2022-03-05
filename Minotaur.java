import java.util.*;
import java.lang.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class Minotaur {
    // True represents that there is a cake on the plate
    // False represents that there is no cake on the plate
    // We start off with a cake on the plate so initialize to true
    static int n = 10;
    static AtomicBoolean cake = new AtomicBoolean(true); 
    static AtomicBoolean isLeader = new AtomicBoolean(false); 
    static AtomicInteger counter =  new AtomicInteger(0);

    public static class Threading extends Thread {
        // Flag represents whether or not the current guest has already 
        // eaten the cake.
        boolean flag;

        public Threading() {
            flag = false;
        }
        
        public void run() {
            // If the leader is selected and there is no cake on the plate then
            // we know that one guest must have eaten a cake so we increment count
            // and get a new cake on the plate
            if (isLeader.get() == true) {
                counter.getAndIncrement();
                cake.compareAndSet(false, true);
                System.out.println("The leader counted at least " + counter +  " guests entered the labyrinth");
            }
            else {
                // Eat the cake
                if (flag == false) {
                    flag = true;
                    cake.compareAndSet(true, false);
                    System.out.println("Guest ate the cake");
                }
                else {
                    System.out.println("Guest already ate the cake");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Initialize the array of n number of threads
        Thread[] threads = new Thread[n];

        // Pick a random guest to be the leader who will count the empty plates
        Random rand = new Random();
        int leader = rand.nextInt(n);

        // Declare N number of threads
        for (int i = 0; i < n; i++) {
            if (i == leader) {
                threads[i] = new Thread(new Threading(), "leader");
            }
            else {
                threads[i] = new Thread(new Threading(), "guest");
            }
        }

        // Initialize N number of threads
        while (counter.intValue() < n) {
            int guest = rand.nextInt(n);
            if (guest == leader) {
                System.out.println("Leader is selected");
                isLeader.compareAndSet(false, true);
            }
            else {
                System.out.print("Guest #" + guest + " is selected: ");
                isLeader.compareAndSet(true, false);
            }

            try {
                threads[guest].run();
                threads[guest].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nEvery guest entered the Minotaur's labyrinth");
    }
}