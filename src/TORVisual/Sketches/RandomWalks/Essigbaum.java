package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Essigbaum extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    public Essigbaum(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Essigbaum";
        nameLatin = "Rhus typhina";

        colorStart = sketch.color(98, 102, 26);
        colorEnd = sketch.color(196, 201, 101);

        x = startX;
        y = startY;
        cr = 98; //rgb color value red
        cg = 102; //rgb color value green
        cb = 26;  //rgb color value blue
        alpha = 20;
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        sw = this.area.w / 100.0f * 0.05f; //stroke weight
        swd = this.area.w / 100.0f * 0.02f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.8f; //stroke weight maximum
        size = this.area.h / 100.0f * 0.4f; //circle size
        sizemax = this.area.h / 100.0f * 1.1f; //circle size maximum
        sd = this.area.h / 100.0f * 0.03f; //size difference
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);

                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    // if (cg > 134)
                    //     cg -= 1;  //green -1
                    break;

                case 2:
                    if (alpha >= 20 & alpha <= 40)
                        alpha += 1;

                    if (size > sd & size < sizemax)
                        size -= sd;  //circle size - difference size
                    break;


                case 3:
                    if (alpha <= 40 & alpha >= 20)
                        alpha -= 1;  //alpha -1
                    break;

                case 4:
                    moveY(-dy);
                    break;

                case 5:
                    moveX(-dx);

                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:

                    moveX(dx);

                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    // if (cg < 255)
                    //     cg += 1;   //green + 1
                    break;

            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            //sketch.fill(cr, cg, cb, alpha);
            this.canvas.fill(c, 0);
            //     sketch.stroke(32,79,17,31);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
