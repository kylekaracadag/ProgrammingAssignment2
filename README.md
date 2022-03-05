# ProgrammingAssignment2
## Instructions for running the program
 - Download all the files in this repository and put them in a folder
 - Open your command prompt and change the directory to the folder you created
 - To run the code for problem one type "javac Minotaur.java" in the command prompt
 - To run the code for problem two type "javac Vase.java" in the command prompt
 - The output will be displayed in the terminal

## Problem 1: Minotaur’s Birthday Party
The strategy that I came up with for this problem was having the guests select a random leader before the game has started and after the game starts only the leader would check if the plate is empty and would increment a counter that represents how many guests entered the room. If the leader increments a counter it means that at least one guest must have entered the maze before him. As for the guests they only eat the cake if they have not done so already. The ones that have already eaten a cake just enter the maze and leave. The guest are picked randomly to enter the maze and they keep entering until the leader has counted N number of empty plates when they enter.

To code my solution I created an array of N number of threads, each thread representing a guest. I generated a random number from 0 to N which represents the index of the leader. When the game starts the guests are picked randomly using randomly generated indexes from 0 to N. If the index of the guest that is picked is the same as the leader an atomic boolean variable is set to true indicating that the leader is in the maze. Otherwise the atomic variable is set to false. I also used an atomic integer for the couner that the leader uses. An atomic boolean is used to indicate whether or not the cake is on the plate. 

## Problem 2: Minotaur’s Crystal Vase
For this problem I used strategy 3, using a queue to have guests enter the room. First I created a randomly ordered list with unique numbers ranging from 0 to N representing the indices of the array of threads. Then these numbers are put into a queue. I iterate through the queue until it is empty and re-queue a guest with a 20% chance representing that they re-entered the line. When they enter the room they spend 100 nanoseconds inside through the sleep function and then they leave. 

I believe this strategy is the best out of the three because it is the simplest and the most risk free one. The first strategy I would say is the worst one because not every thread may be able to visit the room because we can have the thread that is inside the room run for as long as it wants. We can also have too many threads trying to check to see if the room is empty. The second strategy is promising but we would have to make sure we put a lock on the variable that shows whether or not the room is available or busy and a simpler solution to using a lock like that is to simply just use a queue which is pretty much what I did for strategy 3.

## Execution Times
|   N   | Problem 1|Problem 2|
|-------|----------|---------|
| 10    | 0.0013s  | 0.0012s |
| 100   | 0.0132s  | 0.0031s |
| 1000  | 0.7436s  | 0.0148s |
| 10000 | 87.8619s | 0.0842s |
