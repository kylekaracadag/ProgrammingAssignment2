import java.util.*;
import java.lang.*;
import java.util.concurrent.*;


class Vase {
    static int n = 10;

    public static class Worker extends Thread {

        public Worker() {
            
        }
        
        public void run() {
            System.out.println("Guest " + getName() + " entered the room");

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Guest " + getName() + " exited the room");
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[n];
        Queue<Integer> queue = new LinkedList<>();

        // Add the indices of the threads in random order into a queue
        ArrayList<Integer> indeces = new ArrayList<Integer>(); 
        for (int i = 0; i < n; i++) { 
            indeces.add(i); 
        } 
        Collections.shuffle(indeces); 
        for (int i = 0; i < n; i++) { 
            queue.add(indeces.get(i));
        } 

        // Declare N number of threads
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new Worker());
        }

        while (!queue.isEmpty()) {
            int guest = queue.remove();

            try {
                threads[guest].run();
                threads[guest].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 20% chance that a guest decides to re-enter the queue
            if (Math.random() < 0.2) {
                queue.add(guest);
                System.out.println("Guest re-entered the queue");
            }
        }

        System.out.println("\nNo guests left in the queue");
    }
}
