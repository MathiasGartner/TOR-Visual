package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Corn extends RandomWalker {

    float dh, dw, sizemax, sd, swmax, xdistance, ydistance, distancemax, dxrotate, dyrotate;
    float sw;
    float swd;

    public Corn(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Gerstenähren";
        nameLatin = "Hordeum vulgare";

        x1 = startX;
        y1 = startY;

        colorStart = sketch.color(110, 86, 58);
        colorEnd = sketch.color(74, 59, 40);

        dx = this.area.w / 100.0f * 0.2f; //difference x
        dy = this.area.w / 100.0f * 0.4f; //difference y

        dxrotate = this.area.w / 100.0f * 0.02f;
        dyrotate = this.area.w / 100.0f * 0.01f;

        xdistance = this.area.w / 100.0f * 0.01f;
        ydistance = this.area.w / 100.0f * 0.01f;

        distancemax = this.area.w / 100.0f * 0.1f;

        x2 = x1 + xdistance;
        y2 = y1 + ydistance;

        alpha = 20;
        sw = this.area.w / 100.0f * 0.3f; //stroke weight
        swd = this.area.w / 100.0f * 0.02f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.6f; //stroke weight maximum

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    if (this.recentDiceResults.isEmpty() || this.recentDiceResults.get(0).Result < 4) {
                        if (x1 < x2) {
                            x1 += dxrotate;
                        }
                        else {
                            x1 -= dxrotate;
                        }
                    }
                    else {
                        if (y1 < y2) {
                            y1 += dyrotate;
                        }
                        else {
                            y1 -= dyrotate;
                        }
                    }
                    if (sw > swd) {
                        sw = sw - swd;
                    }
                    break;

                case 2:
                    if ((y1 - dy >= 0) && (y2 - dy >= 0)) {
                        y1 -= dy;
                        y2 -= dy;
                    }
                    break;


                case 3:

                    if ((y1 + dy <= area.h) && (y2 + dy <= area.h)) {
                        y1 += dy;
                        y2 += dy;
                    }
                    if (sw < swmax) {
                        sw = sw + swd;
                    }
                    break;


                case 4:
                    if (this.recentDiceResults.isEmpty() || this.recentDiceResults.get(0).Result < 4) {
                        if (Math.abs(x2 - x1) < distancemax - dxrotate && x1 + dxrotate <= area.w) {
                            if (x1 < x2) {
                                x1 -= dxrotate;
                            }
                            else {
                                x1 += dxrotate;
                            }
                        }
                    }
                    else {
                        if (Math.abs(y2 - y1) < distancemax - dyrotate && (y1 + dyrotate <= area.h)) {
                            if (y1 < y2) {
                                y1 -= dyrotate;
                            }
                            else {
                                y1 += dyrotate;
                            }
                        }
                    }
                    break;

                case 5:
                    if (x1 + dx <= area.w && x2 + dx <= area.w) {
                        x1 += dx;
                        x2 += dx;
                    }
                    break;


                case 6:
                    if ((x1 - dx >= 0) && (x2 - dx >= 0)) {
                        x1 -= dx;
                        x2 -= dx;
                    }
                    break;
            }
            this.canvas.strokeWeight(sw);
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.stroke(c, alpha);
            this.canvas.line(x1, y1, x2, y2);
        }
    }
}