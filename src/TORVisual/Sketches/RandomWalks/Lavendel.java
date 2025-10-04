package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


// Lavendel: draw purple, blue Ellipse with strokes, fill /3

public class Lavendel extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle, hmax, hmin;

    public Lavendel(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Lavendel";
        nameLatin = "Lavandula";

        colorStart = sketch.color(71, 55, 119);
        colorEnd = sketch.color(63, 52, 94);

        alpha = 10;
        sw = this.area.w / 100.0f * 0.1f; //stroke weight
        swd = this.area.w / 100.0f * 0.05f; //stroke weight difference
        swmax = this.area.w / 100.0f * 1f; //stroke weight maximum
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        w = this.canvas.width / 100 * 0.2f;
        h = this.canvas.height / 100 * 0.6f;
        hmax = this.canvas.height / 100 * 1.1f;
        hmin = this.canvas.height / 100 * 0.1f;
        angle_rad = 5;
        dangle = 0.2f;
        //dangle=1;
        sw = this.area.w / 100.0f * 0.2f; //stroke weight
        swd = this.area.w / 100.0f * 0.05f; //stroke weight difference
        sd = this.area.h / 100.0f * 0.1f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    break;

                case 2:
                    if (alpha < 30)
                        alpha += 1;

                    if (size > sizemin & size < sizemax)
                        size -= sd;  //circle size - difference size

                    angle_rad = angle_rad - dangle;

                    break;


                case 3:
                    if (h < hmax)
                        h += sd;

                    if (alpha > 0)
                        alpha -= 1;  //alpha -1

                    angle_rad = angle_rad + dangle;
                    break;

                case 4:
                    if (w < hmax)
                        w += sd;
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    break;

                case 5:
                    if (w > hmin)
                        w -= sd;
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(+(float) Math.cos(angle_rad) * dx);
                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:
                    if (h > hmin)
                        h -= sd;
                    moveY(+(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            this.canvas.fill(c, alpha / 3);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha / 2);
            this.canvas.ellipse(x, y, w, h);
        }
    }
}
