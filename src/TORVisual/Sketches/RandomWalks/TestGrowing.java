package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TestGrowing extends RandomWalker{

    private float size = 1.0f;

    private float mass = 0.0f;
    private float centerX = 0.0f;
    private float centerY = 0.0f;

    private int maxLeft = 0;
    private int maxRight = 0;
    private int maxTop = 0;
    private int maxBottom = 0;

    public int maxLeftDisplay = 0;
    public int maxRightDisplay = 0;
    public int maxTopDisplay = 0;
    public int maxBottomDisplay = 0;
    public int xExtent;
    public int yExtent;
    public int xExtentDisplay;
    public int yExtentDisplay;
    public int maxExtentDisplay;

    public TestGrowing(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "TestGrowing";
        nameLatin = "TestGrowing";

        x = startX;
        y = startY;
        dx = 3.0f; //difference x
        dy = 3.0f; //difference y

        size = 10.0f; //circle size

        this.centerX = this.area.w / 2;
        this.centerY = this.area.h / 2;

        maxLeft = (int)startX;
        maxRight = (int)startX;
        maxTop = (int)startY;
        maxBottom = (int)startY;
    }

    public PImage getPart() {
        PImage part = this.canvas.get(this.maxLeftDisplay, this.maxTopDisplay, this.xExtent, this.yExtent);
        return part;
    }

    @Override
    public void draw() {
        this.mass += 1;

        int r = ThreadLocalRandom.current().nextInt(0, 4);
        if (r == 0) {
            x = Math.min(this.area.w, x + dx);
            if (x > maxRight) {
                maxRight = (int)x;
            }
        }
        else if (r == 1) {
            x = Math.max(0, x - dx);
            if (x < maxLeft) {
                maxLeft = (int)x;
            }
        }
        else if (r == 2) {
            y = Math.min(this.area.h, y + dy);
            if (y > maxBottom) {
                maxBottom = (int)y;
            }
        }
        else if (r == 3) {
            y = Math.max(0, y - dy);
            if (y < maxTop) {
                maxTop = (int)y;
            }
        }
        var c = sketch.color(40, 139, 119, 50);
        this.canvas.fill(c);
        this.canvas.noStroke();
        //this.canvas.circle(this.area.w/2, this.area.h/2, size/1000.0f);
        this.canvas.circle(x, y, size);

        xExtent = maxRight - maxLeft;
        yExtent = maxBottom - maxTop;
        maxLeftDisplay = Math.max(0, maxLeft - 10 - (int)(0.01 * xExtent));
        maxRightDisplay = Math.min(this.area.w, maxRight + 10 + (int)(0.01 * xExtent));
        maxTopDisplay = Math.max(0, maxTop - 10 - (int)(0.01 * yExtent));
        maxBottomDisplay = Math.min(this.area.h, maxBottom + 10 + (int)(0.01 * yExtent));
        xExtentDisplay = maxRightDisplay - maxLeftDisplay;
        yExtentDisplay = maxBottomDisplay - maxTopDisplay;
        maxExtentDisplay = Math.max(xExtentDisplay, yExtentDisplay);
        if (xExtentDisplay < maxExtentDisplay) {
            int diff = maxExtentDisplay - xExtentDisplay;
            int diff_2 = diff / 2;
            if (maxLeftDisplay < diff_2) {
                maxRightDisplay += diff_2 - maxLeftDisplay;
                maxLeftDisplay = 0;
            }
            if (this.area.w - maxRightDisplay < diff_2) {
                maxLeftDisplay -= diff_2 - (this.area.w - maxRightDisplay);
                maxRightDisplay = this.area.w;
            }
        }
        else if (yExtentDisplay < maxExtentDisplay) {
            int diff = maxExtentDisplay - yExtentDisplay;
            int diff_2 = diff / 2;
            if (maxTopDisplay < diff_2) {
                maxBottomDisplay += diff_2 - maxTopDisplay;
                maxTopDisplay = 0;
            }
            if (this.area.w - maxBottomDisplay < diff_2) {
                maxTopDisplay -= diff_2 - (this.area.h - maxBottomDisplay);
                maxBottomDisplay = this.area.h;
            }
        }
    }
}