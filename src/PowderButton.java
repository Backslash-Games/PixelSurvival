public class PowderButton extends Button{

    int id = 0;

    public PowderButton(int x, int y, int width, int height, int powderID)
    {
        origin = new Point(x, y);
        size = new Point(width, height);

        id = powderID;
    }
    @Override
    public void OnClicked() {
        Program.instance.SetPenType(id);
    }
}
