package TORVisual.Data;

import java.awt.geom.Point2D;

public class PiMCPoint {

    public Point2D.Float coords;
    public Point2D.Float drawAt;
    public Boolean inCircle;

    public PiMCPoint(float x, float y, float drawSize) {
        this.coords = new Point2D.Float(x, y);
        this.drawAt = new Point2D.Float(drawSize + x * drawSize, drawSize + y * drawSize);
        this.inCircle = Math.sqrt(x * x + y * y) >= 1.0;
    }
}
