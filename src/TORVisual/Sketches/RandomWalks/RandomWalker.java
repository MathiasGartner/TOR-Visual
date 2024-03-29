package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class RandomWalker extends EmbeddedSketch {

    public String name;
    public String nameLatin;

    protected ArrayList<DiceResult> resultsToShow;

    float startX;
    float startY;
    float x;
    float y;
    float x1, y1, x2, y2, x3, y3; // x and y for eg. lines, polygon
    float size;
    float dx; //x change
    float dy; //y change
    float ds; //size change
    float w, h;

    int cr, cg, cb; //color (red, green, blue)
    int alpha = 30;
    int colorStart;
    int colorEnd;
    float colorPercent; //for lerping (interpolating) between colorStart and colorEnd
    float dColor; // colorPercent changes by dc

    float border = 10;
    float returnArea = 30;
    float minX;
    float maxX;
    float minY;
    float maxY;

    boolean xMinOut = false;
    boolean xMaxOut = false;
    boolean yMinOut = false;
    boolean yMaxOut = false;


    public RandomWalker(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area);

        name = "unknown";
        nameLatin = "unknown";

        this.resultsToShow = resultsToShow;

        startX = this.area.w / 2.0f;
        startY = this.area.h / 2.0f;
        size = 5;
        x = startX;
        y = startY;

        x1 = startX;
        y1 = startY;
        x2 = startX + 2;
        y2 = startY + 2;
        x3 = startX + 4;
        y3 = startY + 8;

        minX = border;
        maxX = this.area.w - border;
        minY = border;
        maxY = this.area.h - border;

        colorStart = sketch.color(40, 139, 119);
        colorEnd = sketch.color(90, 60, 119);
        colorPercent = 0.0f;
        dColor = 0.01f;
    }

    protected void moveXPeriodic(float steps) {
        x += steps;
        if (x > this.area.w) {
            x = x - this.area.w;
        }
        if (x < 0) {
            x = this.area.w - x;
        }
    }

    protected void moveYPeriodic(float steps) {
        y += steps;
        if (y > this.area.h) {
            y = y - this.area.h;
        }
        if (y < 0) {
            y = this.area.h - y;
        }
    }

    protected void moveXWithReturnArea(float steps) {
        if (steps > 0) { //move right
            if (x < maxX && !xMaxOut) {
                x += steps;
            }
            else {
                xMaxOut = true;
            }
            if (xMinOut && x > returnArea) {
                xMinOut = false;
            }
        }
        else if (steps < 0) { //move left
            if (x > minX && !xMinOut) {
                x += steps;
            }
            else {
                xMinOut = true;
            }
            if (xMaxOut && x < this.area.w - returnArea) {
                xMaxOut = false;
            }
        }
    }

    protected void moveYWithReturnArea(float steps) {
        if (steps > 0) { //move down
            if (y < maxY && !yMaxOut) {
                y += steps;
            }
            else {
                yMaxOut = true;
            }
            if (yMinOut && y > returnArea) {
                yMinOut = false;
            }
        }
        else if (steps < 0) { //move up
            if (y > minY && !yMinOut) {
                y += steps;
            }
            else {
                yMinOut = true;
            }
            if (yMaxOut && y < this.area.h - returnArea) {
                yMaxOut = false;
            }
        }
    }

    protected void moveX(float steps) {
        //this.moveXPeriodic(steps);
        this.moveXWithReturnArea(steps);
    }

    protected void moveY(float steps) {
        //this.moveYPeriodic(steps);
        this.moveYWithReturnArea(steps);
    }


    @Override
    public void draw() {
    }
}
