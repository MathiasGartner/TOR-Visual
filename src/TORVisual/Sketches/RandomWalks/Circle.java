package TORVisual.Sketches.RandomWalks;

import TORVisual.MainCanvas;
import TORVisual.SketchArea;
import processing.core.PApplet;

public class Circle extends RandomWalker{

    public Circle(PApplet sketch, SketchArea area)
    {
        super(sketch, area);

    }

    @Override
    public void draw() {
        int r = randInt();
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
        sketch.fill(cr, cg, cb, alpha);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.circle(x, y, size);
    }
}
