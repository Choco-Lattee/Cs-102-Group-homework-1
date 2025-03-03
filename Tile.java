public class Tile {
    
    int value;
    char color;

    /*
     * Creates a tile using the given color and value, colors are represented
     * using the following letters: Y: Yellow, B: Blue, R: Red, K: Black
     * Values can be in the range [1,7]. There are four tiles of each color-value
     * combination (7 * 4 * 4) = 112 tiles, false jokers are not included in this game.
     */
    public Tile(int value, char color) {
        this.value = value;
        this.color = color;
    }

    /*
     * Compares tiles so that they can be added to the hands in order
     */
    public int compareTo(Tile t) {
        if (t == null) {
            return -1;
        }
        if (getValue() < t.getValue()) {
            return -1;
        } else if (getValue() > t.getValue()) {
            return 1;
        } else {
            return Integer.compare(colorNameToInt(), t.colorNameToInt());
        }
    }

    /*
     * Converts color to an integer value for easy comparison
     */
    public int colorNameToInt() {
        switch (color) {
            case 'Y': return 0;
            case 'B': return 1;
            case 'R': return 2;
            case 'K': return 3;
            default: return -1;
        }
    }

    /*
     * Determines if this tile can make a chain with the given tile
     * A chain is formed if the tiles have the same value but different colors
     */
    public boolean canFormChainWith(Tile t) {
        return t != null && t.getColor() != color && t.getValue() == value;
    }

    public String toString() {
        return "" + value + color;
    }

    public int getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }
}