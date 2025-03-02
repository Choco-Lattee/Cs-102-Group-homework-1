
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

    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return null;
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
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
            System.out.println(tiles[i]);
        }
        System.out.println(shuffledTiles);
        for(int i = 0; i < tiles.length; i++)
        {
            int random = (int)(Math.random() * (count - 1));
            tiles[i] = shuffledTiles.get(random);
        }
        for(int i = 0; i < tiles.length; i++)
        {
            System.out.println(tiles[i]);
        }
        System.out.println(shuffledTiles);

    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return players[getCurrentPlayerIndex()].isWinningHand();
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() 
    {
        Player currentPlayer = players[currentPlayerIndex];
        boolean isTheDiscardedTileUseful = false;

        for(int i =0; i < 15; i++){
            if((getLastDiscardedTile().charAt(0) == currentPlayer.getTiles()[i].toString().charAt(0)) 
            && getLastDiscardedTile().charAt(1) != currentPlayer.getTiles()[i].toString().charAt(1)){
                isTheDiscardedTileUseful =true;
                break;
            }
        }

        if(isTheDiscardedTileUseful){
            System.out.println("Computer picks from discarded ones.");
            currentPlayer.addTile(lastDiscardedTile); 
        }
        else{
            System.out.println("Computer picks from tiles.");
            currentPlayer.addTile(new Tile(getTopTile().charAt(0),getTopTile().charAt(1)));
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
            discardTile(tileIndex);
            lastDiscardedTile = leastUsefulTile;
            displayDiscardInformation();
        }
    }
    
    /*
     * helper for discardTileForComputer
     */
    private Tile findLeastUsefulTile(Player player) {
        Tile[] playerTiles = player.getTiles();
        int[] tileCounts = new int[8]; //tiles are from 1-7 (no 0-index usage)
        
        for (int i = 0; i < player.numberOfTiles; i++) { //find occurrences of each tile value
            tileCounts[playerTiles[i].getValue()]++;
        }
    
        //prioritize discarding duplicates first
        for (int i = 0; i < player.numberOfTiles; i++) {
            if (tileCounts[tiles[i].getValue()] > 1) {
                return playerTiles[i];
            }
        }

        //if there is no duplicate, discard the tile contributing the least to chains
        return playerTiles[0];
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
