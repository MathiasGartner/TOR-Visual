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

        colorStart = sketch.color(255, 255, 180); // Sehr helles Pastellgelb
        colorEnd = sketch.color(255, 220, 0);     // Kräftiges Sonnengelb

        stemColor = sketch.color(140, 120, 90, 50);

        // --- GEÄNDERT: Noch mehr Transparenz ---
        // Alpha-Wert von 30 auf 15 reduziert.
        alpha = 15;

        float stepSize = this.area.w / 100.0f * 0.6f;
        dx = stepSize;
        dy = stepSize;

        // --- GEÄNDERT: Shapes noch kleiner (Flower Cluster) ---
        // Multiplikator von 0.8f auf 0.6f reduziert.
        flowerClusterSize = this.area.h / 100.0f * 0.6f;

        float spawnBorder = border + flowerClusterSize * 2;
        x = sketch.random(spawnBorder, this.area.w - spawnBorder);
        y = sketch.random(spawnBorder, this.area.h - spawnBorder);
    }

    /**
     * Zeichnet eine Blüte an der aktuellen Position des Walkers.
     */
    private void drawFlower() {
        // --- GEÄNDERT: Shapes noch kleiner (Anzahl der Florets) ---
        // Anzahl leicht reduziert, passend zur kleineren Blütengröße.
        int numFlorets = 15;

        // --- GEÄNDERT: Shapes noch kleiner (Floret Size) ---
        // Basisgröße der einzelnen Punkte von 2.0f auf 1.5f reduziert.
        float floretSize = 1.5f;

        for (int i = 0; i < numFlorets; i++) {
            float offsetX = sketch.random(-flowerClusterSize, flowerClusterSize);
            float offsetY = sketch.random(-flowerClusterSize, flowerClusterSize);

            if (PApplet.dist(0, 0, offsetX, offsetY) < flowerClusterSize) {
                var c = sketch.lerpColor(colorStart, colorEnd, sketch.random(1.0f));
                // Die Transparenz bleibt vom neuen, niedrigeren alpha-Wert beeinflusst.
                this.canvas.fill(c, alpha + sketch.random(40));
                this.canvas.noStroke();
                this.canvas.circle(x + offsetX, y + offsetY, floretSize * sketch.random(0.5f, 1.5f));
            }
        }
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1: // Versuch: Nach links
                    if (x > border + dx) moveX(-dx); else moveX(dx);
                    break;
                case 2: // Versuch: Nach rechts
                    if (x < this.area.w - border - dx) moveX(dx); else moveX(-dx);
                    break;
                case 3: // Versuch: Nach oben
                    if (y > border + dy) moveY(-dy); else moveY(dy);
                    break;
                case 4: // Versuch: Nach unten
                    if (y < this.area.h - border - dy) moveY(dy); else moveY(-dy);
                    break;
                case 5:
                    // Pause
                    break;
                case 6: // Blühen
                    drawFlower();
                    continue;
            }

            this.canvas.stroke(stemColor);
            this.canvas.strokeWeight(1.2f);
            this.canvas.point(x, y);
        }
    }
}




