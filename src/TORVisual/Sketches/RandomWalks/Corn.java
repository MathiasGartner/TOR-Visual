package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Corn extends RandomWalker
{

    public Corn(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Gerstenähren";
        nameLatin="Hordeum vulgare";
    }

    @Override
    public void draw() {

    }
}
