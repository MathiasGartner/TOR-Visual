package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Strohblume extends RandomWalker {
    float flowerClusterSize;
    int stemColor;

    public Strohblume(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Strohblume";
        nameLatin = "Helichrysum";

        // Bright yellow color palette for the flowers
        colorStart = sketch.color(255, 220, 0);   // Bright Yellow
        colorEnd = sketch.color(255, 180, 0);     // Golden Yellow
        stemColor = sketch.color(140, 120, 90, 50); // Muted brown for stems

        alpha = 60; // Semi-opaque flowers
        dColor = 0.05f;

        // Movement steps: strong vertical bias to create stems
        dx = this.area.w / 100.0f * 0.2f;
        dy = this.area.h / 100.0f * 0.8f;

        flowerClusterSize = this.area.h / 100.0f * 1.5f;

        // Start at the bottom of the screen
        y = this.area.h - border;
        x = sketch.random(border, this.area.w - border);
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // --- New Movement and Drawing Rules for Helichrysum ---

            // A roll of 6 triggers a "bloom" event.
            if (r == 6) {
                // Draw a dense cluster of circles to represent the flower head.
                int numFlorets = 25;
                float floretSize = 4.0f;
                for (int i = 0; i < numFlorets; i++) {
                    float offsetX = sketch.random(-flowerClusterSize, flowerClusterSize);
                    float offsetY = sketch.random(-flowerClusterSize, flowerClusterSize);

                    // Only draw in a circular area
                    if (PApplet.dist(0, 0, offsetX, offsetY) < flowerClusterSize) {
                        // Interpolate color for variation in the flower head
                        var c = sketch.lerpColor(colorStart, colorEnd, sketch.random(1.0f));
                        this.canvas.fill(c, alpha + sketch.random(40));
                        this.canvas.noStroke();
                        this.canvas.circle(x + offsetX, y + offsetY, floretSize * sketch.random(0.8f, 1.2f));
                    }
                }

                // After blooming, the "plant" dies and a new one starts at the bottom.
                y = this.area.h - border;
                x = sketch.random(border, this.area.w - border);

            } else {
                // For all other rolls, we grow the stem upwards.
                switch (r) {
                    case 1:
                    case 2:
                        // Gentle sway to the left
                        moveX(-dx);
                        break;
                    case 3:
                    case 4:
                    case 5:
                        // Strong upward growth
                        moveY(-dy);
                        break;
                }

                // Draw the stem as a thin, semi-transparent line
                this.canvas.stroke(stemColor);
                this.canvas.strokeWeight(1.2f);
                this.canvas.point(x, y); // Draw points to create a dotted stem line
            }
        }
    }
}

