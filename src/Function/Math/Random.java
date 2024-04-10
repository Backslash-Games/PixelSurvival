package Function.Math;

public class Random {
    static java.util.Random rng = null;

    public static int Range(int min, int max)
    {
        if(rng == null)
            rng = new java.util.Random();
        return rng.nextInt(max - min) + min;
    }
    public static int Range(int max)
    {
        return Range(0, max);
    }
}
