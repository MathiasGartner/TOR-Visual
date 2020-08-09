package TORVisual.Sketches;

import TORVisual.Data.DiceResult;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

public class PiMC extends EmbeddedSketch {

    private int resultsNeededForNewPosition;
    protected ArrayList<Point2D.Double> points;
    protected ArrayList<Point2D.Double> pointsToProcess;
    protected ArrayList<DiceResult> newResults;
    protected ArrayList<Integer> resultsToProcess;

    protected PiMC(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);

        this.resultsNeededForNewPosition = 10;
        this.points = new ArrayList<Point2D.Double>();
        this.pointsToProcess = new ArrayList<Point2D.Double>();
        this.newResults = newResults;
        this.resultsToProcess = new ArrayList<Integer>();
    }

    @Override
    public void draw() {
        this.pointsToProcess.clear();
        for (var result : this.newResults) {
            this.resultsToProcess.add(result.Result);
            this.createPosition();
        }
        for (var p : this.pointsToProcess) {
            //TODO: draw point
            //TODO: update pi
        }
    }

    private void createPosition() {
        if (this.resultsToProcess.size() > this.resultsNeededForNewPosition) {
            Point2D.Double p = new Point2D.Double();
            p.x = 0; //TODO!
            p.y = 0;
            this.pointsToProcess.add(p);
            this.resultsToProcess.clear();
        }
    }
}
