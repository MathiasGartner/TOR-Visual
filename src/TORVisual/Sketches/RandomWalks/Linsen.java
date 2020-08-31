package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Linsen extends RandomWalker
{

    public Linsen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Linsen";
        nameLatin="Lens culinaris";
    }

    @Override
    public void draw() {

    }
}
