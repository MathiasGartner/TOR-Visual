package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Blumenknospen extends RandomWalker {

    float alphaMax, alphaMin, alphaChange;

    public Blumenknospen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Blumenknospen";
        nameLatin = "Leucadendron";

        // Define a color palette inspired by the Leucadendron image
        // A creamy, light center color
        colorStart = sketch.color(242, 230, 209);
        // A deep, brownish-red for the outer petals
        colorEnd = sketch.color(140, 52, 52);
        dColor = 0.02f;

        // Set a higher alpha for a more solid, layered appearance
        alpha = 20; // Startwert verringert
        alphaMax = 60; // Maxwert verringert für mehr Transparenz
        alphaMin = 10; // Minwert verringert
        alphaChange = 1.5f;

        // Movement steps (verkleinert für weniger große Schritte)
        dx = this.area.w / 100.0f * 0.8f;
        dy = this.area.h / 100.0f * 0.8f;

        // Initial size defines the spread of the triangle vertices (stark verkleinert)
        size = this.area.h / 100.0f * 0.7f;

        // Initialize triangle vertices around the starting point
        x1 = x - size;
        y1 = y + size;
        x2 = x + size;
        y2 = y + size;
        x3 = x;
        y3 = y - size;
    }

    @Override
    public void draw() {
        // Iterate through each random number result
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // The switch statement determines how each dice roll affects the drawing parameters
            switch (r) {
                case 1:
                    moveY(-dy);
                    if (colorPercent > 0) colorPercent -= dColor;
                    break;

                case 2:
                    moveX(-dx);
                    if (alpha > alphaMin) alpha -= alphaChange;
                    break;

                case 3:
                    // Distort the shape by moving vertices individually
                    x1 += sketch.random(-dx, dx);
                    y2 += sketch.random(-dy, dy);
                    break;

                case 4:
                    // Distort the shape further
                    x2 -= sketch.random(-dx, dx);
                    y3 -= sketch.random(-dy, dy);
                    break;

                case 5:
                    moveX(dx);
                    if (alpha < alphaMax) alpha += alphaChange;
                    break;

                case 6:
                    moveY(dy);
                    if (colorPercent < 1) colorPercent += dColor;
                    break;
            }

            // Gently pull the vertices towards the main (x,y) position
            // This keeps the shape together while allowing it to deform
            x1 = PApplet.lerp(x1, x - size, 0.2f);
            y1 = PApplet.lerp(y1, y + size, 0.2f);
            x2 = PApplet.lerp(x2, x + size, 0.2f);
            y2 = PApplet.lerp(y2, y + size, 0.2f);
            x3 = PApplet.lerp(x3, x, 0.2f);
            y3 = PApplet.lerp(y3, y - size, 0.2f);


            // --- Drawing Logic ---
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            // No stroke for a softer, more blended appearance
            this.canvas.noStroke();
            this.canvas.fill(sketch.red(c), sketch.green(c), sketch.blue(c), alpha);

            // Draw the deforming triangle
            this.canvas.triangle(x1, y1, x2, y2, x3, y3);
        }
    }
}

