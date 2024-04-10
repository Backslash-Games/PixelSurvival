public class Button {
    public Point origin;
    public Point size;

    public Button() { }
    public Button(int x, int y, int width, int height)
    {
        origin = new Point(x, y);
        size = new Point(width, height);
    }

    public boolean PointInside(Point position)
    {
        return position.X > origin.X && position.Y > origin.Y && position.X <= origin.X + size.X && position.Y <= origin.Y + size.Y;
    }

    public void OnClicked() { }
}
