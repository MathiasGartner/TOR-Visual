package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Baumschwamm extends RandomWalker
{

    public Baumschwamm(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name="Baumschwamm";
        nameLatin="Fomitopsis pinicola";
    }

    @Override
    public void draw() {

    }
}
