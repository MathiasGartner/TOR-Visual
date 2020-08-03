package TORVisual;

import TORVisual.Data.DBManager;
import TORVisual.Data.DiceResult;
import TORVisual.Settings.SettingsVisual;
import TORVisual.Sketches.AreaTest;
import TORVisual.Sketches.RandomWalks.*;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import processing.core.PApplet;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainCanvas extends PApplet {

    private static int counter = 0;
    private int lastDiceResultId = -1;
    private ArrayList<DiceResult> nextDiceResults;
    private ArrayList<DiceResult> resultsToShow;

    private int displayId;
    private int screenW;
    private int screenH;
    private boolean fullScreen;

    private AreaTest areaTest1;
    private AreaTest areaTest2;
    private AreaTest areaTest3;
    private ArrayList<EmbeddedSketch> sketches;
    private ArrayList<SketchArea> sketchAreas;
    private int borderPx;

    private MainCanvas() {
        this.resultsToShow = new ArrayList<DiceResult>();
        this.resultsToShow.add(new DiceResult());
        this.borderPx = 13;
    }

    public MainCanvas(int displayId) {
        this();
        this.displayId = displayId;
        fullScreen = true;
    }

    public MainCanvas(int displayId, int w, int h) {
        this();
        this.displayId = displayId;
        this.screenW = w;
        this.screenH = h;
        fullScreen = false;
    }

    public void settings() {
        if (this.fullScreen) {
            fullScreen(P2D);
        }
        else {
            size(this.screenW, this.screenH);
        }
        //define sketch areas
        sketchAreas = new ArrayList<SketchArea>();
        sketchAreas.add(new SketchArea(0, 0, (int) (screenW * 0.7), screenH));
        int currentX = sketchAreas.get(0).xw + borderPx;
        sketchAreas.add(new SketchArea(currentX, 0, screenW - currentX, (int) (screenH * 0.6)));
        int currentY = sketchAreas.get(1).yh + borderPx;
        sketchAreas.add(new SketchArea(currentX, currentY, screenW - currentX, screenH - currentY));

        //create sketches
        areaTest1 = new AreaTest(this, sketchAreas.get(0));
        areaTest1.setBackgroundColor(20, 50, 100);
        areaTest2 = new AreaTest(this, sketchAreas.get(1));
        areaTest2.setBackgroundColor(220, 50, 100);
        areaTest3 = new AreaTest(this, sketchAreas.get(2));
        areaTest3.setBackgroundColor(20, 250, 100);

        //RandomWalker randomWalkerSketch = new RandomWalker(this, sketchAreas.get(0));
        //Ellipse EllipseSketch = new Ellipse(this, sketchAreas.get(0));
        //Circle CircleSketch = new Circle(this, sketchAreas.get(0));
        //EllipseCotton EllipseCottonSketch = new EllipseCotton(this, sketchAreas.get(0));
        //WoodCircle WoodCircleSketch = new WoodCircle(this, sketchAreas.get(0));
        Sternanis SternanisSketch = new Sternanis(this, sketchAreas.get(0), this.resultsToShow);
        SternanisSketch.setRecentDiceResultsCount(5);
        RoundSquare RoundSquareSketch = new RoundSquare(this, sketchAreas.get(1), this.resultsToShow);
        RoundSquareSketch.setRecentDiceResultsCount(15);
        //add sketches to sketch-list
        sketches = new ArrayList<EmbeddedSketch>();
        //sketches.add(CircleSketch);
        //sketches.add(EllipseCottonSketch);
        //sketches.add(EllipseSketch);
        //sketches.add(WoodCircleSketch);
        sketches.add(SternanisSketch);
        sketches.add(RoundSquareSketch);
        sketches.add(areaTest3);
    }

    public void setup() {
        for (var sketch : sketches) {
            stroke(sketch.backgroundColor);
            fill(sketch.backgroundColor);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
        }
    }

    double resultsPerFrame;
    double lastShownResultIndex;


    public void draw() {
        if (counter % SettingsVisual.LoadDataEveryNthFrame == 0) {
            DBManager db = new DBManager();
            try {
                nextDiceResults = db.getDiceResultAboveId(lastDiceResultId);
                resultsPerFrame = (double)nextDiceResults.size() / SettingsVisual.LoadDataEveryNthFrame;
                lastShownResultIndex = 0;
                if (nextDiceResults.size() > 0) {
                    lastDiceResultId = nextDiceResults.get(nextDiceResults.size() - 1).Id;
                }
                System.out.println("results ids until: " + lastDiceResultId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            counter = 0;
        }
        counter++;
        //TODO: make sure also the last result in nextDiceResults is shown
        double showResultsUpToIndex = lastShownResultIndex + resultsPerFrame;
        resultsToShow.clear();
        for (int i = (int)lastShownResultIndex; i < (int)showResultsUpToIndex; i++) {
           resultsToShow.add(nextDiceResults.get(i));
        }
        if (resultsToShow.size() > 0) {
            System.out.println("frame: " + frameCount);
            for (var dr : resultsToShow) System.out.println(dr.Id);
        }
        //System.out.println("size: " + resultsToShow.size());
        //for (var dr : resultsToShow) System.out.println(dr.Id);
        lastShownResultIndex = showResultsUpToIndex;

        //draw sketches
        for (var sketch : sketches) {
            sketch.addNewDiceResults(resultsToShow);
            pushMatrix();
            translate(sketch.area.x, sketch.area.y);
            sketch.draw();
            popMatrix();
        }
        //draw borders
        for (var area : sketchAreas) {
            stroke(0, 0, 0);
            fill(0, 0, 0);
            rect(area.x - borderPx, area.y - borderPx, area.xw + 2 * borderPx, borderPx);
            rect(area.x - borderPx, area.y - borderPx, borderPx, area.yh + 2 * borderPx);
            rect(area.x - borderPx, area.yh, area.xw + 2 * borderPx, borderPx);
            rect(area.xw, area.y - borderPx , borderPx, area.yh + 2 * borderPx);
        }
    }
}
