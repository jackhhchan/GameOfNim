import java.util.Random;
/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/

public class NimAIPlayer extends NimPlayer implements Testable
{
    // you may further extend a class or implement an interface
    // to accomplish the tasks.

    public NimAIPlayer()
    {
        super();
    }
    public NimAIPlayer(String username, String familyName, String givenName)
    {
        super(username, familyName, givenName);
    }


    public String advancedMove(boolean[] available, String lastMove)
    {
        // the implementation of the victory
        // guaranteed strategy designed by you
        String move = "";

        return move;
    }
    /*
    Conditions of game for winning:
    1. initial number of stones is k(M + 1) + 1, where k is a positive integer, and rival player moves first.
    2. initial number of stones is NOT k(M + 1) + 1, where k is a positive integer, and rival player moves second.
     */
    public int removeStones(int removeStones, int currentStones, int upperbound, boolean win)
    {
        if (win)
        {
            System.out.println();
            System.out.println("AI will win!");

            // if
            // determine the value k depending on the current number of stones.
            double k = Math.ceil((currentStones - 1) / (upperbound + 1));
            int k_int = (int) k;

            removeStones = -(k_int * (upperbound - 1) + 1 - currentStones);

            return removeStones;
        }
        else
        {
            System.out.println("AI is playing random numbers.");
            removeStones = randomNumber(upperbound);
            return removeStones;
        }
    }

    private int randomNumber(int upperbound)
    {
        Random random = new Random();
        int randomNumber = random.nextInt(upperbound) + 1;
        return randomNumber;
    }
}