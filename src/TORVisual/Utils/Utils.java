package TORVisual.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
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
