import javax.sound.midi.Soundbank;
import java.util.Scanner;
import java.io.*;

public class Nimsys {


    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        NimGame game = new NimGame();

        // initialize the possible number of arguments that may follow an input function.
        int FOURARGS = 5;
        int THREEARGS = 4;
        int ONEARG = 2;
        int NOARGS = 1;


        //Load in previous games records if exists.
        loadFile(game);


        System.out.println("Welcome to Nim\n");
        String inputFunction;

        // List of acceptable inputFunction commands.
        String[] inputFunctionList = new String[9];
        inputFunctionList[0] = "addplayer";
        inputFunctionList[1] = "addaiplayer";
        inputFunctionList[2] = "editplayer";
        inputFunctionList[3] = "removeplayer";
        inputFunctionList[4] = "displayplayer";
        inputFunctionList[5] = "resetstats";
        inputFunctionList[6] = "rankings";
        inputFunctionList[7] = "startgame";
        inputFunctionList[8] = "exit";


        do {
            // Prompts for input and analyse input.
            System.out.print("$");
            String[] input = keyboard.nextLine().split("\\s|,");
            inputFunction = input[0];

            checkInputFunction(inputFunctionList, inputFunction);

            /*
             * Runs the program based on the entered input function.
             */

            switch(inputFunction)
            {
                case "addplayer":
                        addPlayer(input, game, THREEARGS);
                    break;

                case "addaiplayer":
                        addAIPlayer(input, game, THREEARGS);
                    break;

                case "editplayer":
                        editPlayer(input, game, THREEARGS);
                    break;

                case "removeplayer":
                        removePlayer(input, game, NOARGS, keyboard);
                    break;

                case "displayplayer":
                        displayPlayer(input, game, NOARGS);
                    break;

                case "resetstats":
                        resetStats(input, game, NOARGS, keyboard);
                    break;

                case "rankings":
                        rankings(input, game, ONEARG);
                    break;

                case "startgame":
                        startGame(input, game, FOURARGS, keyboard);

            }
            System.out.println();

        } while (!inputFunction.equals("exit"));


        /*
        Create output stream and update players in players.dat after exit command is invoked.
        Flush and close after succession.
         */
        outputFile(game);

