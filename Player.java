import java.util.ArrayList;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        Tile removedTile = playerTiles[index];
        numberOfTiles = numberOfTiles - 1;
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }
        return removedTile;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {
        if (numberOfTiles < 15)  {
            playerTiles[numberOfTiles] = t;
            numberOfTiles++;
            for (int i = numberOfTiles - 2; i >= 0; i--) {
                if (t != null && playerTiles[i] != null && t.compareTo(playerTiles[i]) == -1) {
                    playerTiles[i+ 1] = playerTiles[i];
                    playerTiles[i] = t;
                }
            }
        }
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        ArrayList<Integer> usedValue = new ArrayList<>();
        int chainsNumber = 0;
        for (int i = 0; i < numberOfTiles; i++) 
        {
            int lenght = 1;
            for (int k = i + 1; k <numberOfTiles; k++) 
            {
                if (playerTiles[i] != null && playerTiles[k] != null && playerTiles[i].getValue() == playerTiles[k].getValue() && playerTiles[k].colorNameToInt() == lenght) 
                {
                    lenght++;
                }
            }
            if (lenght == 4 && !usedValue.contains(playerTiles[i].getValue())) 
            {
                chainsNumber++;
                usedValue.add(playerTiles[i].getValue());
            }
        }
        if (chainsNumber == 3) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i] != null&&  playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i] != null)
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
