package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Wachtelbohne extends RandomWalker
{

    public Wachtelbohne(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Wachtelbohne";
        nameLatin="Phaseolus vulgaris";
    }

    @Override
    public void draw() {

    }
}
