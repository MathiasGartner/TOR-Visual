package TORVisual.Sketches;

import TORVisual.Database.DiceResult;
import TORVisual.Database.PiMCPoint;
import TORVisual.EmbeddedSketch;
import TORVisual.Settings.SettingsVisual;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PiMC extends EmbeddedSketch {

    class PointHighlight {
        float size;
        float x;
        float y;
        float alpha;
        int color;
        int counter;

        PointHighlight(float x, float y) {
            this.size = 10;
            this.x = x;
            this.y = y;
            this.alpha = 230;
            this.counter = 0;
        }

        void updateAndDraw(PGraphics pg) {
            pg.fill(this.color, this.alpha);
            pg.ellipse(this.x, this.y, this.size, this.size);
            this.counter += 1;
            if (this.counter % 3 == 0) {
                this.alpha = Math.max(this.alpha - 1, 0);
            }
        }

        boolean isFaded() {
            return this.alpha == 0;
        }
    }

    private int counter;
    private int resultsNeededForOneCoordinate;
    private int resultsNeededForNewPosition;
    private int boxSize;
    private int boxSizeTotal;
    protected ArrayList<PiMCPoint> points;
    protected ArrayList<PiMCPoint> pointsToProcess;
    protected ArrayList<PointHighlight> pointHighlights;
    protected ArrayList<DiceResult> newResults;
    protected ArrayList<Integer> resultsToProcess;

    private int squareL;
    private float squareL2;
    private float graphX;
    private float graphY;

    private int resultTableW;
    private int resultTableH;
    private float resultTableX;
    private float resultTableY;

    int c1;
    int c2;
    int c3;

    int c1In;
    int c2In;
    int c3In;

    private double pi;
    private double variance;
    private double error;
    private int total;
    private int inCircle;

    PGraphics piGraph;
    PGraphics piPoints;
    PGraphics piPointHighlights;
    PGraphics resultTable;
    ArrayList<PImage> resultImages;
    PImage piPoint;
    PImage piGlyph;
    PImage approxGlyph;
    PImage plusMinusGlyph;

    int piTextSize = 30;

    Random r = new Random();

    boolean initialPositionsHandled = false;

    public PiMC(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);

        this.counter = 0;

        this.resultsNeededForOneCoordinate = SettingsVisual.ResultsNeededForOneCoordinate;
        this.resultsNeededForNewPosition = resultsNeededForOneCoordinate * 2;
        this.boxSizeTotal = Integer.parseInt(new String(new char[resultsNeededForOneCoordinate]).replace('\0', '5'), 6); //base 6 numbers are from 0 to 5
        this.boxSize = this.boxSizeTotal / 2;
        this.points = new ArrayList<PiMCPoint>();
        this.pointsToProcess = new ArrayList<PiMCPoint>();
        this.pointHighlights = new ArrayList<PointHighlight>();
        this.newResults = newResults;
        this.resultsToProcess = new ArrayList<Integer>();

        this.pi = 1.0;
        this.variance = 1.0;
        this.error = 1.0;
        this.total = 0;
        this.inCircle = 0;

        float pB = 2.0f;
        float pS = 6.0f;
        float pUnit = this.area.w / (pB + pS + pB);
        this.squareL = (int) (pS * pUnit);
        this.squareL2 = this.squareL / 2.0f;
        this.graphX = pB * pUnit;
        this.graphY = (pB - 1) * pUnit;

        this.piGraph = sketch.createGraphics(this.squareL, this.squareL);
        piGraph.beginDraw();
        piGraph.clear();
        piGraph.noFill();
        float sw = 3.0f;
        float sw2 = sw / 2.0f;

        piGraph.strokeWeight(sw * 4.0f);
        piGraph.stroke(Utils.Colors.BACKGROUND);
        piGraph.circle(this.squareL2, this.squareL2, this.squareL - sw);

        piGraph.strokeWeight(sw * 4.0f);
        piGraph.stroke(Utils.Colors.BACKGROUND);
        piGraph.rect(0 + sw2, 0 + sw2, this.squareL - sw, this.squareL - sw);

        piGraph.strokeWeight(sw);
        piGraph.stroke(Utils.Colors.GREEN);
        piGraph.circle(this.squareL2, this.squareL2, this.squareL - sw);

        piGraph.strokeWeight(sw);
        piGraph.stroke(Utils.Colors.WHITE);
        piGraph.rect(0 + sw2, 0 + sw2, this.squareL - sw, this.squareL - sw);

        piGraph.noStroke();
        piGraph.endDraw();

        piPoints = sketch.createGraphics(this.squareL, this.squareL);
        piPoints.noStroke();

        piPointHighlights = sketch.createGraphics(this.squareL, this.squareL);
        piPointHighlights.beginDraw();
        piPointHighlights.clear();
        piPointHighlights.endDraw();

        float rBLeft = 1.0f;
        float rBRight = 24.0f;
        float rW = 1.0f;
        float rUnit = this.area.w / (rBLeft + rW + rBRight);
        this.resultTableW = (int) (rW * rUnit);
        this.resultTableH = (int) (this.area.h);
        this.resultTableX = rBLeft * rUnit;
        this.resultTableY = 0;

        this.resultTable = sketch.createGraphics(resultTableW, resultTableH);

        this.setBackgroundColor(Utils.Colors.BACKGROUND);

        this.c1 = sketch.color(sketch.red(Utils.Colors.WHITE), sketch.green(Utils.Colors.WHITE), sketch.blue(Utils.Colors.WHITE), 255);
        this.c2 = sketch.color(sketch.red(Utils.Colors.WHITE), sketch.green(Utils.Colors.WHITE), sketch.blue(Utils.Colors.WHITE), 180);
        this.c3 = sketch.color(sketch.red(Utils.Colors.WHITE), sketch.green(Utils.Colors.WHITE), sketch.blue(Utils.Colors.WHITE), 60);

        this.c1In = sketch.color(sketch.red(Utils.Colors.GREEN), sketch.green(Utils.Colors.GREEN), sketch.blue(Utils.Colors.GREEN), 255);
        this.c2In = sketch.color(sketch.red(Utils.Colors.GREEN), sketch.green(Utils.Colors.GREEN), sketch.blue(Utils.Colors.GREEN), 180);
        this.c3In = sketch.color(sketch.red(Utils.Colors.GREEN), sketch.green(Utils.Colors.GREEN), sketch.blue(Utils.Colors.GREEN), 60);

        r.setSeed(12345);

        resultImages = new ArrayList<PImage>();
        for (int i = 1; i <= 6; i++) {
            resultImages.add(sketch.loadImage("images/white_v0/result-" + i + " white.png"));
        }

        piPoint = sketch.loadImage("images/point_gradient.png");
        piPoint.resize(10, 0);

        piGlyph = sketch.loadImage("images/pi.png");
        piGlyph.resize(20, 0);
        approxGlyph = sketch.loadImage("images/approx.png");
        approxGlyph.resize(20, 0);
        plusMinusGlyph = sketch.loadImage("images/plusMinus.png");
        plusMinusGlyph.resize(16, 0);

    }

    public void generateInitialPositions(ArrayList<DiceResult> initialResults) {
        for (var result : initialResults) {
            this.resultsToProcess.add(ThreadLocalRandom.current().nextInt(1, 6 + 1));
            //this.resultsToProcess.add(result.Result);
            this.createPosition();
        }
        this.initialPositionsHandled = false;
    }

    @Override
    public void draw() {
        this.clear();
        this.canvas.fill(255);

        if (this.initialPositionsHandled) {
            this.pointsToProcess.clear();
        }
        else {
            this.initialPositionsHandled = true;
        }
        for (var result : this.newResults) {
            this.resultsToProcess.add(result.Result);
            this.createPosition();
        }

        //this.canvas.text("pi = " + this.pi, 5, 15);
        //this.canvas.text("var = " + this.variance, 5, 30);
        //this.canvas.text("err = " + this.error, 5, 45);

        piPoints.beginDraw();
        piPoints.noStroke();

        // fade last frame
        counter += 1;
        if (counter == 1) {
            piPoints.fill(Utils.Colors.BACKGROUND, 1);
            piPoints.rect(0, 0, piPoints.width, piPoints.height);
            counter = 0;
        }

        for (var p : this.pointsToProcess) {
            int color = p.inCircle ? Utils.Colors.GREEN : Utils.Colors.WHITE;
            piPoints.tint(color);
            piPoints.image(piPoint, p.drawAt.x - (float)(piPoint.pixelWidth / 2.0), (float)(p.drawAt.y - piPoint.pixelHeight / 2.0));
            piPoints.noTint();
        }
        piPoints.noTint();
        piPoints.endDraw();

        // draw point highlights
        piPointHighlights.beginDraw();
        piPointHighlights.clear();
        piPointHighlights.noStroke();
        for (int i = pointHighlights.size() - 1; i >= 0; i--) {
            PointHighlight h = pointHighlights.get(i);
            h.updateAndDraw(piPointHighlights);
            if (h.isFaded()) {
                pointHighlights.remove(i);
            }
        }
        piPointHighlights.endDraw();

        this.canvas.image(piPoints, this.graphX, this.graphY);
        this.canvas.image(piPointHighlights, this.graphX, this.graphY);

        this.canvas.image(piGraph, this.graphX, this.graphY);

        resultTable.beginDraw();
        resultTable.background(Utils.Colors.BACKGROUND);
        int row = 0;
        int col = 0;
        int maxCol = 1;
        int s = resultTable.width;
        int spacing = 5;
        for (var r : recentDiceResults) {
            resultTable.tint(r.UserGenerated ? Utils.Colors.GREEN : Utils.Colors.WHITE);
            resultTable.image(resultImages.get(r.Result - 1), col * (s + spacing), row * (s + spacing), s, s);
            resultTable.noTint();
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
        resultTable.endDraw();
        this.canvas.image(resultTable, this.resultTableX, this.resultTableY);

        float resultTextX;
        float resultTextY;
        resultTextX = this.area.w / 2.0f - 170.0f;
        resultTextY = this.graphY + this.squareL + 90;
        this.canvas.fill(Utils.Colors.WHITE);
        this.canvas.tint(Utils.Colors.WHITE);
        this.canvas.image(piGlyph, resultTextX, resultTextY - 20);
        this.canvas.image(approxGlyph, resultTextX + 40, resultTextY - 15);
        this.canvas.textSize(piTextSize);
        this.canvas.text(new DecimalFormat("0.0000").format(pi), resultTextX + 80, resultTextY);
        this.canvas.image(plusMinusGlyph, resultTextX + 200, resultTextY - 22);
        this.canvas.text(new DecimalFormat("0.0000").format(error), resultTextX + 230, resultTextY);
        this.canvas.noFill();
        this.canvas.noTint();
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

            PointHighlight h = new PointHighlight(p.drawAt.x, p.drawAt.y);
            this.total++;
            if (p.inCircle) {
                this.inCircle++;
                h.color = Utils.Colors.GREEN;
            }
            else {
                h.color = h.color = Utils.Colors.WHITE;
            }
            this.pointHighlights.add(h);

            this.pi = 4.0 * this.inCircle / (double) this.total;
            this.variance = pi * (4.0 - pi);
            this.error = Math.sqrt(this.variance) / Math.sqrt(this.total) * 1.0;
            for (int i = 0; i < this.resultsNeededForNewPosition; i++) {
                resultsToProcess.remove(0);
            }
        }
    }
}
