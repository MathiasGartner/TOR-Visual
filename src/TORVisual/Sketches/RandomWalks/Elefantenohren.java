package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Elefantenohren extends RandomWalker {

    // Use separate width and height to create more organic, elliptical shapes
    float w, h;
    float dw, dh; // The amount the width and height change in each step
    float w_max, h_max; // Maximum width and height
    float w_min, h_min; // Minimum width and height
    float currentAlpha;
    float dAlpha;
    float minAlpha;
    float maxAlpha;

    public Elefantenohren(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Elefantenohren";
        nameLatin = "Aspidosperma cuspa";

        // Colors inspired by the image of dried Aspidosperma cuspa pods
        // A light, sandy beige color
        colorStart = sketch.color(222, 184, 135);
        // A darker, earthy brown
        colorEnd = sketch.color(101, 67, 33);

        // Movement steps for the walker's position - increased for more spread
        dx = this.area.w / 100.0f * 1.8f; // Erhoeht fuer mehr Ausbreitung
        dy = this.area.h / 100.0f * 1.8f; // Erhoeht fuer mehr Ausbreitung

        // Initial size and size-change parameters for the ellipses
        w = this.area.w / 100.0f * 1.5f;
        h = this.area.h / 100.0f * 1.0f;
        dw = this.area.w / 100.0f * 0.25f; // Increased for more size variation
        dh = this.area.h / 100.0f * 0.25f; // Increased for more size variation

        // Define min/max size constraints to keep shapes within a natural range
        w_max = this.area.w / 100.0f * 5.0f; // Max Groesse erhoeht
        h_max = this.area.h / 100.0f * 1.8f; // Maximale Hoehe verringert
        w_min = this.area.w / 100.0f * 0.2f;  // Decreased for more size variation
        h_min = this.area.h / 100.0f * 0.2f;  // Decreased for more size variation

        // Speed of color transition
        dColor = 0.06f; // Erhoeht fuer schnellere Farbwechsel

        // --- Transparency Variation ---
        maxAlpha = 128; // Max 50%
        minAlpha = 20;  // Min ~8%
        currentAlpha = (maxAlpha + minAlpha) / 2; // Start in the middle
        dAlpha = 10;     // Erhoeht fuer staerkere Transparenz-Aenderungen
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            // Diese Regel zieht den Walker zurück zur Mitte, wenn er sich zu weit entfernt.
            // So wird verhindert, dass die Form an den Rändern "kleben" bleibt.
            float centerX = this.area.w / 2.0f;
            float centerY = this.area.h / 2.0f;

            // Berechne die Distanz zum Zentrum
            float distX = centerX - x;
            float distY = centerY - y;

            // Eine staerkere, zur Distanz proportionale Kraft anwenden, um ihn zurueckzuziehen.
            // Multiplikator erhoeht, um die Form staerker vom Rand fernzuhalten.
            x += distX * 0.006f;
            y += distY * 0.006f;


            // The switch statement defines how each random number (1-6) affects the drawing
            switch (r) {
                case 1: // Move up-left, shrink width, shift color to the lighter start color, decrease alpha
                    moveX(-dx * 0.5f);
                    moveY(-dy);
                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    if (w > w_min) {
                        w -= dw;
                    }
                    if (currentAlpha > minAlpha) {
                        currentAlpha -= dAlpha;
                    }
                    break;

                case 2: // Move left, shrink height, slightly more transparent
                    moveX(-dx);
                    if (h > h_min) {
                        h -= dh;
                    }
                    if (currentAlpha > minAlpha) {
                        currentAlpha -= dAlpha * 0.5f;
                    }
                    break;

                case 3: // A more sudden downward move, grow height, shrink width to create thinner shapes
                    moveY(dy * 1.2f);
                    if (h < h_max) {
                        h += dh * 2;
                    }
                    if (w > w_min) {
                        w -= dw * 2;
                    }
                    if (colorPercent > 0) {
                        colorPercent -= dColor * 3; // Fast change to light
                    }
                    break;

                case 4: // Move up, shift color to the darker end color, increase alpha
                    moveY(-dy);
                    if (colorPercent < 1) {
                        colorPercent += dColor * 3; // Faster change to dark
                    }
                    if (currentAlpha < maxAlpha) {
                        currentAlpha += dAlpha;
                    }
                    break;

                case 5: // Grow in both width and height, slightly more opaque
                    if (w < w_max) {
                        w += dw;
                    }
                    if (h < h_max) {
                        h += dh;
                    }
                    if (currentAlpha < maxAlpha) {
                        currentAlpha += dAlpha * 0.5f;
                    }
                    break;

                case 6: // Move down-right
                    moveY(dy);
                    moveX(dx);
                    break;
            }

            // Get the interpolated color for the current step
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            // Set drawing properties for the shape
            this.canvas.noStroke(); // The dried pods don't have a distinct outline, so no stroke is used
            this.canvas.fill(c, currentAlpha); // Use the variable alpha value

            // Draw a custom heart-like shape, adjusted to be more round
            this.canvas.beginShape();
            // Bottom point
            this.canvas.vertex(x, y + h * 0.5f);
            // Right Lobe using a bezier curve for a rounded, organic look
            this.canvas.bezierVertex(x + w * 0.7f, y, x + w * 0.5f, y - h * 0.5f, x, y - h * 0.1f);
            // Left Lobe, mirroring the right side
            this.canvas.bezierVertex(x - w * 0.5f, y - h * 0.5f, x - w * 0.7f, y, x, y + h * 0.5f);
            this.canvas.endShape(PApplet.CLOSE);
        }
    }
}









