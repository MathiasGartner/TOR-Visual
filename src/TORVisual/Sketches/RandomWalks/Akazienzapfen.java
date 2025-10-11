package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Akazienzapfen extends RandomWalker {

    // Parameters for controlling the visual output
    float strokeWeight;
    float strokeWeightChange;
    float maxStrokeWeight;
    float maxSize;
    float irregularity; // Controls how irregular the shape is
    float dAlpha;       // The amount alpha changes by
    float minAlpha;
    float maxAlpha;

    public Akazienzapfen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        // Naming the walker
        name = "Akazienzapfen";
        nameLatin = "Cupressus";

        // --- Initialize visual parameters to match the cones ---

        // Set a darker color palette
        colorStart = sketch.color(60, 40, 28);  // Even darker brown
        colorEnd = sketch.color(140, 115, 90); // A darker, muted tan/brown
        dColor = 0.02f; // Controls the speed of color change

        // Set the transparency parameters for more transparency (max 50% opacity would be ~127, but we'll use a lower range for subtlety)
        alpha = 15;
        minAlpha = 5; // Lowered for more transparency
        maxAlpha = 25; // Lowered for more transparency
        dAlpha = 0.5f;

        // Define movement speed
        dx = this.area.w / 100.0f * 0.3f;
        dy = this.area.h / 100.0f * 0.3f;

        // Define stroke weight parameters to create the "crack" effect
        strokeWeight = this.area.w / 100.0f * 0.05f;
        strokeWeightChange = this.area.w / 100.0f * 0.02f;
        maxStrokeWeight = this.area.w / 100.0f * 0.4f;

        // Define the size parameters for the cone segments
        size = this.area.h / 100.0f * 0.6f; // Smaller initial size
        ds = this.area.h / 100.0f * 0.04f; // Smaller size change
        maxSize = this.area.h / 100.0f * 1.0f; // Reduced max size

        // How much the vertices can be indented
        irregularity = 0.5f;
    }

    @Override
    public void draw() {
        // Process each random number result to draw a step of the walk
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // Add a random variation to each movement step
            float randomX = sketch.random(-dx * 0.5f, dx * 0.5f);
            float randomY = sketch.random(-dy * 0.5f, dy * 0.5f);

            // The switch statement determines how each random number affects the visualization
            switch (r) {
                case 1: // Move up-left and decrease stroke weight
                    moveX(-dx + randomX);
                    moveY(-dy + randomY);
                    if (strokeWeight > strokeWeightChange) {
                        strokeWeight -= strokeWeightChange;
                    }
                    break;

                case 2: // Become more brown (darker) and less transparent
                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    if (alpha > minAlpha) {
                        alpha -= dAlpha;
                    }
                    break;

                case 3: // Move down and decrease segment size
                    moveY(dy + randomY);
                    moveX(randomX); // Add slight horizontal drift
                    if (size > ds) {
                        size -= ds;
                    }
                    break;

                case 4: // Move up and increase segment size, but not beyond maxSize
                    moveY(-dy + randomY);
                    moveX(randomX); // Add slight horizontal drift
                    if (size < maxSize) {
                        size += ds;
                    }
                    break;

                case 5: // Become more tan (lighter) and more transparent
                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    if (alpha < maxAlpha) {
                        alpha += dAlpha;
                    }
                    break;

                case 6: // Move down-right and increase stroke weight for deeper cracks
                    moveX(dx + randomX);
                    moveY(dy + randomY);
                    if (strokeWeight < maxStrokeWeight) {
                        strokeWeight += strokeWeightChange;
                    }
                    break;
            }

            // --- Drawing the cone segment ---

            // Interpolate color for the current step
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            // Set the fill and stroke for the segment
            this.canvas.fill(c, alpha);
            this.canvas.strokeWeight(strokeWeight);
            this.canvas.stroke(colorStart, alpha * 1.2f); // Use the dark color for the stroke to emphasize cracks

            // Draw a more complex, indented polygon instead of a simple triangle
            int vertices = 6; // Number of vertices for our shape
            float baseRadius = size;

            this.canvas.beginShape();
            for (int i = 0; i < vertices; i++) {
                // Calculate radius with random indentation for this vertex
                float radius = baseRadius + sketch.random(-baseRadius * irregularity, baseRadius * irregularity);

                // Calculate angle for this vertex
                float angle = sketch.TWO_PI / vertices * i;

                // Calculate vertex position
                float vx = x + sketch.cos(angle) * radius;
                float vy = y + sketch.sin(angle) * radius;
                this.canvas.vertex(vx, vy);
            }
            this.canvas.endShape(PApplet.CLOSE);
        }
    }
}


