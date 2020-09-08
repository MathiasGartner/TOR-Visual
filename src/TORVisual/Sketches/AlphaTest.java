package TORVisual.Sketches;

import TORVisual.Database.DiceResult;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

public class AlphaTest extends EmbeddedSketch {

    public AlphaTest(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);
    }

    @Override
    public void draw() {
        this.canvas.fill(255, 0, 0, 40);
        this.canvas.rect(40, 40, 100, 100);
        this.canvas.rect(60, 60, 100, 100);
    }
}
