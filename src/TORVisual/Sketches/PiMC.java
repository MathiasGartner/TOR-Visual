package TORVisual.Sketches;

import TORVisual.Data.DiceResult;
import TORVisual.Data.PiMCPoint;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.Random;
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

    private int squareL;
    private float squareL2;
    private float graphX;
    private float graphY;

    private double pi;
    private int total;
    private int inCircle;

    PGraphics piGraph;

    Random r = new Random();

    public PiMC(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);

        this.resultsNeededForOneCoordinate = 7;
        this.resultsNeededForNewPosition = resultsNeededForOneCoordinate * 2;
        this.boxSizeTotal = Integer.parseInt(new String(new char[resultsNeededForOneCoordinate]).replace('\0', '5'), 6); //base 6 numbers are from 0 to 5
        this.boxSize = this.boxSizeTotal / 2;
        this.points = new ArrayList<PiMCPoint>();
        this.pointsToProcess = new ArrayList<PiMCPoint>();
        this.newResults = newResults;
        this.resultsToProcess = new ArrayList<Integer>();

        this.squareL = (int)(this.area.w / 10.0 * 8);
        this.squareL2 = this.squareL / 2.0f;
        this.graphX = this.area.w / 10.0f;
        this.graphY = this.area.h / 10.0f;

        this.pi = 1.0;
        this.total = 0;
        this.inCircle = 0;

        this.piGraph = sketch.createGraphics(this.squareL, this.squareL);
        this.piGraph.noStroke();
        piGraph.beginDraw();
        piGraph.fill(100);
        piGraph.rect(0, 0, this.squareL, this.squareL);
        piGraph.fill(150);
        piGraph.circle(this.squareL2, this.squareL2, this.squareL);
        piGraph.fill(255);
        piGraph.endDraw();

        this.setBackgroundColor(0, 0, 0);

        r.setSeed(12345);
    }

    @Override
    public void draw() {
        this.pointsToProcess.clear();
        for (var result : this.newResults) {
            this.resultsToProcess.add(result.Result);
            this.createPosition();
        }

        this.clear();
        sketch.fill(255);
        sketch.text("pi = " + this.pi, 5, 15);

        piGraph.beginDraw();
        for (var p : this.pointsToProcess) {
            piGraph.circle(p.drawAt.x, p.drawAt.y, 5);
        }
        piGraph.endDraw();
        sketch.image(piGraph, this.graphX, this.graphY);
    }

    private void createPosition() {
        if (this.resultsToProcess.size() >= this.resultsNeededForNewPosition) {
            var results = resultsToProcess.stream().map(r -> Integer.toString(r - 1)).collect(Collectors.joining("")); //base 6 numbers are from 0 to 5
            float x = Integer.parseInt(results.substring(0, resultsNeededForOneCoordinate), 6) - this.boxSize;
            float y = Integer.parseInt(results.substring(resultsNeededForOneCoordinate, 2 * resultsNeededForOneCoordinate), 6) - this.boxSize;
            PiMCPoint p = new PiMCPoint(x / boxSize, y / boxSize, this.squareL2);
            //x = r.nextFloat() * 2 - 1;
            //y = r.nextFloat() * 2 - 1;
            //PiMCPoint p = new PiMCPoint(x, y, this.squareL2);
            this.pointsToProcess.add(p);
            this.total++;
            if (p.inCircle) {
                this.inCircle++;
            }
            this.pi = 4.0 * this.inCircle / (double)this.total;
            for (int i = 0; i < this.resultsNeededForNewPosition; i++) {
                resultsToProcess.remove(0);
            }
        }
    }
}
