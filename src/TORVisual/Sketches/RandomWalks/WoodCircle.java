package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class WoodCircle extends RandomWalker{

float sw, swd;
    public WoodCircle(PApplet sketch, SketchArea area,  ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        x = startX;
        y = startY;
        cr = 126;
        cg = 133;
        cb = 83;
        dx = (float) 4;
        dy = (float) 4;
        ds = (float) 2;
        sw = (float) 0.5;
        swd = (float) 0.1;
        size = 10;
    }

    @Override
    public void draw() {
        int r = randInt();
        switch (r) {
            case 1:
                if (x < area.w)
                    x += dx;

                //if (sw < 4)
                //    sw += swd;

                if (cg < 255)  //green + 1
                    cg += 1;
                break;
            case 2:
                if (y < area.h)
                    y += dy;

                //if (sw > 0)
                //    sw -= swd;

                if (cg > 134)  //green -1
                    cg -= 1;

                break;
            case 3:
                if (x > 1 & x < area.w)
                    x -= dx;

                //  if (cb > 0)  //blue -1
                //      cb -= 1;
                if (h < 20)
                    h += ds;

                break;
            case 4:
                if (y > 1 & y < area.h)
                    y -= dy;

                //   if (cb < 255)  //blue + 1
                //cb += 1;

                if (w < 20)
                    w += ds;
                break;

            case 5:

                if (w > ds)
                    w -= ds;
                if (size > ds)
                    size -= ds;

                if (alpha <= 100 & alpha >= 20)
                    alpha -= 1;
                break;
            case 6:
                if (size<20)
                    size += ds;
                if (h > ds)
                    h -= ds;

                if (alpha >= 20 & alpha <= 100)
                    alpha += 1;
                break;
        }
        //sketch.fill(cr, cg, cb, alpha);
        sketch.fill(cr, cg, cb, 0);
        sketch.strokeWeight(sw);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.circle(x, y, size);
    }
}
