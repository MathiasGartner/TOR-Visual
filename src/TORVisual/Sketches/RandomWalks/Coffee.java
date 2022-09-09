package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Coffee extends RandomWalker {

    float dh, dw, sizemax, sizemin, sd, swmax, x4, y4, winkel, multiply, start;
    float sw;
    float swd;

    public Coffee(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Kaffee";
        nameLatin = "Coffea arabica";

        colorStart = sketch.color(227, 159, 123);
        colorEnd = sketch.color(200, 150, 123);

        x = startX;
        y = startY;
        x2 = (float) (x + (this.area.w / 100.0f * 1));
        y2 = y;
        x3 = (float) (x + (this.area.w / 100.0f * 0.5));
        y3 = (float) (y + (this.area.w / 100.0f * 0.5));
        x4 = (float) (x - (this.area.w / 100.0f * 0.5));
        y4 = y * 0.5f;
        w = this.area.w / 100.0f * 0.4f;
        h = this.area.w / 100.0f * 0.3f;
        sd = this.area.h / 100.0f * 0.04f; //size difference
        sizemax = this.area.w / 100.0f * 1f;
        sizemin = this.area.w / 100.0f * 0.2f;
        winkel = (float) 0.5f;
        multiply = 0f;
        start = 0;

        alpha = 20;
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.7f; //difference y
        // dh = this.area.w / 300;
        // dw = this.area.w / 300;
        //w=this.area.w/200;
        //wmax=this.area.w/100;
        //h=this.area.h/200;
        sw = this.area.w / 100.0f * 0.15f; //stroke weight
        swd = this.area.w / 100.0f * 0.01f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.4f; //stroke weight maximum
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;
            switch (r) {
                case 1:
                    moveX(-dx);
                    if (sw + swd < swmax) {
                        sw += swd; //stroke weight + stroke weight distance
                    }
                    break;
                case 2:
                    moveY(-dy);
                    if (sw > swd) {
                        sw -= swd;  //stroke weight - stroke weight distance
                    }
                    break;
                case 3:
                    moveY(dy);
                    if (cg > 0) {
                        cg -= 1;   //green - 1
                    }
                    if (h + sd < sizemax) {
                        h += sd; //+w difference
                    }
                    multiply = 2.5f;
                    start = (float) Math.PI;
                    break;
                case 4:
                    moveX(dx);
                    if (w + sd < sizemax) {
                        w += sd; //+w difference
                    }
                    start = (float) -(Math.PI);
                    break;
                case 5:
                    if (alpha <= 40) {
                        alpha += 1;
                    }
                    if (w > sizemin) {
                        w -= sd;  //halfmoon size - difference size
                    }
                    winkel = (float) 0f;
                    multiply = 1.5f;
                    break;
                case 6:
                    if (alpha >= 10) {
                        alpha -= 1;  //alpha -1
                    }
                    if (h > sizemin) {
                        h -= sd;
                    }
                    winkel = (float) 1.5f;
                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            this.canvas.stroke(c, alpha);
            this.canvas.noFill();
            float hEffective = h*1.4f;
            this.canvas.ellipse(x, y, w, hEffective);
            this.canvas.line(x+w/2.f, y, x+w/2.f, y+hEffective);
        }
    }
}

