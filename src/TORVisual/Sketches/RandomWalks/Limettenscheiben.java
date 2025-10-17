package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Limettenscheiben extends RandomWalker {

    // Variablen für die Steuerung der visuellen Darstellung
    float angle_rad, sw, swd, swmax, size, sizemax, sizemin, sd, dangle;

    // Farbpalette für die Variation je nach Würfelwurf
    int colorLime1, colorLime2, colorLime3, colorLime4;

    /**
     * Konstruktor für die Limettenscheiben.
     * Initialisiert alle spezifischen Eigenschaften dieses RandomWalkers.
     * @param sketch Das PApplet-Objekt zum Zeichnen.
     * @param area Der Bereich, in dem gezeichnet werden soll.
     * @param resultsToShow Die Würfelergebnisse, die die Bewegung steuern.
     */
    public Limettenscheiben(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        // Name und lateinischer Name
        name = "Limettenscheiben";
        nameLatin = "Citrus latifolia";

        // Haupt-Farbpalette für den allgemeinen Verlauf
        colorStart = sketch.color(123, 135, 15); // #7B870F
        colorEnd = sketch.color(192, 189, 164);   // #C0BDA4

        // NEU: Zusätzliche Farben für die Variation bei jedem Würfelwurf
        colorLime1 = sketch.color(192, 189, 164); // #C0BDA4 (ein heller, fast beiger Ton)
        colorLime2 = sketch.color(79, 71, 15);    // #4F470F (ein sehr dunkler Grünton)
        colorLime3 = sketch.color(123, 135, 15);  // #7B870F (ein kräftiges Limettengrün)
        colorLime4 = sketch.color(197, 136, 37);  // #C58825 (ein gelb-oranger Ton)

        // Alpha-Wert für Transparenz (ca. 10-50% von 255)
        alpha = 20;

        // Bewegungsparameter
        dx = this.area.w / 100.0f * 0.6f;
        dy = this.area.w / 100.0f * 0.6f;
        angle_rad = 20;
        dangle = 0.5f;

        // Parameter für die Strichstärke (simuliert die Schale)
        sw = this.area.w / 100.0f * 0.1f;
        swd = this.area.w / 100.0f * 0.05f;
        swmax = this.area.w / 100.0f * 0.4f;

        // Parameter für die Größe der Kreise (die "Scheiben")
        size = this.area.h / 100.0f * 0.2f;
        sizemax = this.area.h / 100.0f * 0.3f;
        sizemin = this.area.h / 100.0f * 0.1f;
        sd = this.area.h / 100.0f * 0.05f;
    }

    /**
     * Die draw-Methode wird in jedem Frame aufgerufen.
     * Sie iteriert durch die Würfelergebnisse, passt die Zeichenparameter an
     * und wählt eine Farbe basierend auf dem Würfelergebnis,
     * bevor für jedes Ergebnis eine Limettenscheibe gezeichnet wird.
     */
    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;
            int c; // Lokale Variable für die Farbe dieser Iteration

            // Die Logik basiert auf dem Würfelergebnis (1-6)
            switch (r) {
                case 1: // Größer werden, nach links oben bewegen
                    if (size + sd < sizemax) {
                        size += sd;
                    }
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    c = colorLime1; // Heller Ton
                    break;

                case 2: // Strichstärke verringern
                    if (sw > swd && sw < swmax) {
                        sw -= swd;
                    }
                    c = colorLime2; // Dunkler Grünton
                    break;

                case 3: // Transparenz verringern, Winkel ändern
                    if (alpha <= 60) {
                        alpha += 2;
                    }
                    angle_rad = angle_rad + dangle;
                    c = colorLime3; // Kräftiges Limettengrün
                    break;

                case 4: // Strichstärke erhöhen
                    if (sw + swd < swmax) {
                        sw += swd;
                    }
                    c = colorLime4; // Gelb-oranger Ton
                    break;

                case 5: // Kleiner werden, Transparenz erhöhen
                    if (size - sd > sizemin) {
                        size -= sd;
                    }
                    if (alpha >= 10) {
                        alpha -= 2;
                    }
                    c = colorLime2; // Erneut der dunkle Grünton für mehr Variation
                    break;

                case 6: // Nach rechts unten bewegen, Winkel ändern
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);
                    angle_rad = angle_rad - dangle;
                    c = colorLime3; // Erneut das kräftige Limettengrün
                    break;

                default: // Fallback-Farbe, falls etwas schiefgeht
                    c = colorStart;
                    break;
            }

            // Zeicheneinstellungen setzen
            this.canvas.fill(c, alpha);             // Füllfarbe mit Transparenz
            this.canvas.strokeWeight(sw);           // Strichstärke
            this.canvas.stroke(c, alpha + 15);      // Randfarbe, etwas weniger transparent

            // Zeichnet den Kreis (die "Limettenscheibe") an der aktuellen Position
            this.canvas.circle(x, y, size);
        }
    }
}
