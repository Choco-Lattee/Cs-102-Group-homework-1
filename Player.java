import java.util.ArrayList;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15];
        numberOfTiles = 0;
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        if (index < 0 || index >= numberOfTiles) {
            return null;
        }
        Tile removedTile = playerTiles[index];
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }
        playerTiles[numberOfTiles - 1] = null;
        numberOfTiles--;
        return removedTile;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     */
    public void addTile(Tile t) {
        if (numberOfTiles >= 15 || t == null) {
            return;
        }
        int i;
        for (i = numberOfTiles - 1; i >= 0 && playerTiles[i].compareTo(t) > 0; i--) {
            playerTiles[i + 1] = playerTiles[i];
        }
        playerTiles[i + 1] = t;
        numberOfTiles++;
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     */
    public boolean isWinningHand() {
        ArrayList<Integer> usedValues = new ArrayList<>();
        int chainsNumber = 0;

        for (int i = 0; i < numberOfTiles; i++) {
            if (usedValues.contains(playerTiles[i].getValue())) {
                continue;
            }
            int length = 1;
            for (int k = i + 1; k < numberOfTiles; k++) {
                if (playerTiles[i].getValue() == playerTiles[k].getValue() &&
                    playerTiles[k].colorNameToInt() != playerTiles[i].colorNameToInt()) {
                    length++;
                }
            }
            if (length == 4) {
                chainsNumber++;
                usedValues.add(playerTiles[i].getValue());
            }
        }
        return chainsNumber >= 3;
    }

    public Tile findLeastUsefulTile() {
        if (numberOfTiles == 0) return null;
        for (int i = 0; i < numberOfTiles; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < numberOfTiles; j++) {
                if (i != j && playerTiles[i].getValue() == playerTiles[j].getValue()) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                return playerTiles[i];
            }
        }
        return playerTiles[numberOfTiles - 1];
    }

    public Tile removeTile(Tile t) {
        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i].compareTo(t) == 0) {
                return getAndRemoveTile(i);
            }
        }
        return null;
    }

    public boolean canUseTile(Tile t) {
        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i].canFormChainWith(t)) {
                return true;
            }
        }
        return false;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }

    public int getNumberOfTiles(){
        return numberOfTiles;
    }
}
