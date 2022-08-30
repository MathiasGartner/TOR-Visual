package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


// Lavendel: draw purple, blue Ellipse with strokes, fill /3

public class Ellipse extends RandomWalker {

    public Ellipse(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        cr = 40;
        cg = 140;
        cb = 119;
        dx = this.area.w / 100.0f * 0.9f; //difference x
        dy = this.area.w / 100.0f * 0.9f; //difference y
        ds = (float) 2;
        w = 3;
        h = 3;
        size = this.area.h / 100.0f * 2f; //circle size
        dColor = 0.05f;

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;
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
            this.canvas.fill(c, alpha / 4.0f);
            this.canvas.stroke(c, alpha);
            this.canvas.ellipse(x, y, w, h);
        }
    }
}
