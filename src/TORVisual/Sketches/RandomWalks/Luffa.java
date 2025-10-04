package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Luffa extends RandomWalker {
    float dh, dw, sizemax, sizemin, sd, swmax;
    float sw;
    float swd;

    public Luffa(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Luffa";
        nameLatin = "Luffa aegyptiaca";

        cr = 250; //rgb color value red
        cg = 246; //rgb color value green
        cb = 210;  //rgb color value blue
        alpha = 10;
        dx = this.area.w / 100.0f * 0.5f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y

        sw = this.area.w / 100.0f * 0.1f; //stroke weight
        swd = this.area.w / 100.0f * 0.04f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.5f; //stroke weight maximum
        size = this.area.h / 100.0f * 0.4f; //circle size
        sizemax = this.area.h / 100.0f * 1.1f; //circle size maximum
        sizemin = this.area.h / 100.0f * 0.1f;
        sd = this.area.h / 100.0f * 0.04f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);

                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    break;

                case 2:
                    if (alpha >= 5 & alpha <= 50)
                        alpha += 1;

                    if (size > sizemin & size < sizemax)
                        size -= sd;  //circle size - difference size
                    break;


                case 3:
                    if (alpha <= 50 & alpha >= 5)
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

                    break;
            }
            this.canvas.fill(cr, cg, cb, 0);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(cr, cg, cb, alpha);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
