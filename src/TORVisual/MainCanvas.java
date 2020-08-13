package TORVisual;

import TORVisual.Data.DBManager;
import TORVisual.Data.DiceResult;
import TORVisual.Settings.SettingsVisual;
import TORVisual.Sketches.AreaTest;
import TORVisual.Sketches.PiMC;
import TORVisual.Sketches.RandomWalks.*;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainCanvas extends PApplet {

    private static int counter = 0;
    private static int resultCounter = 0;
    private int lastDiceResultId = -1;
    private ArrayList<DiceResult> nextDiceResults;
    private ArrayList<DiceResult> resultsToShow;

    private int displayId;
    private int screenW;
    private int screenH;
    private boolean fullScreen;
    PGraphics info;

    private AreaTest areaTest1;
    private AreaTest areaTest2;
    private AreaTest areaTest3;
    private ArrayList<EmbeddedSketch> sketches;
    private ArrayList<SketchArea> sketchAreas;
    private int borderPx;

    private MainCanvas() {
        this.resultsToShow = new ArrayList<DiceResult>();
        this.resultsToShow.add(new DiceResult());
        this.borderPx = 0;
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
        var renderer = JAVA2D;
        if (this.fullScreen) {
            fullScreen(renderer, displayId);
        }
        else {
            size(this.screenW, this.screenH, renderer);
        }
    }

    public void setup() {
        if (this.fullScreen) {
            this.screenW = this.width;
            this.screenH = this.height;
        }

        info = createGraphics(230, 60);
        info.noStroke();

        //define sketch areas
        sketchAreas = new ArrayList<SketchArea>();
        int d = screenH / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //sketchAreas.add(new SketchArea(i * d, j * d, d, d));
            }
        }
        //sketchAreas.add(new SketchArea(screenH + 1, 0, screenW - screenH, screenH));
        sketchAreas.add(new SketchArea(0, 0, 300, 300));
        //create sketches
        sketches = new ArrayList<EmbeddedSketch>();
        for (int i = 0; i < 9; i++) {
            var sketch = new AreaTest(this, sketchAreas.get(i));
            sketch.setBackgroundColor(10 * i, 20 * i, 200 / (i + 1));
            sketches.add(sketch);
        }
        PiMC piMCSketch = new PiMC(this, sketchAreas.get(9), this.resultsToShow);
        sketches.add(piMCSketch);

        /*
        //RandomWalker randomWalkerSketch = new RandomWalker(this, sketchAreas.get(0));
        //Ellipse EllipseSketch = new Ellipse(this, sketchAreas.get(0));
        //Circle CircleSketch = new Circle(this, sketchAreas.get(0));
        //WoodCircle WoodCircleSketch = new WoodCircle(this, sketchAreas.get(0));

        SternanisSketch.setRecentDiceResultsCount(5);

        //add sketches to sketch-list
        sketches = new ArrayList<EmbeddedSketch>();
        //sketches.add(CircleSketch);

        //sketches.add(EllipseSketch);
        //sketches.add(WoodCircleSketch);

        sketches.add(areaTest3);
        */

        //Sternanis SternanisSketch = new Sternanis(this, sketchAreas.get(0), this.resultsToShow);
        //sketches.add(SternanisSketch);

        //RoundSquare RoundSquareSketch = new RoundSquare(this, sketchAreas.get(0), this.resultsToShow);
        //sketches.add(RoundSquareSketch);

        EllipseCotton EllipseCottonSketch = new EllipseCotton(this, sketchAreas.get(0), this.resultsToShow);
        sketches.add(EllipseCottonSketch);

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
                resultCounter += nextDiceResults.size();
                //System.out.println("results ids until: " + lastDiceResultId);
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
            //System.out.println("frame: " + frameCount);
            //for (var dr : resultsToShow) System.out.println(dr.Id);
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
        /*
        for (var area : sketchAreas) {
            stroke(0, 0, 0);
            fill(0, 0, 0);
            rect(area.x - borderPx, area.y - borderPx, area.xw + 2 * borderPx, borderPx);
            rect(area.x - borderPx, area.y - borderPx, borderPx, area.yh + 2 * borderPx);
            rect(area.x - borderPx, area.yh, area.xw + 2 * borderPx, borderPx);
            rect(area.xw, area.y - borderPx , borderPx, area.yh + 2 * borderPx);
        }
        */

        info.beginDraw();
        info.fill(0);
        info.rect(0, 0, info.width, info.height);
        info.fill(255);
        //String recentResultsText = resultsToShow.stream().map(p -> Integer.toString(p.Result)).collect(Collectors.joining(" "));
        //info.text(recentResultsText, 10, 10);
        info.text("Results shown: " + resultCounter, 10, 20);
        info.text("Frame rate: " + frameRate, 10, 40);
        info.endDraw();
        image(info, screenW - info.width, screenH - info.height);
    }
}
