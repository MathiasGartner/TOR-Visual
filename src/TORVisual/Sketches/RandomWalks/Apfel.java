package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Apfel extends RandomWalker
{

    public Apfel(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name="Apfelscheiben";
        nameLatin="Malus domestica";
    }

    @Override
    public void draw() {

    }
}
