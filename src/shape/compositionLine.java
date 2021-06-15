package shape;

import java.awt.Graphics;

public class compositionLine extends basicLine{
	public compositionLine(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void draw(Graphics g) {
		double distance = Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		double len=8;
		double normalDistanceX = len*(x2-x1)/distance;
        double normalDistanceY = len*(y2-y1)/distance;
        double extendPoint1X = x2-normalDistanceX-normalDistanceY;
        double extendPoint1Y = y2-normalDistanceY+normalDistanceX;
        double extendPoint2X = x2-normalDistanceX+normalDistanceY;
        double extendPoint2Y = y2-normalDistanceY-normalDistanceX;
        double extendPoint3X = x2-normalDistanceX*2;
        double extendPoint3Y = y2-normalDistanceY*2;
        int [] X = { x2, (int) extendPoint1X, (int) extendPoint3X, (int) extendPoint2X };
        int [] Y = { y2, (int) extendPoint1Y, (int) extendPoint3Y, (int) extendPoint2Y };
        g.fillPolygon(X, Y, 4);
		g.drawLine(x1, y1, x2, y2);
	}
}
