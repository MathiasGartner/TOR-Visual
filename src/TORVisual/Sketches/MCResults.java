package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class MCResults extends EmbeddedSketch {

    public MCResults(PApplet sketch, SketchArea area) {
        super(sketch, area);
    }

    @Override
    public void draw() {
        sketch.fill(100, 100, 100);
        sketch.line(0, 0, this.area.w, this.area.h);
        sketch.line(0, this.area.h, this.area.w, 0);
        sketch.fill(50, 50, 200);
        sketch.rect(sketch.mouseX, sketch.mouseY, 15, 20);
    }
}
