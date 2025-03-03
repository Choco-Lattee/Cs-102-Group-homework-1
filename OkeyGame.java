
import java.util.ArrayList;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int currentTile = 0;

        for(int i = 0; i < players.length; i++){
            if(i == 0){
                for(int a = 0; a < 15; a++, currentTile++)
                    players[i].addTile(tiles[currentTile]);
            }

            else{
                for(int a = 0; a < 14; a++, currentTile++)
                    players[i].addTile(tiles[currentTile]);
            }
        }
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile temp = tiles[0];
        Tile[] newtiles = new Tile[tiles.length - 1];

        for(int i = 0; i < tiles.length - 1; i++){
            newtiles[i] = tiles[i + 1];
        }

        tiles = newtiles;

        return temp.toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() 
    {
        int count = 0;
        ArrayList<Tile> shuffledTiles = new ArrayList<>();
        for(Tile tile:tiles)
        {
            shuffledTiles.add(tile);
            count++;
            
        }
        for(int i = 0; i < tiles.length; i++)
        {
            int random = (int)(Math.random() * (count - 1));
            tiles[i] = shuffledTiles.get(random);
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        if(tiles.length <= 0)
        {
            return true;
        }
        return players[getCurrentPlayerIndex()].isWinningHand();
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
    Player currentPlayer = players[currentPlayerIndex];

    if (lastDiscardedTile == null) { 
        System.out.println("No discarded tile, picking from tiles.");
        currentPlayer.addTile(new Tile(getTopTile().charAt(0), getTopTile().charAt(1)));
        return;
    }

    boolean isTheDiscardedTileUseful = false;
    int count =0;
    for (int i = 0; i < currentPlayer.getNumberOfTiles(); i++) {
        if (lastDiscardedTile != null && currentPlayer.getTiles()[i] != null && lastDiscardedTile.getValue() == currentPlayer.getTiles()[i].getValue() &&
            lastDiscardedTile.getColor() != currentPlayer.getTiles()[i].getColor()) {
            count++;
            if(count == 2){
                isTheDiscardedTileUseful = true;
                lastDiscardedTile = null;
                break;
            }
        }
    }

    if (isTheDiscardedTileUseful) {
        System.out.println("Computer picks from discarded ones.");
        currentPlayer.addTile(lastDiscardedTile); 
    } else {
        System.out.println("Computer picks from tiles.");
        currentPlayer.addTile(new Tile(getTopTile().charAt(0), getTopTile().charAt(1)));
    }
}

    /*
     * Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
public void discardTileForComputer() {
    Player currentPlayer = players[currentPlayerIndex];
    Tile leastUsefulTile = findLeastUsefulTile(currentPlayer);
    
    if (leastUsefulTile != null) {
        int tileIndex = currentPlayer.findPositionOfTile(leastUsefulTile);
        
        if (tileIndex != -1) { //ensure the tile exists before discarding
            discardTile(tileIndex);
        } else {
            System.out.println("Error: Could not find tile " + leastUsefulTile.toString() + " to discard.");
        }
    } else {
        System.out.println("Error: No valid tile found to discard.");
    }
}

    
    /*
     * helper for discardTileForComputer
     */
private Tile findLeastUsefulTile(Player player) {
    Tile[] playerTiles = player.getTiles();
    int[] tileCounts = new int[8]; //index 0 is unused, values from [1,7]
    Tile leastUsefulTile = playerTiles[0]; //declare and initialize

    //count occurrences for each tile value
    for (int i = 0; i < player.numberOfTiles; i++) 
    {
        if(playerTiles[i] != null && playerTiles[i].getValue() < tileCounts.length)
        {
            tileCounts[playerTiles[i].getValue()]++;
        }
    }

    //find the most frequent duplicate to discard first
    Tile duplicateTile = null;
    for (int i = 0; i < player.numberOfTiles; i++) 
    {
        if (playerTiles[i] != null && tileCounts.length > playerTiles[i].getValue() && tileCounts[playerTiles[i].getValue()] > 1) 
        {
            duplicateTile = playerTiles[i];  // Keep last duplicate found
        }
    }

    if (duplicateTile != null) {
        return duplicateTile; //return a duplicate if found
    }

    //find the tile with the lowest count
    int minCount = tileCounts[playerTiles[0].getValue()];
    for (int i = 1; i < player.numberOfTiles; i++) {
        if(playerTiles[i]==null)
        {
            int value = playerTiles[i-1].getValue();
            minCount = tileCounts[value];
            leastUsefulTile = playerTiles[i];
            break;
        }
        int value = playerTiles[i].getValue();
        if (tileCounts[value] < minCount) {
            minCount = tileCounts[value];
            leastUsefulTile = playerTiles[i];
        }
    }

    return leastUsefulTile;
}



    
    /*
     * discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
public void discardTile(int tileIndex) {
    Player currentPlayer = players[currentPlayerIndex];

    if (tileIndex < 0 || tileIndex >= currentPlayer.numberOfTiles) {
        System.out.println("Invalid tile index.");
        return;
    }

    lastDiscardedTile = currentPlayer.getAndRemoveTile(tileIndex);

    //clear the correct last tile slot
    if (currentPlayer.numberOfTiles > 0) 
    {
        currentPlayer.getTiles()[currentPlayer.numberOfTiles - 1] = null;
    }
    
    displayDiscardInformation();
}


    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
