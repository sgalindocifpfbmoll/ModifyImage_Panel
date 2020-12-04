package Modificator;

public class Modifications {
    // Modifications
    public static final int[][] BLUR = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
    public static final int[][] SHARP = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
    public static final int[][] NEGATIVE = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};

    public static final int DELETEIMAGE = 0;
    public static final int BLURIMAGE = 1;
    public static final int SHARPIMAGE = 2;
    public static final int NEGATIVEIMAGE = 3;
    public static final int ORIGINALIMAGE = 4;
}
