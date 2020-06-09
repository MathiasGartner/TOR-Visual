package TORVisual;

public class SketchArea {
    public int x;
    public int y;
    public int w;
    public int h;
    public int xw;
    public int yh;

    public SketchArea(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.xw = x + w;
        this.yh = y + h;
    }
}
