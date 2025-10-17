package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;
public class Lianenscheiben extends RandomWalker {
    // Variablen für Winkel, Strichstärke, Größe und Transparenz
    float angle_rad, sw, swd, swmax, size, sizemax, sizemin, sd, dangle;

    public Lianenscheiben(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Lianenscheiben";
        nameLatin = "Serjania";

        // NEU: Farben weiter abgedunkelt
        // Ein dunkleres, erdiges Braun als Startfarbe
        colorStart = sketch.color(130, 90, 40);
        // Ein sehr tiefes, fast schwarzes Braun als Endfarbe
        colorEnd = sketch.color(60, 40, 20);

        alpha = 15;

        dx = this.area.w / 100.0f * 0.7f;
        dy = this.area.w / 100.0f * 0.8f;
        angle_rad = 20;
        dangle = 0.4f;
        sw = this.area.w / 100.0f * 0.3f;
        swd = this.area.w / 100.0f * 0.04f;
        swmax = this.area.w / 100.0f * 1f;

        // NEU: Shapes weiter verkleinert
        size = this.area.h / 100.0f * 0.3f;     // Noch kleinere Startgröße
        sizemax = this.area.h / 100.0f * 0.6f;   // Noch kleinere Maximalgröße
        sizemin = this.area.h / 100.0f * 0.1f;   // Noch kleinere Minimalgröße
        sd = this.area.h / 100.0f * 0.02f;      // Kleinere Schrittweite der Größenänderung
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    if (sw > swd && sw < swmax)
                        sw -= swd;
                    break;

                case 2:
                    if (alpha <= 27)
                        alpha += 3;

                    if (size > sizemin && size < sizemax)
                        size -= sd;

                    angle_rad = angle_rad - dangle;
                    break;

                case 3:
                    if (alpha >= 8)
                        alpha -= 3;
                    break;

                case 4:
                    angle_rad = angle_rad + dangle;
                    break;

                case 5:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);

                    if (size + sd < sizemax)
                        size += sd;
                    break;

                case 6:
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);

                    if (sw + swd < swmax)
                        sw += swd;
                    break;
            }

            alpha = sketch.constrain(alpha, 5, 30);

            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            this.canvas.fill(c, alpha);
            this.canvas.strokeWeight(sw);
            this.canvas.stroke(c, alpha);
            this.canvas.circle(x, y, size);
        }
    }
}



