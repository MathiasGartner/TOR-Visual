package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Samtgras extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, hmax, wmax;

    public Samtgras(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Samtgras";
        nameLatin = "Lagurus ovatus";

        colorStart = sketch.color(179, 147, 116);
        //colorEnd = sketch.color(74, 38, 5);

        alpha = 30;

        sw = this.area.w / 100.0f * 0.2f; //stroke weight


        angle_rad = 5;

        size = this.area.h / 100.0f * 1f; //circle size
        sd = this.area.h / 100.0f * 0.1f; //size difference


        dx = this.canvas.width / 100 * 0.7f;
        dy = this.canvas.width / 100 * 0.5f;
        ds = this.canvas.width / 100 * 0.2f;
        w = this.canvas.width / 100 * 0.1f;
        h = this.canvas.height / 100 * 1.5f;
        hmax = 2.0f;
        wmax = 0.5f;

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    if (alpha >= 5)
                        alpha -= 1;  //alpha -1
                    break;

                case 2:
                    if (w > sd)
                        w -= sd;

                    if (angle_rad > 1) {
                        angle_rad = angle_rad - 1;
                    }
                    if (h < hmax)
                        h += ds;
                    moveY((float) Math.cos(angle_rad));
                    break;


                case 3:

                    if (alpha <= 70)
                        alpha += 1;

                    if (angle_rad < 360) {
                        angle_rad = angle_rad + 1;
                    }
                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    break;

                case 4:
                    moveX(-(float) (Math.sin(angle_rad)));


                    if (h > ds)
                        h -= ds;

                    break;

                case 5:
                    moveX((float) (Math.sin(angle_rad)));
                    moveY(-(float) Math.cos(angle_rad));
                    if (w > ds)
                        w -= ds;

                    break;


                case 6:


                    if (w < wmax)
                        w += ds;

                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    break;
            }

            //var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(colorStart, alpha);
            this.canvas.noStroke();
            this.canvas.ellipse(x, y, w, h);
        }
    }
}