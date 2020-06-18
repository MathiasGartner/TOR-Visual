package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class Overview extends EmbeddedSketch {

    public Overview(PApplet sketch, SketchArea area) {
        super(sketch, area);
    }

    @Override
    public void draw() {
        sketch.fill(100, 200, 100);
        sketch.line(0, 0, this.area.w, this.area.h);
        sketch.line(0, this.area.h, this.area.w, 0);
        sketch.fill(0, 250, 100);
        sketch.rect(sketch.mouseX, sketch.mouseY, 5, 20);
    }
}
