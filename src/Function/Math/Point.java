package Function.Math;

public class Point implements Comparable<Point>
{
    public int X = 0;
    public int Y = 0;

    public static Point up = new Point(0, -1);
    public static Point right = new Point(1, 0);
    public static Point down = new Point(0, 1);
    public static Point left = new Point(-1, 0);
    public static Point one = new Point(1, 1);
    public static Point zero = new Point(0, 0);

    public Point(int x, int y){
        X = x;
        Y = y;
    }

    public static Point add(Point cPoint, Point oPoint){
        return new Point(cPoint.X + oPoint.X, cPoint.Y + oPoint.Y);
    }
    public static Point subtract(Point cPoint, Point oPoint){
        return new Point(cPoint.X - oPoint.X, cPoint.Y - oPoint.Y);
    }
    public static Point multiply(Point cPoint, Point oPoint){
        return new Point(cPoint.X * oPoint.X, cPoint.Y * oPoint.Y);
    }
    public static Point divide(Point cPoint, Point oPoint){
        return new Point(Math.floorDiv(cPoint.X, oPoint.X), Math.floorDiv(cPoint.Y, oPoint.Y));
    }
    public static Point invertMagnitude(Point cPoint){
        return Point.multiply(cPoint, new Point(-1, -1));
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
