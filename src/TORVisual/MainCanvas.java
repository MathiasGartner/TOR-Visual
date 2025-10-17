package TORVisual;

import TORVisual.Database.DBManager;
import TORVisual.Database.DiceResult;
import TORVisual.Settings.SettingsVisual;
import TORVisual.Sketches.PiMC;
import TORVisual.Sketches.RandomWalks.*;
import TORVisual.Utils.Utils;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

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
    float sketchesGroupIconX;
    float sketchesGroupIconY;

    PiMC piMCSketch;
    PImage piIcon;
    PImage jkuIcon;
    private ArrayList<EmbeddedSketch> sketchesFront;
    private ArrayList<EmbeddedSketch> sketchesCenter;
    private ArrayList<EmbeddedSketch> sketchesBack;
    private ArrayList<EmbeddedSketch> sketchesToShow;
    private ArrayList<EmbeddedSketch> sketchesToShowNext;
    private ArrayList<EmbeddedSketch> sketchesAll;
    private ArrayList<ArrayList<EmbeddedSketch>> sketchesGroups;
    private ArrayList<SketchArea> sketchAreas;
    PFont font;
    private int borderPx;

    private MainCanvas() {
        this.resultsToShow = new ArrayList<DiceResult>();
        this.resultsToShow.add(new DiceResult());
        this.borderPx = 0;
    }

    public MainCanvas(int displayId) {
        this();
        this.displayId = displayId;
        this.fullScreen = true;
    }

    public MainCanvas(int displayId, int w, int h) {
        this();
        this.displayId = displayId;
        this.screenW = w;
        this.screenH = h;
        this.fullScreen = false;
    }

    public void settings() {
        //var renderer = JAVA2D;
        var renderer = P2D;
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
        int infoWidth = 260;
        int infoHeight = 50 + 50;
        info = createGraphics(infoWidth, infoHeight);
        info.noStroke();
        font = createFont("Ailerons-Regular.ttf", 20);
        int boxW, boxH, borderLeft, borderTop, borderBottom, borderRight, marginX, marginY;

        borderLeft = 120;
        borderRight = 100;
        borderTop = 100;
        borderBottom = 100;
        marginX = 40;
        marginY = 80;
        boxW = (((1920 / 8) * 5) - borderLeft - borderRight - (2 * marginX)) / 3;
        boxH = (1080 - borderTop - borderBottom - (2 * marginY)) / 3;

        //define sketch areas
        sketchAreas = new ArrayList<SketchArea>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sketchAreas.add(new SketchArea(borderLeft + i * (marginX + boxW), borderTop + j * (marginY + boxH), boxW, boxH));
            }
        }

        int piMCX = borderLeft + 3 * (marginX + boxW) + marginX;
        int piMCY = borderTop;
        sketchAreas.add(new SketchArea(piMCX, piMCY, screenW - piMCX, screenH - piMCY));

        //create Pi sketch
        piMCSketch = new PiMC(this, sketchAreas.get(9), this.resultsToShow);
        piMCSketch.setRecentDiceResultsCount(27);
        piMCSketch.canvas.textFont(font);
        piIcon = this.loadImage("images/pi_circle_cube_illu-01.png"); //TODO: create two separate images so we can tint them

        //JKU Logo
        jkuIcon = this.loadImage("images/jkuwhite_en.png");

        //create Random Walker sketches
        sketchesFront = new ArrayList<EmbeddedSketch>();
        sketchesCenter = new ArrayList<EmbeddedSketch>();
        sketchesBack = new ArrayList<EmbeddedSketch>();
        sketchesToShow = new ArrayList<EmbeddedSketch>();
        sketchesAll = new ArrayList<EmbeddedSketch>();
        sketchesGroups = new ArrayList<ArrayList<EmbeddedSketch>>();

        sketchesGroups.add(sketchesFront);
        sketchesGroups.add(sketchesCenter);
        sketchesGroups.add(sketchesBack);

        try {
            DBManager db = new DBManager();
            var clients = db.getClients();
            for (var c : clients) {
                int areaInGroup = (c.Position - 1) % 9;
                int groupId = (c.Position - 1) / 9;
                var group = sketchesGroups.get(groupId);
                group.add(WalkerFactory.create(c.Material, this, sketchAreas.get(areaInGroup), this.resultsToShow));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (var sg : sketchesGroups) {
            sketchesAll.addAll(sg);
        }
        sketchesAll.add(piMCSketch);

        sketchesToShow = sketchesBack;

        for (var sketch : sketchesToShow) {
            stroke(sketch.backgroundColor);
            fill(sketch.backgroundColor);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
        }

        sketchesGroupIconX = 20.0f;
        sketchesGroupIconY = 40.0f;
        sketchesGroupIcon = new ArrayList<PGraphics>();
        int sketchesGroupIconSizeX = 60;
        float iW = 5;
        float iS = 1.5f;
        float iB = 2;
        float sUnit = sketchesGroupIconSizeX / (3 * iW + 2 * iS + 2 * iB);
        int sketchesGroupIconSizeY = (int) ((3 * iW + 3 * iS) * sUnit);
        float iWidth = iW * sUnit;
        float iSpacing = iS * sUnit;
        float iBorder = iB * sUnit;
        float sw = 1.0f;
        float sw2 = sw / 2.0f;
        for (int i = 0; i < 3; i++) {
            PGraphics icon = createGraphics(sketchesGroupIconSizeX, sketchesGroupIconSizeY);
            icon.beginDraw();
            icon.background(Utils.Colors.BACKGROUND);
            icon.fill(Utils.Colors.GRAY);
            icon.noStroke();
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    icon.square(iBorder + j * (iSpacing + iWidth), k * (iSpacing + iWidth) + iSpacing / 2.0f, iWidth);
                }
            }
            icon.noFill();
            icon.stroke(Utils.Colors.WHITE);
            icon.strokeWeight(sw);
            icon.rect(sw2, sketchesGroupIconSizeY / 3.0f * i + sw2, sketchesGroupIconSizeX - sw, iSpacing + iWidth - sw);
            icon.endDraw();
            sketchesGroupIcon.add(icon);
        }

        oldTimeStamp = this.minute();
        sketchGroupIndexToShow = 2;
        sketchGroupIndexToShowNext = 2;
        sketchesToShowNext = null;
        inSketchSwitchMode = false;

        //Load initial data for Pi sketch (from previous events)
        if (SettingsVisual.UseAllDiceResultEventSourcesForPi) {
            var eventSources = new ArrayList<String>();
            eventSources.add("ArsElectronica2020");
            eventSources.add("CyberArts2021");
            eventSources.add("Test2022");
            eventSources.add("Kapelica2022");
            DBManager db = new DBManager();
            for (var src : eventSources) {
                try {
                    ArrayList<DiceResult> previousResults = db.getDiceResultByEventSource(src);
                    piMCSketch.generateInitialPositions(previousResults);
                    resultCounter += previousResults.size();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    boolean useDataFromDB = false;
    boolean enableSwitching = true;
    boolean inSketchSwitchMode;
    float switchPercent;
    int oldTimeStamp;
    int sketchGroupIndexToShow;
    int sketchGroupIndexToShowNext;
    double resultsPerFrame;
    double lastShownResultIndex;
    int dummyId = 0;

    public void draw() {
        if (counter % SettingsVisual.LoadDataEveryNthFrame == 0) {
            DBManager db = new DBManager();
            try {
                if (useDataFromDB) {
                    nextDiceResults = db.getDiceResultAboveId(lastDiceResultId);
                }
                else {
                    nextDiceResults = new ArrayList<DiceResult>();
                    for (int i = 0; i < 100; i++) {
                        var dr = new DiceResult();
                        dr.Id = dummyId;
                        dr.Result = Utils.randDiceResult();
                        dr.ClientId = Utils.randClientId();
                        dr.Material = "";
                        dr.Time = new Date();
                        dr.UserGenerated = Utils.randClientId() == 15;
                        nextDiceResults.add(dr);
                        dummyId++;
                    }
                }
                resultsPerFrame = (double) nextDiceResults.size() / SettingsVisual.LoadDataEveryNthFrame;
                lastShownResultIndex = 0;
                if (nextDiceResults.size() > 0) {
                    lastDiceResultId = nextDiceResults.get(nextDiceResults.size() - 1).Id;
                }
                resultCounter += nextDiceResults.size();
                //System.out.println("results ids until: " + lastDiceResultId);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            counter = 0;
        }
        counter++;
        //TODO: make sure also the last result in nextDiceResults is shown
        double showResultsUpToIndex = lastShownResultIndex + resultsPerFrame;
        if ((counter + 1) % SettingsVisual.LoadDataEveryNthFrame == 0) {
            showResultsUpToIndex = nextDiceResults.size();
        }
        if (showResultsUpToIndex > nextDiceResults.size()) {
            showResultsUpToIndex = nextDiceResults.size();
        }
        resultsToShow.clear();
        for (int i = (int) lastShownResultIndex; i < (int) showResultsUpToIndex; i++) {
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

        //draw sketches
        for (var sketch : sketchesAll) {
            sketch.addNewDiceResults(resultsToShow);
            sketch.canvas.beginDraw();
            sketch.draw();
            sketch.canvas.endDraw();
        }

        //draw PiMC
        this.noTint();
        image(piMCSketch.canvas, piMCSketch.area.x, piMCSketch.area.y);
        this.image(piIcon, this.width - 90, sketchesGroupIconY);

        //draw JKU logo
        int jkuX = 1308;
        int jkuY = 625;
        int jkuFactor = 10;
        this.tint(Utils.Colors.WHITE);
        this.image(jkuIcon, this.width - 180, 950, jkuX / jkuFactor, jkuY / jkuFactor);
        this.noTint();

        //TODO: switch sketches to show
        if (enableSwitching) {
            if (oldTimeStamp != minute()) {
                oldTimeStamp = minute();
                sketchGroupIndexToShowNext = (sketchGroupIndexToShowNext + 1) % 3;
                sketchesToShowNext = sketchesGroups.get(sketchGroupIndexToShowNext);
                inSketchSwitchMode = true;
                switchPercent = 0;
            }
        }
        if (inSketchSwitchMode) {
            tint(255, 255 * (1.0f - switchPercent));
            fill(Utils.Colors.WHITE, 230 * (1.0f - switchPercent));
        }
        else {
            tint(255, 255);
            fill(Utils.Colors.WHITE, 230);
        }
        for (var sketch : sketchesToShow) {
            this.displayRandomWalkSketch((RandomWalker) sketch);
        }
        image(sketchesGroupIcon.get(sketchGroupIndexToShow), sketchesGroupIconX, sketchesGroupIconY);
        if (inSketchSwitchMode) {
            tint(255, 255 * switchPercent);
            fill(Utils.Colors.WHITE, 230 * switchPercent);
            for (var sketch : sketchesToShowNext) {
                this.displayRandomWalkSketch((RandomWalker) sketch);
            }
            image(sketchesGroupIcon.get(sketchGroupIndexToShowNext), sketchesGroupIconX, sketchesGroupIconY);
            switchPercent += 0.008;
        }
        if (inSketchSwitchMode && switchPercent >= 1.0f) {
            inSketchSwitchMode = false;
            sketchesToShow = sketchesToShowNext;
            sketchGroupIndexToShow = sketchGroupIndexToShowNext;
        }
        noTint();
        fill(Utils.Colors.WHITE);

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
        //info.fill(0);
        info.fill(Utils.Colors.BACKGROUND);
        info.rect(0, 0, info.width, info.height);
        info.fill(Utils.Colors.WHITE);
        //String recentResultsText = resultsToShow.stream().map(p -> Integer.toString(p.Result)).collect(Collectors.joining(" "));
        //info.text(recentResultsText, 10, 10);
        info.textAlign(CENTER);
        info.textFont(font);
        info.textSize(30);
        info.text("#" + resultCounter, info.width / 2, 25);
        info.textSize(14);
        info.text("generated numbers", info.width / 2, 55);
        //info.textSize(16);
        //info.text("Ars Electronica 2020: 50342", 10, 40);
        //info.text("Cyber Arts 2021: " + resultCounter, 10, 65);
        //info.text("Frame rate: " + frameRate, 10, 40);
        info.endDraw();
        int infoPosX = 497;
        int infoPosY = 260;
        image(info, screenW - infoPosX, screenH - infoPosY);
    }

    public void displayRandomWalkSketch(RandomWalker rw) {
        image(rw.canvas, rw.area.x, rw.area.y);
        textAlign(CENTER);
        textFont(font);
        text(rw.nameLatin, rw.area.x + rw.area.w / 2.0f, rw.area.yh + 25);
    }
}
