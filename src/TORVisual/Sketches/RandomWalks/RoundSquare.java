package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class RoundSquare extends RandomWalker {

    public RoundSquare(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        cr = 163;
        cg = 77;
        cb = 59;
        dx = (float) 4;
        dy = (float) 4;
        ds = (float) 2;
        h = this.sketch.height / 100;
        w = this.sketch.width / 100;
        size = 10;

    }

    @Override
    public void draw() {
        float tl = 1;
        float tr = 1;
        float bl = 1;
        float br = 1;
        //this.recentDiceResults();
        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    if (x < area.w)
                        x += dx;

                    if (cg < 255)  //green + 1
                        cg += 1;
                    break;
                case 2:
                    if (y < area.h)
                        y += dy;

                    if (cg > 0)  //green -1
                        cg -= 1;

                    break;
                case 3:
                    if (x > 1 & x < area.w)
                        x -= dx;

                    //  if (cb > 0)  //blue -1
                    //      cb -= 1;
                    if (h < size)
                        h += ds;

                    break;
                case 4:
                    if (y > 1 & y < area.h)
                        y -= dy;

                    //   if (cb < 255)  //blue + 1
                    //cb += 1;

                    if (w < size)
                        w += ds;
                    break;

                case 5:

                    if (w > ds)
                        w -= ds;
                    //if (size > ds)
                    //    size -= ds;

                    if (alpha <= 100 & alpha >= 20)
                        alpha -= 1;
                    break;
                case 6:
                    //size += ds;
                    if (h > ds)
                        h -= ds;

                    if (alpha >= 20 & alpha <= 100)
                        alpha += 1;
                    break;
            }
            sketch.fill(cr / 3, cg / 3, cb / 3, alpha / 3);
            sketch.stroke(cr, cg, cb, alpha);
            sketch.ellipse(x, y, w, h);
            sketch.rect(x, y, w, h, tl, tr, br, bl);
        }
    }
}
