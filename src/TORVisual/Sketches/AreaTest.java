package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class AreaTest extends EmbeddedSketch {

    public AreaTest(PApplet sketch, SketchArea area) {
        super(sketch, area);
    }

    @Override
    public void draw() {
        sketch.stroke(255, 255, 255);
        sketch.fill(255, 255, 255);
        sketch.line(0, 0, this.area.w, this.area.h);
        sketch.line(0, this.area.h, this.area.w, 0);
        sketch.stroke(200, 250, 0);
        sketch.fill(200, 250, 0);
        sketch.rect(sketch.mouseX, sketch.mouseY, 5, 20);
    }
}
