package TORVisual;

import processing.core.PApplet;

public abstract class EmbeddedSketch {
    protected PApplet sketch;
    protected SketchArea area;
    protected int backgroundColor;

    protected EmbeddedSketch(PApplet sketch, SketchArea area) {
        this.sketch = sketch;
        this.area = area;
        this.backgroundColor = sketch.color(10, 10, 10);
    }

    protected EmbeddedSketch(PApplet sketch, int x, int y, int w, int h) {
        this(sketch, new SketchArea(x, y, w, h));
    }

    public abstract void draw();

    public void setBackgroundColor(int r, int g, int b) {
        this.backgroundColor = sketch.color(r, g, b);
    }

    public void setBackgroundColor(int r, int g, int b, int a) {
        this.backgroundColor = sketch.color(r, g, b, a);
    }
}
