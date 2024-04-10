package Function.Math;

public class Point implements Comparable<Point>
{
    public int X = 0;
    public int Y = 0;

    public Point(int x, int y){
        X = x;
        Y = y;
    }

    public static Point add(Point cPoint, Point oPoint){
        return new Point(cPoint.X + oPoint.X, cPoint.Y + oPoint.Y);
    }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ")";
    }

    @Override
    public int compareTo(Point o) {
        return 0;
    }
}