        System.exit(0);
    }



    /*
    List of helper functions:
     */
    private static void checkInputFunction(String[] inputFunctionList, String inputFunction)
    {
        // Handling exception for invalid command.
        try
        {
            for (int i = 0; i <= inputFunctionList.length; i++)
            {
                if (inputFunction.equals(inputFunctionList[i]))
                {
                    break;
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.printf("'%s' is not a valid command.%n", inputFunction);
            System.out.println();
        }

    }

    private static void addPlayer(String[] input, NimGame game, int validArgs)
    {
        try
        {
            if (input.length < validArgs)
            {
                throw new Exception("Incorrect number of arguments supplied to command.");
            }
            game.addPlayer(input[1], input[2], input[3]);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println();
        }

    }
    private static void addAIPlayer(String[] input, NimGame game, int validArgs)
    {
        try
        {
            if (input.length < validArgs)
            {
                throw new Exception("Incorrect number of arguments supplied to command.");
            }
            game.addAIPlayer(input[1], input[2], input[3]);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println();
        }

    }
    private static void editPlayer(String[] input, NimGame game, int validArgs)
    {
        try
        {
            if (input.length < validArgs)
            {
                throw new Exception("Incorrect number of arguments supplied to command.");
            }
            game.editPlayer(input[1], input[2], input[3]);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println();
        }

    }
    private static void removePlayer(String[] input, NimGame game, int noArgs, Scanner keyboard)
    {
        if (input.length == noArgs)
        {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            boolean removeAllCheck = keyboard.next().charAt(0) == 'y';
            keyboard.nextLine();
            if (removeAllCheck)
            {
                game.removePlayer(null);
            }
        }
        else {
            game.removePlayer(input[1]);
        }
    }
    private static void displayPlayer(String[] input, NimGame game, int noArgs)
    {
        if (input.length == noArgs)
        {
            game.displayPlayer(null);
        }
        else
        {
            game.displayPlayer(input[1]);
        }
    }
    private static void resetStats(String[] input, NimGame game, int noArgs, Scanner keyboard)
    {
        if (input.length == noArgs)
        {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            boolean removeAllCheck = keyboard.next().charAt(0) == 'y';
            if (removeAllCheck) {
                game.removePlayer(null);
            }
            game.resetStats(null);
        }
        else
        {
            game.resetStats(input[1]);
        }
    }
    private static void rankings(String[] input, NimGame game, int oneArg)
    {
        if (input.length == oneArg && input[1].equals("asc"))
        {
            game.rankings("asc");
        }
        else
        {
            game.rankings("desc");
        }
    }

    /*
    Game system logic is within startGame helper method.
     */
    private static void startGame(String[] input, NimGame game, int validArgs, Scanner keyboard)
    {
        try
        {
            if (input.length < validArgs)
            {
                throw new Exception("Incorrect number of arguments supplied to command.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println();
        }

        //Initialize currentPlayers
        NimPlayer currentPlayers[];

        // Pre-game checks
        // if both players exists, return current players in currentPlayers array.
        // If not, return array as null.
        currentPlayers = game.startGame(Integer.parseInt(input[1]),
                Integer.parseInt(input[2]),
                input[3], input[4]);
        if (currentPlayers == null) {
            System.out.println("One of the players does not exist.");
        }
        else
        {
            // start the game since both players exists.
            int turnKeeper = 0;                             //initialize turn keeper to keep track of player's turn.
            int upperbound = game.getUpperbound();
            int currentStones = game.getCurrentStones();
            // check if AI player or players exists and if the must-win condition satifies.
            int winner = checkAIWinConditions(currentStones, upperbound, currentPlayers);


            while (game.getCurrentStones() > 0) {
                // Get current stone from game. (updated after each round.)
                currentStones = game.getCurrentStones();
                //Print out the number of stones left in asterisks *.
                System.out.printf("%n%d stones left: ", currentStones);
                for (int i = 0; i < currentStones; i++) {
                    System.out.print("*");
                }
                System.out.printf("%n");

                int numRemove = 0;
                boolean win;
                //Prompt player for stone removal in alternate turns.
                if (turnKeeper == 0)
                {
                    System.out.println(currentPlayers[0] + "'s turn - remove how many?:");
                    try
                    {
                        if (currentPlayers[0] instanceof NimHumanPlayer)
                        {
                            numRemove = keyboard.nextInt();
                            keyboard.nextLine();
                            if (numRemove > upperbound || numRemove < 1 || numRemove > currentStones)
                            {
                                throw new Exception();
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.printf("%nInvalid move. You must remove between 1 and %d stones.%n%n", upperbound);
                        continue;
                    }
                    // returns true if this player is the AI winner.
                    win = ifAIWin(winner, turnKeeper);

                    currentStones -= currentPlayers[0].removeStones(numRemove, currentStones, upperbound, win);
                    game.setCurrentStones(currentStones);
                    turnKeeper = 1;
                }
                else if (turnKeeper == 1)
                {
                    System.out.println(currentPlayers[1] + "'s turn - remove how many?:");
                    try
                    {
                        if (currentPlayers[1] instanceof NimHumanPlayer)
                        {
                            numRemove = keyboard.nextInt();
                            keyboard.nextLine();
                            if (numRemove > upperbound || numRemove < 1 || numRemove > currentStones)
                            {
                                throw new Exception();
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.printf("%nInvalid move. You must remove between 1 and %d stones.%n%n", upperbound);
                        continue;
                    }

                    win = ifAIWin(winner, turnKeeper);

                    currentStones -= currentPlayers[1].removeStones(numRemove, currentStones, upperbound, win);
                    game.setCurrentStones(currentStones);
                    turnKeeper = 0;
                }
            }
            System.out.println("Game Over");

            // Updating games played stats for both players in the game.
            game.updateGames(currentPlayers[0], currentPlayers[1]);

            //Prints out which player has won based on the turnKeeper's value.
            switch (turnKeeper) {
                case 0:
                    turnWinner(game, currentPlayers, 0);
                    break;
                case 1:
                    turnWinner(game, currentPlayers, 1);
                    break;
            }

            //update win ratios for the participated players.
            game.updateWinRatios(currentPlayers[0], currentPlayers[1]);
        }
    }

    private static boolean ifAIWin(int winner, int turnKeeper)
    {
        boolean win;
        // check if the current player is the AI winner.
        if (winner == 0 && turnKeeper == 0)
        {
            win = true;
        }
        else if (winner == 1 && turnKeeper == 1)
        {
            win = true;
        }
        else
        {
            win = false;
        }
        return win;
    }

    // The message and updates for the winner.
    private static void turnWinner(NimGame game, NimPlayer[] currentPlayers, int playerNumber)
    {
        System.out.printf("%s %s wins!%n",
                currentPlayers[playerNumber].getFamilyName(),
                currentPlayers[playerNumber].getGivenName());
        game.updateWin(currentPlayers[playerNumber]);
    }
    private static int checkAIWinConditions(int currentStones, int upperbound, NimPlayer[] currentPlayers)
    {
        int winner = -1;
        // check if current players contain an AI player.
        if ((!(currentPlayers[0] instanceof NimAIPlayer)) && !(currentPlayers[1] instanceof NimAIPlayer))
        {
            return winner;
        }
        else
        {
            int modNum = (currentStones - 1)%(upperbound + 1);

            // if modNum is 0 then, initial stones is an element of k(M + 1) + 1 and vice versa.
            if (modNum == 0 && currentPlayers[1] instanceof NimAIPlayer)
            {
                // second player needs to be the winner.
                return winner = 1;
            }
            else if (modNum != 0 && currentPlayers[0] instanceof NimAIPlayer)
            {
                // first player needs to be the winner.
                return winner = 0;
            }
            else
            {
                // no winning guarantee strategy is implemented.
                return winner;
            }

        }

    }

    /*
    Helper functions for loading and storing current player information from or into players.dat.
     */
    private static void loadFile(NimGame game)
    {
        ObjectInputStream inStream;
        //Load in previous games records if exists.
        try
        {
            inStream = new ObjectInputStream(new FileInputStream("players.dat"));
            NimPlayer[] players = (NimPlayer[]) inStream.readObject();
            game.setPlayers(players);
        }
        catch(Exception e){}
    }
    private static void outputFile(NimGame game)
    {
        ObjectOutputStream outStream = null;
        try
        {
            outStream = new ObjectOutputStream(new FileOutputStream("players.dat"));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


        NimPlayer[] players = game.getPlayers();

        try
        {
            outStream.writeObject(players);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            outStream.flush();
            outStream.close();
        }
        catch(Exception e)
        {
            System.out.println("Unable to flush or close outputstream.");
            System.out.println(e.getMessage());
        }

    }



}
