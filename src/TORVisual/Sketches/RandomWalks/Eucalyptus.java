package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Eucalyptus extends RandomWalker
{

    public Eucalyptus(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Eucalyptus";
        nameLatin="Eucalyptus ovata";
    }

    @Override
    public void draw() {

    }
}
