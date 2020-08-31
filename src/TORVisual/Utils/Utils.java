package TORVisual.Utils;

import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static class Colors {
        private static PApplet dummy;
        public static int BACKGROUND;
        public static int WHITE;
        public static int GRAY;
        public static int PURPLE;
        public static int GREEN;

        static {
            dummy = new PApplet();
            BACKGROUND = dummy.color(10, 11, 12);
            WHITE = dummy.color(248, 250, 251);
            GRAY = dummy.color(74, 74, 72);
            PURPLE = dummy.color(90, 60, 120);
            GREEN = dummy.color(40, 140, 120);
        }
    }

    public static int randInt(int from, int to) {
        int randomNum = ThreadLocalRandom.current().nextInt(from, to+1);
        return randomNum;
    }

    public static int randClientId() {
        return randInt(1, 27);
    }

    public static int randDiceResult() {
        return randInt(1, 6);
    }
}
