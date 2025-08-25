package data.race;

public class GameMap {
    private final int lanes;      // tory
    private final int length;     // kolumny
    private final int visibleX;   // liczba widocznych kolumn na ekranie
    private final int[][][] map;  // [tor][kolumna][rodzaj przeszkody]
    private int offset = 0;       // które kolumny są widoczne

    public GameMap(int lanes, int length, int[][][] mapData, int visibleX) {
        this.lanes = lanes;
        this.length = length;
        this.visibleX = visibleX;
        this.map = mapData;
    }

    public int getObstacle(int lane, int col) {
        if (lane < 0 || lane >= lanes || col + offset >= length) return 0;
        return map[lane][col + offset][0];
    }

    public void scroll() {
        if (offset < length - visibleX) offset++;
    }

    public int getLanes() { return lanes; }

    public int getOffset() { return offset; }

    public int getVisibleCols() { return visibleX; }

    public int getLength() { return length; }
}
