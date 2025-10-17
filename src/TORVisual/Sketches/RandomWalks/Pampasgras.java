package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Pampasgras extends RandomWalker {

    float hmax, hmin, wmax;

    public Pampasgras(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Pampasgras";
        nameLatin = "Cortaderia selloana";

        // Unbenutzte Farbvariablen cr, cg, cb wurden ignoriert
        dx = this.area.w / 100.0f * 0.6f;
        dy = this.area.w / 100.0f * 0.3f;
        ds = this.area.w / 100.0f * 0.02f;
        h = this.area.h / 100 * 0.5f;
        hmax = this.area.h / 100 * 1.5f;
        w = this.area.w / 100 * 0.1f;
        wmax = this.area.w / 100 * 0.5f;
        dColor = 0.05f;

        // GEÄNDERT: Farben auf Lilatöne angepasst
        colorStart = sketch.color(120, 90, 160);  // Dunkleres Lila
        colorEnd = sketch.color(200, 180, 220); // Helles Lavendel
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;
            switch (r) {
                case 1:
                    this.moveY(dy);

                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    break;
                case 2:
                    this.moveX(dx);


                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    break;
                case 3:
                    this.moveY(-dy);

                    if (h < hmax)
                        h += ds;
                    break;
                case 4:

                    this.moveX(-dx);

                    if (w < wmax)
                        w += ds;
                    break;
                case 5:
                    if (w > ds && w < wmax) {
                        w -= ds;
                    }
                    if (alpha <= 100 & alpha >= 20) {
                        alpha -= 1;
                    }
                    break;
                case 6:
                    if (h > ds && h < hmax) {
                        h -= ds;
                    }
                    if (alpha >= 10 & alpha <= 50) {
                        alpha += 1;
                    }
                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(c, alpha * 0.2f);
            this.canvas.stroke(c, alpha * 0.3f);
            this.canvas.ellipse(x, y, w, h);
        }
    }
}

