package TORVisual;

import TORVisual.Data.DiceResult;
import TORVisual.Settings.SettingsVisual;
import TORVisual.Sketches.PiMC;
import TORVisual.Sketches.RandomWalks.*;
import TORVisual.Utils.Utils;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.Date;

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
    ArrayList<PGraphics> sketchesGroupIcon;

    PiMC piMCSketch;
    private ArrayList<EmbeddedSketch> sketchesFront;
    private ArrayList<EmbeddedSketch> sketchesCenter;
    private ArrayList<EmbeddedSketch> sketchesBack;
    private ArrayList<EmbeddedSketch> sketchesToShow;
    private ArrayList<EmbeddedSketch> sketchesToShowNext;
    private ArrayList<EmbeddedSketch> sketchesAll;
    private ArrayList<ArrayList<EmbeddedSketch>> sketchesGroups;
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

        int boxW, boxH, borderLeft, borderTop, borderBottom, borderRight, marginX,marginY;

        borderLeft=120;
        borderRight=100;
        borderTop=100;
        borderBottom=100;
        marginX=40;
        marginY=80;
        boxW=(((1920/8)*5) - borderLeft - borderRight - (2*marginX))/3;
        boxH=(1080 - borderTop - borderBottom - (2*marginY))/3;


        //define sketch areas
        sketchAreas = new ArrayList<SketchArea>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sketchAreas.add(new SketchArea(borderLeft+i*(marginX+boxW), borderTop+j*(marginY+boxH), boxW, boxH));
            }
        }

        sketchAreas.add(new SketchArea(screenH + 1, 0, screenW - screenH, screenH));
        sketchAreas.add(new SketchArea(0, 0, 900, 900));

        //create Pi sketch
        piMCSketch = new PiMC(this, sketchAreas.get(9), this.resultsToShow);

        //create Random Walker sketches
        sketchesFront = new ArrayList<EmbeddedSketch>();
        sketchesCenter = new ArrayList<EmbeddedSketch>();
        sketchesBack = new ArrayList<EmbeddedSketch>();
        sketchesToShow = new ArrayList<EmbeddedSketch>();
        sketchesAll = new ArrayList<EmbeddedSketch>();
        sketchesGroups = new ArrayList<ArrayList<EmbeddedSketch>>();

        /*for (int i = 0; i < 9; i++) {
            var sketch = new AreaTest(this, sketchAreas.get(i));
            sketch.setBackgroundColor(10 * i, 20 * i, 200 / (i + 1));
            sketches.add(sketch);
        }*/




        /*
        SternanisSketch.setRecentDiceResultsCount(5);

        //add sketches to sketch-list
        sketches = new ArrayList<EmbeddedSketch>();
        //sketches.add(CircleSketch);

        //sketches.add(EllipseSketch);
        sketches.add(areaTest3);*/




        // FRONT ----------------------

        //1
        Orange  OrangeSketch = new Orange(this, sketchAreas.get(0), this.resultsToShow);
        sketchesFront.add(OrangeSketch);

        //2
        Kork KorkSketch = new Kork(this, sketchAreas.get(1),this.resultsToShow);
        sketchesFront.add(KorkSketch);

        //3
        Polstermoos PolstermoosSketch = new Polstermoos(this, sketchAreas.get(2), this.resultsToShow);
        sketchesFront.add(PolstermoosSketch);

        //4
        Palmenblatt PalmenblattSketch = new Palmenblatt(this, sketchAreas.get(3), this.resultsToShow);
        sketchesFront.add(PalmenblattSketch);

        //5
        Pepper PepperSketch = new Pepper(this, sketchAreas.get(4), this.resultsToShow);
        sketchesFront.add(PepperSketch);

        //6
        AcaciaBohnen AcaciaBohnenSketch = new AcaciaBohnen(this, sketchAreas.get(5), this.resultsToShow);
        sketchesFront.add(AcaciaBohnenSketch);

        //7
        Luffa LuffaSketch = new Luffa(this, sketchAreas.get(6), this.resultsToShow);
        sketchesFront.add(LuffaSketch);

        //8
        Platanen PlatanenSketch = new Platanen(this, sketchAreas.get(7), this.resultsToShow);
        sketchesFront.add(PlatanenSketch);

        //9
        Pampasgras PampasgrasSketch = new Pampasgras(this, sketchAreas.get(8), this.resultsToShow);
        sketchesFront.add(PampasgrasSketch);





        //CENTER ----------------------

        //1
        Strandflieder  StrandfliederSketch = new Strandflieder(this, sketchAreas.get(0), this.resultsToShow);
        sketchesCenter.add(StrandfliederSketch);

        //2
        Schlitzahorn  SchlitzahornSketch = new Schlitzahorn(this, sketchAreas.get(1), this.resultsToShow);
        sketchesCenter.add(SchlitzahornSketch);

        //3
        Palmringe  PalmingeSketch = new Palmringe(this, sketchAreas.get(2), this.resultsToShow);
        sketchesCenter.add(PalmingeSketch);

        //4
        EllipseCotton EllipseCottonSketch = new EllipseCotton(this, sketchAreas.get(3), this.resultsToShow);
        sketchesCenter.add(EllipseCottonSketch);

        //5
        Airfarn AirfarnSketch = new Airfarn(this, sketchAreas.get(4), this.resultsToShow);
        sketchesCenter.add(AirfarnSketch);

        //6
        Samtgras SamtgrasSketch = new Samtgras(this, sketchAreas.get(5), this.resultsToShow);
        sketchesCenter.add(SamtgrasSketch);

        //7
        Wachtelbohne WachtelbohneSketch = new Wachtelbohne(this, sketchAreas.get(6), this.resultsToShow);
        sketchesCenter.add(WachtelbohneSketch);

        //8 Essigbaum
        WoodCircle WoodCircleSketch = new WoodCircle(this, sketchAreas.get(7), this.resultsToShow);
        sketchesCenter.add(WoodCircleSketch);

        //9
        Chili ChiliSketch = new Chili(this, sketchAreas.get(8), this.resultsToShow);
        sketchesCenter.add(ChiliSketch);



        //BACK ----------------------

        //1
        Corn CornSketch = new Corn(this, sketchAreas.get(0), this.resultsToShow);
        sketchesBack.add(CornSketch);

        //2
        Cinnamon CinnamonSketch = new Cinnamon(this, sketchAreas.get(1), this.resultsToShow);
        sketchesBack.add(CinnamonSketch);

        //3
        Kirschkern KirschkernSketch = new Kirschkern(this, sketchAreas.get(2), this.resultsToShow);
        sketchesBack.add(KirschkernSketch);

        //4
        Baumschwamm BaumschwammSketch = new Baumschwamm(this, sketchAreas.get(3), this.resultsToShow);
        sketchesBack.add(BaumschwammSketch);

        //5
        Linsen LinsenSketch = new Linsen(this, sketchAreas.get(4), this.resultsToShow);
        sketchesBack.add(LinsenSketch);

        //6
        Apfel ApfelSketch = new Apfel(this, sketchAreas.get(5), this.resultsToShow);
        sketchesBack.add(ApfelSketch);

        //7
        Eucalyptus EucalyptusSketch = new Eucalyptus(this, sketchAreas.get(6), this.resultsToShow);
        sketchesBack.add(EucalyptusSketch);

        //8
        Lavendel LavendelSketch = new Lavendel(this, sketchAreas.get(7), this.resultsToShow);
        sketchesBack.add(LavendelSketch);

        //9
        Sternanis SternanisSketch = new Sternanis(this, sketchAreas.get(8), this.resultsToShow);
        sketchesBack.add(SternanisSketch);



        //---------------------
        // BACKUP CUBE
        //Limetten LimettenSketch = new Ellipse(this, sketchAreas.get(0), this.resultsToShow);
        //sketchesBack.add(LimettenSketch);

        //---------------------
        // BACKUP CUBE
        //Iceland_Moss Iceland_MossSketch = new Iceland_Moss(this, sketchAreas.get(4), this.resultsToShow);
        //sketchesFront.add(Iceland_MossSketch);

        //---------------------
        // BACKUP CUBE
        //Coffee CoffeeSketch = new Coffee(this, sketchAreas.get(4), this.resultsToShow);
        //sketchesFront.add(CoffeeSketch);




        //-----------------------------
        //Ellipse EllipseSketch = new Ellipse(this, sketchAreas.get(0), this.resultsToShow);
        //sketchesBack.add(EllipseSketch);

        //überlegen zu welchen es passen könnte, jedoch ist ein Fehler drinnen
        //RoundSquare RoundSquareSketch = new RoundSquare(this, sketchAreas.get(2), this.resultsToShow);
        //sketchesFront.add(RoundSquareSketch);

        //test testSketch = new test(this, sketchAreas.get(1), this.resultsToShow);
        //sketchesCenter.add(testSketch);
        //---------------------




        sketchesGroups.add(sketchesFront);
        sketchesGroups.add(sketchesCenter);
        sketchesGroups.add(sketchesBack);
        for (var sg : sketchesGroups) {
            sketchesAll.addAll(sg);
        }

        sketchesToShow = sketchesBack;

        for (var sketch : sketchesToShow) {
            stroke(sketch.backgroundColor);
            fill(sketch.backgroundColor);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
        }

        sketchesGroupIcon = new ArrayList<PGraphics>();
        int sketchesGroupIconSizeX = 100;
        float sUnit = sketchesGroupIconSizeX / 23.0f;
        int sketchesGroupIconSizeY = (int)(18 * sUnit);
        float s = 5 * sUnit;
        float iMargin = 1 * sUnit;
        float iBorder = 3 * sUnit;
        for (int i = 0; i < 3; i++) {
            PGraphics icon = createGraphics(sketchesGroupIconSizeX, sketchesGroupIconSizeY);
            icon.beginDraw();
            icon.background(Utils.Colors.BACKGROUND);
            icon.fill(Utils.Colors.GRAY);
            icon.noStroke();
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    icon.square(iBorder + j * (iMargin + s), k * (iMargin + s) + iMargin / 2.0f, s);
                }
            }
            icon.noFill();
            icon.stroke(Utils.Colors.WHITE);
            float sw = 2.0f;
            float sw2 = sw / 2.0f;
            icon.strokeWeight(sw);
            icon.rect(sw2, sketchesGroupIconSizeY / 3.0f * i + sw2, sketchesGroupIconSizeX - sw, iMargin + s - sw);
            icon.endDraw();
            sketchesGroupIcon.add(icon);
        }

        oldTimeStamp = this.minute();
        sketchGroupIndexToShow = 0;
        sketchGroupIndexToShowNext = 0;
        sketchesToShowNext = null;
        inSketchSwitchMode = false;
    }

    boolean enableSwitching = true;
    boolean inSketchSwitchMode;
    float alphaSketchSwitchMode;
    int oldTimeStamp;
    int sketchGroupIndexToShow;
    int sketchGroupIndexToShowNext;
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

        this.background(Utils.Colors.BACKGROUND);

        //draw PiMC
        piMCSketch.canvas.beginDraw();
        piMCSketch.draw();
        piMCSketch.canvas.endDraw();
        image(piMCSketch.canvas, piMCSketch.area.x, piMCSketch.area.y);

        //draw sketches
        for (var sketch : sketchesAll) {
            sketch.addNewDiceResults(resultsToShow);
            sketch.canvas.beginDraw();
            sketch.draw();
            sketch.canvas.endDraw();
        }

        //TODO: switch sketches to show
        if (enableSwitching) {
            if (oldTimeStamp != minute()) {
                oldTimeStamp = minute();
                sketchGroupIndexToShowNext = (sketchGroupIndexToShowNext + 1) % 3;
                sketchesToShowNext = sketchesGroups.get(sketchGroupIndexToShowNext);
                inSketchSwitchMode = true;
                alphaSketchSwitchMode = 0;
            }
        }
        if (inSketchSwitchMode) {
            tint(255, 255 - alphaSketchSwitchMode);
        }
        for (var sketch : sketchesToShow) {
            this.displayRandomWalkSketch((RandomWalker)sketch);
        }
        image(sketchesGroupIcon.get(sketchGroupIndexToShow), 20, 20);
        if (inSketchSwitchMode) {
            tint(255, alphaSketchSwitchMode);
            for (var sketch: sketchesToShowNext) {
                this.displayRandomWalkSketch((RandomWalker)sketch);
            }
            image(sketchesGroupIcon.get(sketchGroupIndexToShowNext), 20, 20);
            alphaSketchSwitchMode += 0.4;
        }
        if (inSketchSwitchMode && alphaSketchSwitchMode >= 255) {
            inSketchSwitchMode = false;
            sketchesToShow = sketchesToShowNext;
            sketchGroupIndexToShow = sketchGroupIndexToShowNext;
        }
        noTint();

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

    public void displayRandomWalkSketch(RandomWalker rw) {
        image(rw.canvas, rw.area.x, rw.area.y);
        fill(255);
        textAlign(CENTER);
        text(rw.name, rw.area.x + rw.area.w / 2.0f, rw.area.yh + 10);
    }
}
