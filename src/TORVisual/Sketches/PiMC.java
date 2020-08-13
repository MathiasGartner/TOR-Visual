package TORVisual.Sketches;

import TORVisual.Data.DiceResult;
import TORVisual.Data.PiMCPoint;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PiMC extends EmbeddedSketch {

    private int resultsNeededForOneCoordinate;
    private int resultsNeededForNewPosition;
    private int boxSize;
    private int boxSizeTotal;
    protected ArrayList<PiMCPoint> points;
    protected ArrayList<PiMCPoint> pointsToProcess;
    protected ArrayList<DiceResult> newResults;
    protected ArrayList<Integer> resultsToProcess;

    private float squareL;
    private float squareL2;
    private float squareX;
    private float squareY;

    public PiMC(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);

        this.resultsNeededForOneCoordinate = 10;
        this.resultsNeededForNewPosition = resultsNeededForOneCoordinate * 2;
        this.boxSizeTotal = Integer.parseInt(new String(new char[resultsNeededForOneCoordinate]).replace('\0', '5'), 6); //base 6 numbers are from 0 to 5
        this.boxSize = this.boxSizeTotal / 2;
        this.points = new ArrayList<PiMCPoint>();
        this.pointsToProcess = new ArrayList<PiMCPoint>();
        this.newResults = newResults;
        this.resultsToProcess = new ArrayList<Integer>();

        this.squareL = this.area.w / 10.0f * 8;
        this.squareL2 = this.squareL / 2.0f;
        this.squareX = this.area.w / 10.0f;
        this.squareY = this.area.h / 10.0f;
    }

    @Override
    public void draw() {
        //this.pointsToProcess.clear();
        for (var result : this.newResults) {
            this.resultsToProcess.add(result.Result);
            this.createPosition();
        }
        sketch.pushMatrix();
        sketch.translate(this.squareX, this.squareY);
        sketch.fill(100);
        sketch.rect(0, 0, this.squareL, this.squareL);
        sketch.fill(150);
        sketch.circle(this.squareL2, this.squareL2, this.squareL);
        sketch.fill(255);
        for (var p : this.pointsToProcess) {
            sketch.circle(p.drawAt.x, p.drawAt.y, 5);
            System.out.println(p.toString());
            //TODO: update pi
        }
        sketch.popMatrix();
    }

    private void createPosition() {
        if (this.resultsToProcess.size() >= this.resultsNeededForNewPosition) {
            var results = resultsToProcess.stream().map(r -> Integer.toString(r - 1)).collect(Collectors.joining("")); //base 6 numbers are from 0 to 5
            float x = Integer.parseInt(results.substring(0, resultsNeededForOneCoordinate), 6) - this.boxSize;
            float y = Integer.parseInt(results.substring(resultsNeededForOneCoordinate, 2 * resultsNeededForOneCoordinate), 6) - this.boxSize;
            PiMCPoint p = new PiMCPoint(x / boxSize, y / boxSize, this.squareL);
            this.pointsToProcess.add(p);
            for (int i = 0; i < this.resultsNeededForNewPosition; i++) {
                resultsToProcess.remove(0);
            }
        }
    }
}
