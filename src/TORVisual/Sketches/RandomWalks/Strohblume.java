package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;



public class Strohblume extends RandomWalker {
    float flowerClusterSize;
    int stemColor;

    // NEU: Eine ArrayList für mehrere Akzentfarben
    ArrayList<Integer> accentPalette;

    public Strohblume(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Strohblume";
        nameLatin = "Helichrysum";

        colorStart = sketch.color(240, 220, 180); // Helles Sand-/Strohgelb
        colorEnd = sketch.color(219, 204, 0);     // Kräftiges gelb

        // NEU: Die Akzent-Palette wird mit den neuen Farben gefüllt
        accentPalette = new ArrayList<Integer>();
        accentPalette.add(sketch.color(220, 206, 0));   // #DCCE00
        accentPalette.add(sketch.color(224, 218, 82));  // #E0DA52

        stemColor = sketch.color(140, 120, 90, 50);
        alpha = 15;

        float stepSize = this.area.w / 100.0f * 0.6f;
        dx = stepSize;
        dy = stepSize;

        flowerClusterSize = this.area.h / 100.0f * 0.6f;

        float spawnBorder = border + flowerClusterSize * 2;
        x = sketch.random(spawnBorder, this.area.w - spawnBorder);
        y = sketch.random(spawnBorder, this.area.h - spawnBorder);
    }

    /**
     * Zeichnet eine Blüte an der aktuellen Position des Walkers.
     */
    private void drawFlower() {
        int numFlorets = 15;
        float floretSize = 1.5f;

        for (int i = 0; i < numFlorets; i++) {
            float offsetX = sketch.random(-flowerClusterSize, flowerClusterSize);
            float offsetY = sketch.random(-flowerClusterSize, flowerClusterSize);

            if (PApplet.dist(0, 0, offsetX, offsetY) < flowerClusterSize) {
                int c;
                if (sketch.random(1.0f) < 0.85) { // 85% Chance für eine Farbe aus dem Verlauf
                    c = sketch.lerpColor(colorStart, colorEnd, sketch.random(1.0f));
                } else { // 15% Chance für eine leuchtende Akzentfarbe
                    // GEÄNDERT: Wählt eine zufällige Farbe aus der neuen Akzent-Palette
                    c = accentPalette.get((int)sketch.random(accentPalette.size()));
                }

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
                case 1:
                    if (x > border + dx) moveX(-dx); else moveX(dx);
                    break;
                case 2:
                    if (x < this.area.w - border - dx) moveX(dx); else moveX(-dx);
                    break;
                case 3:
                    if (y > border + dy) moveY(-dy); else moveY(dy);
                    break;
                case 4:
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


