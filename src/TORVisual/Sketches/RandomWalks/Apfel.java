package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Apfel extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    public Apfel(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Apfelscheiben";
        nameLatin = "Malus domestica";

        colorStart = sketch.color(138, 224, 94);
        colorEnd = sketch.color(124, 158, 49);

        x = startX;
        y = startY;
        alpha = 20;
        dx = this.area.w / 100.0f * 0.3f; //difference x
        dy = this.area.w / 100.0f * 0.3f; //difference y
        sw = this.area.w / 100.0f * 0.02f; //stroke weight
        swd = this.area.w / 100.0f * 0.01f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.4f; //stroke weight maximum
        size = this.area.h / 100.0f * 0.2f; //circle size
        sizemax = this.area.h / 100.0f * 0.7f; //circle size maximum
        sd = this.area.h / 100.0f * 0.03f; //size difference
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    if (alpha >= 20 & alpha <= 40)
                        alpha += 1;

                    if (size > sd & size < sizemax)
                        size -= sd;  //circle size - difference size

                    moveY(dy);


                    break;

                case 2:
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance
                    break;


                case 3:
                    moveX(dx);
                    break;

                case 4:
                    if (alpha <= 40 & alpha >= 20)
                        alpha -= 1;  //alpha -1
                    break;

                case 5:
                    moveX(-dx);

                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:
                    moveY(-dy);


                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    break;

            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            //sketch.fill(cr, cg, cb, alpha);
            this.canvas.fill(c, 0);
            //     sketch.stroke(32,79,17,31);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha / 2);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
