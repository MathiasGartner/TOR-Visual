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
    PGraphics resultTable;
    PGraphics piResult;
    ArrayList<PImage> resultImages;
    PImage icon;
    PImage piPoint;
    PImage piGlyph;
    PImage approx;

    Random r = new Random();

    public PiMC(PApplet sketch, SketchArea area, ArrayList<DiceResult> newResults) {
        super(sketch, area);

        this.resultsNeededForOneCoordinate = SettingsVisual.ResultsNeededForOneCoordinate;
        this.resultsNeededForNewPosition = resultsNeededForOneCoordinate * 2;
        this.boxSizeTotal = Integer.parseInt(new String(new char[resultsNeededForOneCoordinate]).replace('\0', '5'), 6); //base 6 numbers are from 0 to 5
        this.boxSize = this.boxSizeTotal / 2;
        this.points = new ArrayList<PiMCPoint>();
        this.pointsToProcess = new ArrayList<PiMCPoint>();
        this.newResults = newResults;
        this.resultsToProcess = new ArrayList<Integer>();

        this.pi = 1.0;
        this.variance = 1.0;
        this.error = 1.0;
        this.total = 0;
        this.inCircle = 0;

        float pB = 3.0f;
        float pS = 6.0f;
        float pUnit = this.area.w / (pB + pS + pB);
        this.squareL = (int)(pS * pUnit);
        this.squareL2 = this.squareL / 2.0f;
        this.graphX = pB * pUnit;
        this.graphY = (pB - 1) * pUnit;

        this.piGraph = sketch.createGraphics(this.squareL, this.squareL);
        piGraph.beginDraw();
        this.piGraph.noFill();
        float sw = 0.7f;
        float sw2 = sw / 2.0f;
        piGraph.strokeWeight(sw);
        piGraph.stroke(Utils.Colors.GRAY);
        piGraph.rect(0 + sw2, 0 + sw2, this.squareL - sw, this.squareL - sw);
        piGraph.stroke(Utils.Colors.WHITE);
        piGraph.circle(this.squareL2, this.squareL2, this.squareL - sw);
        piGraph.noStroke();
        piGraph.endDraw();

        piPoints = sketch.createGraphics(this.squareL, this.squareL);
        piPoints.noStroke();

        float rBLeft = 0.0f;
        float rBRight = 10.0f;
        float rW = 1.0f;
        float rUnit = this.area.w / (rBLeft + rW + rBRight);
        this.resultTableW = (int)(rW * rUnit);
        this.resultTableH = (int)(this.area.h);
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
            resultImages.add(sketch.loadImage("images/result-" + i + ".png"));
        }

        icon = sketch.loadImage("images/pi_circle_cube_illu-01.png");
        canvas.beginDraw();
        canvas.image(icon, this.area.w - 90, 40);
        canvas.endDraw();

        piPoint = sketch.loadImage("images/point_gradient.png");
        piPoint.resize(4, 0);

        piGlyph = sketch.loadImage("images/pi.png");
        approx = sketch.loadImage("images/approx.png");
    }

    @Override
    public void draw() {
        this.clear();
        this.canvas.fill(255);

        this.pointsToProcess.clear();
        for (var result : this.newResults) {
            this.resultsToProcess.add(result.Result);
            this.createPosition();
        }

        //this.canvas.text("pi = " + this.pi, 5, 15);
        //this.canvas.text("var = " + this.variance, 5, 30);
        //this.canvas.text("err = " + this.error, 5, 45);

        piPoints.beginDraw();
        piPoints.noStroke();
        for (var p : this.pointsToProcess) {
            if (p.inCircle) {
                piPoints.tint(Utils.Colors.GREEN);
            }
            piPoints.image(piPoint, p.drawAt.x, p.drawAt.y);
            piPoints.noTint();
        }
        piPoints.endDraw();
        this.canvas.image(piPoints, this.graphX, this.graphY);

        this.canvas.image(piGraph, this.graphX, this.graphY);

        resultTable.beginDraw();
        resultTable.background(Utils.Colors.BACKGROUND);
        int row = 0;
        int col = 0;
        int maxCol = 1;
        int s = resultTable.width;
        int spacing = 5;
        for(var r : recentDiceResults) {
            if (r.UserGenerated) {
                resultTable.tint(Utils.Colors.GREEN);
            }
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
        resultTextX = this.area.w / 2.0f - 50.0f;
        resultTextY = this.graphY + this.squareL;
        this.canvas.image(piGlyph, resultTextX, resultTextY);
        this.canvas.image(approx, resultTextX + 25, resultTextY);
        this.canvas.textSize(30);
        this.canvas.text(new DecimalFormat("0.0000").format(pi) + " +/- " + new DecimalFormat("#.0000").format(error), resultTextX + 50, resultTextY);
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
            this.variance = pi * (4.0 - pi);
            this.error = Math.sqrt(this.variance) / Math.sqrt(this.total);
            for (int i = 0; i < this.resultsNeededForNewPosition; i++) {
                resultsToProcess.remove(0);
            }
        }
    }
}
