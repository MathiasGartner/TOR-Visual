package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Lianenscheiben extends RandomWalker {
    float dh, dw, sizemax, sizemin, sd, swmax;
    float sw;
    float swd;

    public Lianenscheiben(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Lianenscheiben";
        nameLatin = "Serjania";

        x1 = startX;
        y1 = startY;

        colorStart = sketch.color(255, 151, 33);
        colorEnd = sketch.color(240, 77, 40);

        alpha = 10;
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.5f; //difference y

        sw = this.area.w / 100.0f * 0.02f; //stroke weight
        swd = this.area.w / 100.0f * 0.02f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.4f; //stroke weight maximum
        size = this.area.h / 100.0f * 0.2f; //circle size
        sizemax = this.area.h / 100.0f * 1.2f; //circle size maximum
        sizemin = this.area.h / 100.0f * 0.1f;
        sd = this.area.h / 100.0f * 0.03f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:

                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    if (sw > swd & sw < swmax) {
                        sw -= swd;  //stroke weight - stroke weight distance
                    }
                    break;

                case 2:
                    moveX(-dx);
                    if (size > sizemin & size < sizemax) {
                        size -= sd;  //circle size - difference size
                    }
                    break;


                case 3:

                    break;

                case 4:
                    moveY(-dy);
                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    break;

                case 5:


                    if (size + sd < sizemax) {
                        size += sd; //+size difference
                    }
                    break;


                case 6:
                    moveY(dy);

                    moveX(dx);
                    if (sw + swd < swmax) {
                        sw += swd; //stroke weight + stroke weight distance
                    }
                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(c, alpha * 0.5f);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha * 0.7f);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
