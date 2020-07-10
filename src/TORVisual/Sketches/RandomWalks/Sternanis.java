package TORVisual.Sketches.RandomWalks;

import TORVisual.SketchArea;
import processing.core.PApplet;
import processing.core.PShape;

public class Sternanis extends RandomWalker{


    public Sternanis(PApplet sketch, SketchArea area) {
        super(sketch, area);
        cr = 71;
        cg = 55;
        cb = 119;
        dx = (float) 2;
        dy = (float) 4;
        ds = (float) 2;

    }

    @Override
    public void draw() {

        int r = randInt();
        switch (r) {
            case 1:
                if (x1 < area.w)
                    x1 += ds;
                    x2 += ds;
                    x3 += ds;
                break;
            case 2:
                if (y2 < area.h)
                    y1 += dy;
                    y2 += dy;
                    y3 += dy;
                break;
            case 3:
                if (x1 > 1 & x1 < area.w)
                    x1 -= ds;
                     x2 -= ds;
                    x3 -= ds;
                break;
            case 4:
                if (y1 > 1 & y1 < area.h)
                    y1 -= dy;
                    y2 -= dy;
                    y3 -= dy;
                break;

            case 5:

                if (w > ds)

                    //sketch.rotate((float) (Math.PI/rx));

                break;
            case 6:
                //size += ds;
                if (h > ds)

                break;

        }
        sketch.fill(cr, cg, cb, alpha);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.triangle(x1, y1, x2, y2, x3,y3);
    }

}



