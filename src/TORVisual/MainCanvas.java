package TORVisual;

import TORVisual.Data.DBManager;
import TORVisual.Data.DiceResult;
import TORVisual.Settings.SettingsVisual;
import TORVisual.Sketches.AreaTest;
import TORVisual.Sketches.PiMC;
import TORVisual.Sketches.RandomWalks.*;
import TORVisual.Utils.Utils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<EmbeddedSketch> sketchesFront;
    private ArrayList<EmbeddedSketch> sketchesCenter;
    private ArrayList<EmbeddedSketch> sketchesBack;
    private ArrayList<EmbeddedSketch> sketchesToShow;
    private ArrayList<EmbeddedSketch> sketchesAll;
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
                sketchAreas.add(new SketchArea(i * d, j * d, d, d));
            }
        }
        //sketchAreas.add(new SketchArea(screenH + 1, 0, screenW - screenH, screenH));
        //sketchAreas.add(new SketchArea(0, 0, 900, 900));

        //create sketches
        sketchesFront = new ArrayList<EmbeddedSketch>();
        sketchesCenter = new ArrayList<EmbeddedSketch>();
        sketchesBack = new ArrayList<EmbeddedSketch>();
        sketchesToShow = new ArrayList<EmbeddedSketch>();
        sketchesAll = new ArrayList<EmbeddedSketch>();

        /*for (int i = 0; i < 9; i++) {
            var sketch = new AreaTest(this, sketchAreas.get(i));
            sketch.setBackgroundColor(10 * i, 20 * i, 200 / (i + 1));
            sketches.add(sketch);
        }
        PiMC piMCSketch = new PiMC(this, sketchAreas.get(9), this.resultsToShow);
        sketches.add(piMCSketch);*/



        //Circle CircleSketch = new Circle(this, sketchAreas.get(0));

        /*
        SternanisSketch.setRecentDiceResultsCount(5);

        //add sketches to sketch-list
        sketches = new ArrayList<EmbeddedSketch>();
        //sketches.add(CircleSketch);

        //sketches.add(EllipseSketch);
        sketches.add(areaTest3);*/

        // FRONT

        Ellipse EllipseSketch = new Ellipse(this, sketchAreas.get(0), this.resultsToShow);
        sketchesFront.add(EllipseSketch);

        //Sterne funktionieren - jedoch noch nicht schön
        Sternanis SternanisSketch = new Sternanis(this, sketchAreas.get(1), this.resultsToShow);
        sketchesFront.add(SternanisSketch);

        //überlegen zu welchen es passen könnte, jedoch ist ein Fehler drinnen
        //RoundSquare RoundSquareSketch = new RoundSquare(this, sketchAreas.get(2), this.resultsToShow);
        //sketchesFront.add(RoundSquareSketch);

        //könnte wieder mehr wolkiger sein/heller
        //EllipseCotton EllipseCottonSketch = new EllipseCotton(this, sketchAreas.get(3), this.resultsToShow);
        //sketchesFront.add(EllipseCottonSketch);

        //optisch gut
        //Iceland_Moss Iceland_MossSketch = new Iceland_Moss(this, sketchAreas.get(4), this.resultsToShow);
        //sketchesFront.add(Iceland_MossSketch);

        //nicht fertig
        //WoodCircle WoodCircleSketch = new WoodCircle(this, sketchAreas.get(5), this.resultsToShow);
        //sketchesFront.add(WoodCircleSketch);

        //Cinnamon CinnamonSketch = new Cinnamon(this, sketchAreas.get(6), this.resultsToShow);
        //sketchesFront.add(CinnamonSketch);

        //Fehler klebt nur links am Rand,optisch noch nicht ganz ideal
        //Pepper PepperSketch = new Pepper(this, sketchAreas.get(7), this.resultsToShow);
        //sketchesFront.add(PepperSketch);

        //->irgendwo bei 4000 ein Fehler
        //Chili ChiliSketch = new Chili(this, sketchAreas.get(8), this.resultsToShow);
        //sketchesFront.add(ChiliSketch);

        //CENTER

        //Polstermoos PolstermoosSketch = new Polstermoos(this, sketchAreas.get(0), this.resultsToShow);
        //sketchesCenter.add(PolstermoosSketch);

        //test testSketch = new test(this, sketchAreas.get(1), this.resultsToShow);
        //sketchesCenter.add(testSketch);

        Lavendel LavendelSketch = new Lavendel(this, sketchAreas.get(2), this.resultsToShow);
        sketchesCenter.add(LavendelSketch);

        //BACK

        sketchesAll.addAll(sketchesFront);
        sketchesAll.addAll(sketchesCenter);
        sketchesAll.addAll(sketchesBack);

        sketchesToShow = sketchesFront;

        for (var sketch : sketchesToShow) {
            stroke(sketch.backgroundColor);
            fill(sketch.backgroundColor);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
        }
    }

    double resultsPerFrame;
    double lastShownResultIndex;
    int dummyId = 0;

    public void draw() {
        if (counter % SettingsVisual.LoadDataEveryNthFrame == 0) {
            //DBManager db = new DBManager();
            try {
                nextDiceResults = new ArrayList<DiceResult>();
                //nextDiceResults = db.getDiceResultAboveId(lastDiceResultId);
                for (int i = 0; i < 100; i++) {
                    var dr = new DiceResult();
                    dr.Id = dummyId;
                    dr.Result = Utils.randDiceResult();
                    dr.ClientId = Utils.randClientId();
                    dr.Material = "";
                    dr.Time = new Date();
                    nextDiceResults.add(dr);
                    dummyId++;
                }
                resultsPerFrame = (double)nextDiceResults.size() / SettingsVisual.LoadDataEveryNthFrame;
                lastShownResultIndex = 0;
                if (nextDiceResults.size() > 0) {
                    lastDiceResultId = nextDiceResults.get(nextDiceResults.size() - 1).Id;
                }
                resultCounter += nextDiceResults.size();
                //System.out.println("results ids until: " + lastDiceResultId);
            } catch (Exception e) {
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
        for (var sketch : sketchesAll) {
            sketch.addNewDiceResults(resultsToShow);
            sketch.canvas.beginDraw();
            sketch.draw();
            sketch.canvas.endDraw();
        }
        //TODO: switch sketches to show
        if (frameCount > 1000) {
            sketchesToShow = sketchesCenter;
        }
        for (var sketch : sketchesToShow) {
            image(sketch.canvas, sketch.area.x, sketch.area.y);
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
