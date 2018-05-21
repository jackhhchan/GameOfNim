public class NimGame {
    /**
     * Write the program for the game here.
     */

    private int currentStones;
    private int upperbound;
    private int maxPlayers = 100;

    // Declaring arrays for player stats and player objects
    private NimPlayer[] players = new NimPlayer[maxPlayers]; // stores references to player objects.
    private double[] stats = new double[maxPlayers];         // stores winning ratio.
    // To be populated every time rankings is invoked.


    public void addPlayer(String username, String familyName, String givenName) {
        // Search players array to make sure player doesn't already exist.
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && players[i].getUsername().equals(username)) {
                System.out.println("The player already exist.");
                return;
            } else if (players[i] == null) {
                players[i] = new NimPlayer(username, familyName, givenName);
                return;
            }
        }

    }

    public void removePlayer(String username) {
        if (username == null) {
            for (int player = 0; player < players.length; player++) {
                players[player] = null;
            }
            return;
        }
        for (int player = 0; player < players.length; player++) {
            if (players[player] != null && players[player].getUsername().equals(username)) {
                players[player] = new NimPlayer();
                return;
            }
        }
        System.out.println("The player does not exist.");
    }

    public void editPlayer(String username, String familyName, String givenName) {
        for (NimPlayer player : players) {
            if (player == null) {
                continue;
            }

            if (player.getUsername().equals(username)) {
                player.setFamilyName(familyName);
                player.setGivenName(givenName);
                return;
            }
        }
        System.out.println("The player does not exist.");
    }


    public void displayPlayer(String username) {
        selectionSort(players);
        if (username == null) {
            for (NimPlayer player : players) {
                if (player != null) {
                    System.out.printf("%s,%s,%s,%d games,%d wins.%n",
                            player.getUsername(),
                            player.getFamilyName(),
                            player.getGivenName(),
                            player.getGamesPlayed(),
                            player.getGamesWon());
                }
            }
            return;
        }

        for (int player = 0; player < players.length; player++) {
            if (players[player] == null) {
                continue;
            }
            if (players[player].getUsername().equals(username)) {
                System.out.printf("%s,%s,%s,%d games,%d wins.%n",
                        players[player].getUsername(),
                        players[player].getFamilyName(),
                        players[player].getGivenName(),
                        players[player].getGamesPlayed(),
                        players[player].getGamesWon());
                return;

            }
        }
        System.out.println("The player does not exist.");
    }

    public void resetStats(String username) {
        if (username != null) {
            for (NimPlayer player : players) {
                if (player != null && player.getUsername().equals(username)) {
                    player.setGamesPlayed(0);
                    player.setGamesWon(0);
                    return;
                }
            }
            System.out.println("The player does not exist.");
        } else {
            for (NimPlayer player : players) {
                player.setGamesWon(0);
                player.setGamesPlayed(0);
            }
        }
    }

    public void rankings(String order) {
        // Sort arrays alphabetically using selection sort.
        selectionSort(players);

        //populate stats array with win ratios.
        for (int i = 0; i < stats.length; i++) {
            if (players[i] != null) {
                stats[i] = players[i].getWinRatio();
                System.out.println("stats:");
                System.out.printf("%s: %f%n", players[i].getUsername(), stats[i]);
            }
        }

        // Stable algorithm to sort stats after alphabetically sorting players.
        if (order.equals("asc")) {
            insertionSort(stats, players, "asc");
        } else if (order.equals("desc")) {
            insertionSort(stats, players, "desc");
        }
        // Print the rankings.
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                System.out.printf("%5.0f%% | %10d games | %s %s%n",
                        stats[i],
                        players[i].getGamesPlayed(),
                        players[i].getGivenName(),
                        players[i].getFamilyName());
            }
        }
    }

    public NimPlayer[] startGame(int initial_stones, int upperbound, String username1, String username2) {
        NimPlayer currentPlayers[] = new NimPlayer[2];
        setCurrentStones(initial_stones);
        setUpperbound(upperbound);


        // Search for username1 and username 2
        boolean player1exists = false, player2exists = false;
        for (NimPlayer player : players) {
            if (player != null &&
                    player.getUsername().equals(username1)) {
                currentPlayers[0] = player;
                player1exists = true;
            }
            if (player != null &&
                    player.getUsername().equals(username2)) {
                currentPlayers[1] = player;
                player2exists = true;
            }
        }
        // Check if player 1 and player 2 are found.
        if (player1exists && player2exists) {
            System.out.printf("%nInitial stone count: %d%n", getCurrentStones());
            System.out.printf("Maximum stone removal: %d%n", getUpperbound());
            System.out.printf("Player 1: %s %s%n",
                    currentPlayers[0].getFamilyName(),
                    currentPlayers[0].getGivenName());
            System.out.printf("Player 2: %s %s%n%n",
                    currentPlayers[1].getFamilyName(),
                    currentPlayers[1].getGivenName());
            return currentPlayers;
        } else {
            return null;
        }
    }


    /**
     * Runs the Selection Sort algorithm to sort the player's username alphabetically.
     * The minimum alphabet username is placed in the first position after every scan of the array.
     */

    public static void selectionSort(NimPlayer[] players) {
        int numPlayers = players.length;
        int min_index;
        for (int i = 0; i < numPlayers; i++) {

            if (players[i] != null) {
                // Find index of smallest alphabet in the array for the current scan.
                min_index = indexSmallest(i, players);
                // Swaps position of the min_index with players[i]
                swap(min_index, i, players);
            }

        }
    }

    /**
     * Returns the index of the array with the smallest alphabet.
     */

    private static int indexSmallest(int startIndex, NimPlayer[] players) {
        int numPlayers = players.length;
        // set min index and min value as the startIndex and players[startIndex]
        int min_index = startIndex;
        String min_alphabet = players[startIndex].getUsername();

        for (int i = startIndex; i < numPlayers; i++) {
            // compare current username's first alphabet with current minimum username's first alphabet.
            if (players[i] != null &&
                    players[i].getUsername().compareTo(min_alphabet) < 0) { //if true, player[i]'s username is less than min_alphabet
                min_alphabet = players[i].getUsername();
                min_index = i;
            }
        }
        return min_index;
    }

    /**
     * Swaps the position of player[a] with player[b].
     */
    private static void swap(int a, int b, NimPlayer[] players) {
        NimPlayer temp = players[a]; /*double stats_temp = stats[a];*/
        players[a] = players[b];/* stats[a] = stats[b];*/
        players[b] = temp; /*stats[b] = stats_temp;*/
    }

    /**
     * InsertionSort is a stable algorithm and can be used to sort winning ratio
     * after the usernames are alphabetically sorted.
     */
    public static void insertionSort(double[] A, NimPlayer[] players, String order) {
        /*
        Orders in ascending order. (Min value in front)
         */
        if (order.equals("asc")) {
            // Start with the 2nd position of the array and compare with its first.
            for (int i = 1; i < A.length; i++) {
                // Store value of current array position to be used for comparison in the iteration.
                double v = A[i];
                NimPlayer player_v = players[i];
                // position j will always be 1 position behind current position i.
                int j = i - 1;

                // Keep swapping values from of array[j]
                // is the current array[j] > v?
                while (j >= 0 && A[j] > v) {
                    // Swap winning ratio array and players array's positions.
                    A[j + 1] = A[j];
                    players[j + 1] = players[j];
                    j = j - 1;
                }
                // Update minimum found position with min value.
                A[j + 1] = v;
                players[j + 1] = player_v;
            }
        }

        /*
        Orders in descending order. (Max value in front)
         */
        else {
            // Start with the 2nd position of the array and compare with its first.
            for (int i = 1; i < A.length; i++) {
                // Store value of current array position to be used for comparison in the iteration.
                double v = A[i];
                NimPlayer player_v = players[i];
                // position j will always be 1 position behind current position i.
                int j = i - 1;

                // Keep swapping values from of array[j]
                // is the current array[j] > v?
                while (j >= 0 && A[j] < v) {
                    // Swap winning ratio array and players array's positions.
                    A[j + 1] = A[j];
                    players[j + 1] = players[j];
                    j = j - 1;
                }
                // Update minimum found position with min value.
                A[j + 1] = v;
                players[j + 1] = player_v;
            }
        }
    }

    public void updateGames(NimPlayer player1, NimPlayer player2) {
        player1.setGamesPlayed(player1.getGamesPlayed() + 1);
        player2.setGamesPlayed(player2.getGamesPlayed() + 1);
        System.out.println("Games played:");
        System.out.printf("%s: %d, %s: %d %n",
                player1.getUsername(), player1.getGamesPlayed(),
                player2.getUsername(), player2.getGamesPlayed());
    }

    public void updateWin(NimPlayer winner) {
        winner.setGamesWon(winner.getGamesWon() + 1);
        winner.updateWinRatio();
        System.out.println("Games won:");
        System.out.printf("%s: %d %n",
                winner.getUsername(), winner.getGamesWon());
    }

    public void updateWinRatios(NimPlayer player1, NimPlayer player2) {
        player1.updateWinRatio();
        player2.updateWinRatio();
    }


    public int getUpperbound() {
        return this.upperbound;
    }

    private void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    public int getCurrentStones() {
        return this.currentStones;
    }

    public void setCurrentStones(int newStones) {
        this.currentStones = newStones;
    }

    public NimPlayer[] getPlayers() {
        return this.players;
    }

    public void setPlayers(NimPlayer[] players) {
        for (int i = 0; i < players.length; i++)
        {
            if (players[i] != null)
            {
                this.players[i] = players[i];
            }
        }
    }
}
