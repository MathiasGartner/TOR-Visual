package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.util.ArrayList;


// Lavendel: draw purple, blue Ellipse with strokes, fill /3

public class Ellipse extends RandomWalker
{

    public Ellipse(PApplet sketch, SketchArea area,  ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        cr = 40;
        cg = 140;
        cb = 119;
        dx = (float) 4;
        dy = (float) 4;
        ds = (float) 2;
        w=3;
        h=3;
        size = 10;
        dColor = 0.05f;
    }

    @Override
    public void draw() {
        int r = Utils.randDiceResult();
        switch (r) {
            case 1:
                this.moveX(dx);

                if (colorPercent < 1) {
                    colorPercent += dColor;
                }
                break;
            case 2:
                this.moveY(dy);

                if (colorPercent > 0) {
                    colorPercent -= dColor;
                }
                break;
            case 3:
                this.moveX(-dx);

                if (h < size)
                    h += ds;
                break;
            case 4:
                this.moveY(-dy);

                if (w < size)
                    w += ds;
                break;
            case 5:
                if (w > ds) {
                    w -= ds;
                }
                if (alpha <= 100 & alpha >= 20) {
                    alpha -= 1;
                }
                break;
            case 6:
                if (h > ds) {
                    h -= ds;
                }
                if (alpha >= 20 & alpha <= 100) {
                    alpha += 1;
                }
                break;
        }
        var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
        sketch.fill(c, alpha / 4.0f);
        sketch.stroke(c, alpha);
        sketch.ellipse(x, y, w, h);
    }
}
