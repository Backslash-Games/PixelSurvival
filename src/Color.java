public class Color
{
    public int r = 255;
    public int g = 255;
    public int b = 255;
    public int a = 255;

    public Color(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public Color(int r, int g, int b, int a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color Normalize(){
        int colorDist = 0;
        // -> Find largest value
        if(r > g){
            if(r > b){
                colorDist = 255 - r;
            }
            else{
                colorDist = 255 - b;
            }
        }
        else{
            if(g > b){
                colorDist = 255 - g;
            }
            else{
                colorDist = 255 - b;
            }
        }

        int sr = r + colorDist;
        int sg = g + colorDist;
        int sb = b + colorDist;

        return new Color(sr, sg, sb);
    }

    @Override
    public String toString() {
        return "(" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
