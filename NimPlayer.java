import java.io.Serializable;

public class NimPlayer implements Serializable
{
    private String username;
    private String givenName, familyName;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private double winRatio;

    // Constructor method to create player.
    public NimPlayer(String username, String familyName, String givenName) {
        setUsername(username);
        setFamilyName(familyName);
        setGivenName(givenName);
    }

    // No-args constructor.
    public NimPlayer() {
        username = null;
        givenName = null;
        familyName = null;
        gamesWon = 0;
        gamesPlayed = 0;
    }

    /* Accessor methods */
    public String getUsername() {
        return username;
    }
    public String getGivenName() {
        return givenName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public int getGamesWon() {
        return gamesWon;
    }
    public double getWinRatio() {
        return this.winRatio;
    }

    /* Mutator methods */
    public void setUsername(String username) {
        this.username = username;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public void setGamesPlayed(int num) {
        this.gamesPlayed = num;
    }
    public void setGamesWon(int num) {
        this.gamesWon = num;
    }
    public void setWinRatio(double num){
        this.winRatio = num;
    }


    /*to be called to update win ratio of player every time.*/

    public void updateWinRatio() {
        double gamesWon = (double) getGamesWon();
        double gamesPlayed = (double) getGamesPlayed();
        setWinRatio((gamesWon / gamesPlayed) * 100);
        System.out.printf("%s's win ratio updated: %f%n", getUsername(), getWinRatio());
    }

    public int removeStones(int removestones) {
        return removestones;
    }

    public String toString() {
        return username;
    }
}