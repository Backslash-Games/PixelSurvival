package Debug;

public class Console {
    public static final String ANSI_RESET = "\u001B[0m";

    // -> Base Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // -> Tags
    public static final String ERROR_TAG = RED + "[ERROR]" + ANSI_RESET;

    // -> Console out flags
    public static boolean writeDraw = false;
    public static boolean writeKeypress = false;
    public static boolean writeButtonCreation = false;

    public static String Format(String text, String colorCode){
        return colorCode + "[" + text + "]" + ANSI_RESET;
    }

    public static void out(String sourceText, String colorCode, String message){
        System.out.println(Format(sourceText, colorCode) + ": " + message);
    }
}

