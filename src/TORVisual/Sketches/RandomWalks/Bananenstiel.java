package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Bananenstiel extends RandomWalker {

    float sw, sw_max, sw_min, d_sw; //stroke-weight, stroke-weight-max, stroke-weight-min, stroke-weight-delta

    // NEU: Eine ArrayList für die neue Farbpalette
    ArrayList<Integer> colorPalette;

    public Bananenstiel(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Bananenstiel";
        nameLatin = "Musa acuminata";

        // Decrease the border to allow the walk to get closer to the edges.
        this.border = this.area.w * 0.05f; // Use 5% of the canvas width as a border
        this.minX = this.border;
        this.maxX = this.area.w - this.border;
        this.minY = this.border;
        this.maxY = this.area.h - this.border;

        // GEÄNDERT: Neue Farbpalette mit den gewünschten Grüntönen
        colorPalette = new ArrayList<Integer>();
        colorPalette.add(sketch.color(165, 187, 175)); // #A5BBAF
        colorPalette.add(sketch.color(133, 154, 123)); // #859A7B
        colorPalette.add(sketch.color(54, 79, 76));    // #364F4C
        colorPalette.add(sketch.color(158, 190, 169)); // #9EBEA9
        colorPalette.add(sketch.color(55, 69, 78));    // #37454E

        alpha = 25;

        // Increase movement steps to cover more area
        dx = this.area.w / 100.0f * 0.7f;
        dy = this.area.h / 100.0f * 0.7f;

        // Line thickness parameters
        sw = this.area.w / 100.0f * 0.1f;    // Initial stroke weight
        d_sw = this.area.w / 100.0f * 0.01f; // Change in stroke weight
        sw_max = this.area.w / 100.0f * 0.3f;  // Maximum stroke weight
        sw_min = this.area.w / 100.0f * 0.05f; // Minimum stroke weight

        // Initialize line coordinates
        x1 = x;
        y1 = y;
    }

    /**
     * Moves the walker on the x-axis and bounces it off the horizontal walls.
     * @param steps The distance to move.
     */
    private void moveX_bouncy(float steps) {
        x += steps;
        if (x > maxX) {
            x = maxX - (x - maxX); // Bounce off the right wall
        } else if (x < minX) {
            x = minX + (minX - x); // Bounce off the left wall
        }
    }

    /**
     * Moves the walker on the y-axis and bounces it off the vertical walls.
     * @param steps The distance to move.
     */
    private void moveY_bouncy(float steps) {
        y += steps;
        if (y > maxY) {
            y = maxY - (y - maxY); // Bounce off the bottom wall
        } else if (y < minY) {
            y = minY + (minY - y); // Bounce off the top wall
        }
    }


    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // Update the previous point to the current point before moving
            x2 = x;
            y2 = y;

            switch (r) {
                case 1:
                    moveX_bouncy(-dx * 0.8f);
                    moveY_bouncy(-dy);
                    // Die alte Logik zur Farbveränderung wird nicht mehr benötigt
                    break;

                case 2:
                    moveX_bouncy(dx * 0.8f);
                    moveY_bouncy(-dy * 0.5f);
                    if (sw > sw_min) {
                        sw -= d_sw;
                    }
                    break;

                case 3:
                    moveY_bouncy(dy * 1.2f);
                    break;

                case 4:
                    moveX_bouncy(-dx);
                    moveY_bouncy(dy * 0.5f);
                    if (sw < sw_max) {
                        sw += d_sw;
                    }
                    break;

                case 5:
                    moveX_bouncy(dx * 0.8f);
                    moveY_bouncy(dy);
                    // Die alte Logik zur Farbveränderung wird nicht mehr benötigt
                    break;

                case 6:
                    moveY_bouncy(-dy * 1.2f);
                    moveX_bouncy(dx * 0.2f);
                    break;
            }

            // GEÄNDERT: Wählt eine zufällige Farbe aus der neuen Palette
            var c = colorPalette.get((int)sketch.random(colorPalette.size()));
            this.canvas.strokeWeight(sw);
            this.canvas.stroke(c, alpha);
            this.canvas.noFill();

            // Draw a line from the previous position to the new one
            this.canvas.line(x2, y2, x, y);
        }
    }
}


