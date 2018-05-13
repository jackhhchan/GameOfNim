import java.util.Scanner;

public class Nimsys {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        NimGame game = new NimGame();

        System.out.println("Welcome to Nim\n");
        String inputFunction;

        String[] inputFunctionList = new String[8];
        inputFunctionList[0] = "addplayer";
        inputFunctionList[1] = "editplayer";
        inputFunctionList[2] = "removeplayer";
        inputFunctionList[3] = "displayplayer";
        inputFunctionList[4] = "resetstats";
        inputFunctionList[5] = "rankings";
        inputFunctionList[6] = "startgame";
        inputFunctionList[7] = "exit";


        do {
            // Prompts for input and analyse input.
            System.out.print("$");
            String[] input = keyboard.nextLine().split("\\s|,");

            inputFunction = input[0];

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
                System.out.printf("'%s' is not a valid command.%n", input[0]);
                System.out.println();
            }

            /*
             * Runs the following code if the entered command is addplayer or editplayer.
             */

            switch(inputFunction)
            {
                case "addplayer":
                    try
                    {
                        if (input.length != 4)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }

                    game.addPlayer(input[1], input[2], input[3]);
                    break;

                case "editplayer":
                    try
                    {
                        if (input.length != 4)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }

                    game.editPlayer(input[1], input[2], input[3]);
                    break;

                case "removeplayer":
                    try
                    {
                        if (input.length > 2)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }

                    if (input.length == 1)
                    {
                        System.out.println("Are you sure you want to remove all players? (y/n)");
                        boolean removeAllCheck = keyboard.next().charAt(0) == 'y';
                        if (removeAllCheck) {
                            game.removePlayer(null);
                        }
                    }
                    else {
                        game.removePlayer(input[1]);
                    }
                    break;
                case "resetstats":
                    try
                    {
                        if (input.length > 2)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }

                    if (input.length == 1) {
                        System.out.println("Are you sure you want to remove all players? (y/n)");
                        boolean removeAllCheck = keyboard.next().charAt(0) == 'y';
                        if (removeAllCheck) {
                            game.removePlayer(null);
                        }
                        game.resetStats(null);
                    } else {
                        game.resetStats(input[1]);
                    }
                    break;

                case "rankings":
                    try
                    {
                        if (input.length > 2)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }
                    if (input.length == 2 && input[1].equals("asc"))
                    {
                        game.rankings("asc");
                    } else
                    {
                        game.rankings("desc");
                    }
                    break;

                case "startgame":
                    try
                    {
                        if (input.length != 5)
                        {
                            throw new Exception("Incorrect number of arguments supplied to command.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        System.out.println();
                        continue;
                    }

                    NimPlayer currentPlayers[];

                    // Pre-game checks
                    // if both players exists, return current players in currentPlayers array.
                    // If not, return array as null.
                    currentPlayers = game.startGame(Integer.parseInt(input[1]),
                            Integer.parseInt(input[2]),
                            input[3], input[4]);
                    if (currentPlayers == null) {
                        System.out.println("One of the players does not exist.");
                    } else {
                        // start the game since both players exists.
                        int turnKeeper = 0;
                        int numRemove;
                        int upperbound = game.getUpperbound();

                        while (game.getCurrentStones() > 0) {
                            // Get current stone from game. (updated after each round.)
                            int currentStones = game.getCurrentStones();
                            //Print out the number of stones left in asterisks *.
                            System.out.printf("%d stones left: ", currentStones);
                            for (int i = 0; i < currentStones; i++) {
                                System.out.print("*");
                            }
                            System.out.printf("%n");

                            //Prompt player for stone removal in alternate turns.
                            if (turnKeeper == 0) {
                                System.out.println(currentPlayers[0] + "'s turn - remove how many?:");

                                try
                                {
                                    numRemove = keyboard.nextInt();
                                    if (numRemove > upperbound || numRemove < 1 || numRemove > currentStones) {
                                        throw new Exception();
                                    }
                                }
                                catch (Exception e)
                                {
                                    System.out.printf("%nInvalid move. You must remove between 1 and %d stones.%n%n", upperbound);
                                    continue;
                                }

                                currentStones -= currentPlayers[0].removeStones(numRemove);
                                game.setCurrentStones(currentStones);
                                turnKeeper = 1;
                            } else if (turnKeeper == 1) {
                                System.out.println(currentPlayers[1] + "'s turn - remove how many?:");

                                try
                                {
                                    numRemove = keyboard.nextInt();
                                    if (numRemove > upperbound || numRemove < 1 || numRemove > currentStones) {
                                        throw new Exception();
                                    }
                                }
                                catch (Exception e)
                                {
                                    System.out.printf("%nInvalid move. You must remove between 1 and %d stones.%n%n", upperbound);
                                    continue;
                                }

                                currentStones -= currentPlayers[1].removeStones(numRemove);
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
                                System.out.printf("%s %s wins!%n",
                                        currentPlayers[0].getFamilyName(),
                                        currentPlayers[0].getGivenName());
                                game.updateWin(currentPlayers[0]);

                                break;
                            case 1:
                                System.out.printf("%s %s wins!%n",
                                        currentPlayers[1].getFamilyName(),
                                        currentPlayers[1].getGivenName());
                                game.updateWin(currentPlayers[1]);
                                break;
                        }

                        game.updateWinRatios(currentPlayers[0], currentPlayers[1]);
                    }

            }
            System.out.println();
        } while (!inputFunction.equals("exit"));

        System.exit(0);
    }
}
