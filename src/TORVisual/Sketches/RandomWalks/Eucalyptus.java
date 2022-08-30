package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Eucalyptus extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;

    public Eucalyptus(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Eucalyptus";
        nameLatin = "Eucalyptus ovata";


        colorStart = sketch.color(60, 97, 75);
        colorEnd = sketch.color(110, 170, 140);

        alpha = 10;
        sw = this.area.w / 100.0f * 0.05f; //stroke weight
        swd = this.area.w / 100.0f * 0.01f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.3f; //stroke weight maximum
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        angle_rad = 5;
        //dangle=0.1f;
        dangle = 0.3f;
        size = this.area.h / 100.0f * 1f; //circle size
        sd = this.area.h / 100.0f * 0.1f; //size difference

        w = this.canvas.width / 100 * 0.5f;
        h = this.canvas.height / 100 * 0.4f;
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    break;

                case 2:
                    if (alpha < 30)
                        alpha += 1;

                    if (size > sizemin & size < sizemax)
                        size -= sd;  //circle size - difference size

                    angle_rad = angle_rad + dangle;

                    break;


                case 3:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);

                    break;

                case 4:
                    if (alpha > 0)
                        alpha -= 1;  //alpha -1
                    break;

                case 5:
                    moveY(+(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:

                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            // this.canvas.fill(c, alpha/4);
            this.canvas.noFill();
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            this.canvas.ellipse(x, y, w, h);
        }
    }
}