package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class Overview extends EmbeddedSketch {

    public Overview(PApplet sketch, SketchArea sketchArea) {
        super(sketch, sketchArea);
    }

    @Override
    public void draw() {
        sketch.fill(0, 250, 100);
        sketch.rect(sketch.mouseX, sketch.mouseY, 5, 20);
    }
}
