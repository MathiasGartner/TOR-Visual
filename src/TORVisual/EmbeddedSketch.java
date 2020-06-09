package TORVisual;

import processing.core.PApplet;

public abstract class EmbeddedSketch {
    protected PApplet sketch;
    protected SketchArea area;

    protected EmbeddedSketch(PApplet sketch, SketchArea area) {
        this.sketch = sketch;
        this.area = area;
    }

    protected EmbeddedSketch(PApplet sketch, int x, int y, int w, int h) {
        this(sketch, new SketchArea(x, y, w, h));
    }

    public abstract void draw();
}
