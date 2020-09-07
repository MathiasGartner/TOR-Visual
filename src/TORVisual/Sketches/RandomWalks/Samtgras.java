package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Samtgras extends RandomWalker
{

    public Samtgras(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Samtgras";
        nameLatin="Lagurus ovatus";
    }

    @Override
    public void draw() {

    }
}
