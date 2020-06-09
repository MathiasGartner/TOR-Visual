package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class MCResults extends EmbeddedSketch {

    public MCResults(PApplet sketch, SketchArea sketchArea) {
        super(sketch, sketchArea);
    }

    @Override
    public void draw() {
        sketch.fill(50, 50, 200);
        sketch.rect(sketch.mouseX, sketch.mouseY, 15, 20);
    }
}
