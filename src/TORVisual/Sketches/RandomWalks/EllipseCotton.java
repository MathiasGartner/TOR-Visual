package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.util.ArrayList;

public class EllipseCotton extends RandomWalker {

    float hmax, wmax;

    public EllipseCotton(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Baumwolle";
        nameLatin = "Gossypium";
        cr = 255;
        cg = 255;
        cb = 255;
        alpha = 20;
        dx = this.canvas.width / 100 * 0.4f;
        dy = this.canvas.width / 100 * 0.4f;
        ds = this.canvas.width / 100 * 0.1f;
        w = this.canvas.width / 100 * 0.5f;
        h = this.canvas.height / 100 * 0.4f;
        hmax = this.canvas.height / 100 * 1.0f;
        wmax = this.canvas.width / 100 * 1.0f;

    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveX(-dx);
                    break;
                case 2:
                    moveY(-dy);
                    break;
                case 3:
                    moveX(dx);

                    if (h < hmax)
                        h += ds;

                    break;
                case 4:
                    moveY(dy);

                    if (w < wmax)
                        w += ds;
                    break;

                case 5:

                    if (w > ds)
                        w -= ds;

                    if (alpha >= 10.0f)
                        alpha -= 0.5f;
                    break;
                case 6:
                    //size += ds;
                    if (h > ds)
                        h -= ds;

                    if (alpha <= 40.0f)
                        alpha += 0.5f;
                    break;
            }
        }
        if (alpha > 40) {
            alpha = 40;
        }
        this.canvas.fill(cr, cg, cb, alpha);
        this.canvas.stroke(cr, cg, cb, alpha);
        this.canvas.ellipse(x, y, w, h);
    }
}

