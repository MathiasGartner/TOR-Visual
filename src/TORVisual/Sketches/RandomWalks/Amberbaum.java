package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Amberbaum extends RandomWalker {
    // Movement and size parameters
    float dx, dy;
    float sizeMin, sizeMax, sizeStep;

    // Transparency parameters
    float alphaMin, alphaMax, alphaStep;
    float baseAlpha;

    // NEU: Eine ArrayList für die neue Farbpalette
    ArrayList<Integer> colorPalette;

    public Amberbaum(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Amberbaum";
        nameLatin = "Liquidambar";

        // NEU: Die Farbpalette wird mit den vier angegebenen Farbtönen gefüllt
        colorPalette = new ArrayList<Integer>();
        colorPalette.add(sketch.color(86, 87, 71));    // #565747
        colorPalette.add(sketch.color(190, 191, 178)); // #BEBFB2
        colorPalette.add(sketch.color(185, 174, 110)); // #B9AE6E
        colorPalette.add(sketch.color(165, 153, 105)); // #A59969

        // Define movement speed
        dx = this.area.w / 100.0f * 0.5f;
        dy = this.area.h / 100.0f * 0.5f;

        // Define size boundaries and change step
        sizeMin = this.area.h / 100.0f * 0.1f;
        sizeMax = this.area.h / 100.0f * 0.5f;
        sizeStep = this.area.h / 100.0f * 0.02f;

        // Define transparency boundaries and change step
        alphaMin = 10;
        alphaMax = 80;
        alphaStep = 5;
        baseAlpha = 40;

        alpha = (int) baseAlpha;

        // Initialize position and size
        x = startX;
        y = startY;
        size = sizeMax;
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    // Move left and shrink, decrease transparency
                    moveX(-dx);
                    if (size > sizeMin) {
                        size -= sizeStep;
                    }
                    if (alpha > alphaMin) {
                        alpha -= alphaStep;
                    }
                    break;

                case 2:
                    // Move up and grow, increase transparency
                    moveY(-dy);
                    if (size < sizeMax) {
                        size += sizeStep;
                    }
                    if (alpha < alphaMax) {
                        alpha += alphaStep;
                    }
                    break;

                case 3:
                    // Move right, slight shrink
                    moveX(dx);
                    if (size > sizeMin) {
                        size -= sizeStep / 2;
                    }
                    if (alpha > alphaMin) {
                        alpha -= alphaStep / 2;
                    }
                    break;

                case 4:
                    // Move down, slight grow
                    moveY(dy);
                    if (size < sizeMax) {
                        size += sizeStep / 2;
                    }
                    if (alpha < alphaMax) {
                        alpha += alphaStep / 2;
                    }
                    break;

                case 5:
                    // Add a subtle pulsating effect to the size
                    if (size > sizeMin && size < sizeMax) {
                        size += ((Math.sin(sketch.frameCount * 0.1) + 1) / 2) * sizeStep;
                    }
                    break;

                case 6:
                    // Add a random jitter for a more organic, spiky feel
                    moveX((float) ((Math.random() - 0.5) * dx));
                    moveY((float) ((Math.random() - 0.5) * dy));
                    break;
            }

            // GEÄNDERT: Wählt eine zufällige Farbe aus der neuen Palette
            int c = colorPalette.get((int)sketch.random(colorPalette.size()));

            this.canvas.fill(c, alpha);
            this.canvas.stroke(c, alpha);

            // Stroke weight proportional to size creates a rougher texture
            this.canvas.strokeWeight(size / 5);
            this.canvas.ellipse(x, y, size, size);
        }
    }
}