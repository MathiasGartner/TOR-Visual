package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Palmringe extends RandomWalker
{

    public Palmringe(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Palmringe";
        nameLatin="Borassus flabellifer";
    }

    @Override
    public void draw() {

    }
}
