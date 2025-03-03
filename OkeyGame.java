import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OkeyGame {

    Player[] players;
    List<Tile> tiles;
    Tile lastDiscardedTile;
    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    /*
     * Creates the full set of tiles for the game.
     * There are 4 copies of each color-value combination.
     */
    public void createTiles() {
        tiles = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles.add(new Tile(i, 'Y'));
                tiles.add(new Tile(i, 'B'));
                tiles.add(new Tile(i, 'R'));
                tiles.add(new Tile(i, 'K'));
            }
        }
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Collections.shuffle(tiles);
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        for (int i = 0; i < players.length; i++) {
            int tileCount = (i == 0) ? 15 : 14;
            for (int j = 0; j < tileCount; j++) {
                if (!tiles.isEmpty()) {
                    players[i].addTile(tiles.remove(0));
                }
            }
        }
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public void displayDiscardInformation() {
        if (lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public String getLastDiscardedTile() {
        return (lastDiscardedTile != null) ? lastDiscardedTile.toString() : "None";
    }

    public String getTopTile() {
        if (tiles.isEmpty()) {
            return null;
        }
        return tiles.remove(0).toString();
    }

    public boolean didGameFinish() {
        if (tiles.isEmpty()) {
            System.out.println("No more tiles left in the stack. Game ends in a tie.");
            return true;
        }
        for (Player player : players) {
            if (player.isWinningHand()) {
                System.out.println(player.getName() + " wins.");
                return true;
            }
        }
        return false;
    }

    public void discardTile(int tileIndex) {
        Player currentPlayer = players[currentPlayerIndex];
        if (tileIndex < 0 || tileIndex >= currentPlayer.getNumberOfTiles()) {
            System.out.println("Invalid tile index.");
            return;
        }
        lastDiscardedTile = currentPlayer.getAndRemoveTile(tileIndex);
        System.out.println("Discarded tile: " + lastDiscardedTile);
    }

    public void pickTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        if (lastDiscardedTile != null && currentPlayer.canUseTile(lastDiscardedTile)) {
            System.out.println("Computer picks from discarded ones: " + lastDiscardedTile);
            currentPlayer.addTile(lastDiscardedTile);
            lastDiscardedTile = null;
        } else {
            if (!tiles.isEmpty()) {
                System.out.println("Computer picks from tile stack.");
                currentPlayer.addTile(tiles.remove(0));
            } else {
                System.out.println("No more tiles to draw from the stack.");
            }
        }
    }

    public void discardTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        Tile tileToDiscard = currentPlayer.findLeastUsefulTile();
        if (tileToDiscard != null) {
            lastDiscardedTile = currentPlayer.removeTile(tileToDiscard);
            System.out.println("Computer discarded: " + lastDiscardedTile);
        }
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if (index >= 0 && index < 4) {
            players[index] = new Player(name);
        }
    }
}


