package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.util.ArrayList;

public class Islandmoos extends RandomWalker {

    float sw, swd, swmax; //stroke weight, stroke weight difference, stroke weight maximum

    public Islandmoos(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Islandmoos";
        nameLatin = "Cetraria islandica";

        // Starting point for the first line segment
        x1 = x;
        y1 = y;

        // Colors inspired by Iceland moss, from a lighter to a darker green
        colorStart = sketch.color(155, 182, 92);  // A lighter, yellowish-green
        colorEnd = sketch.color(85, 115, 60);    // A darker, more olive green

        alpha = 40; // Transparency to create a layered effect
        dColor = 0.005f; // Slower color change

        // Movement steps
        dx = this.area.w / 100.0f * 0.4f;
        dy = this.area.h / 100.0f * 0.4f;

        // Line thickness parameters
        sw = this.area.w / 100.0f * 0.1f;   // stroke weight
        swd = this.area.w / 100.0f * 0.01f; // stroke weight difference
        swmax = this.area.w / 100.0f * 0.3f; // stroke weight maximum
    }

    @Override
    public void draw() {
        // Loop through all the random numbers
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // Save the current position to draw a line from here later
            x1 = x;
            y1 = y;

            // Update walker properties based on the random number
            switch (r) {
                case 1: // Move up-left, get thinner
                    moveX(-dx);
                    moveY(-dy * 0.5f);
                    if (sw > swd) {
                        sw -= swd;
                    }
                    break;

                case 2: // Move down, shift color towards darker green
                    moveY(dy);
                    if (colorPercent < 1.0f) {
                        colorPercent += dColor;
                    }
                    break;

                case 3: // Move right, get thicker
                    moveX(dx);
                    if (sw + swd < swmax) {
                        sw += swd;
                    }
                    break;

                case 4: // Move up, shift color towards lighter green
                    moveY(-dy);
                    if (colorPercent > 0.0f) {
                        colorPercent -= dColor;
                    }
                    break;

                case 5: // Move down-right
                    moveY(dy * 0.5f);
                    moveX(dx);
                    break;

                case 6: // Move left
                    moveX(-dx);
                    break;
            }

            // Calculate the current color
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            // Set drawing properties for the canvas
            this.canvas.strokeWeight(sw);
            this.canvas.stroke(c, alpha);
            this.canvas.noFill(); // We are only drawing lines, no shapes

            // Draw a line from the last position to the new position to create branches
            this.canvas.line(x1, y1, x, y);
        }
    }
}
