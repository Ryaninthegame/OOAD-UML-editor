package shape;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public abstract class baseShape {
	protected int x1, y1, x2, y2;
	public boolean groupSelected;
	
	public int getX1() { return x1; }
	public int getY1() { return y1; }
	public int getX2() { return x2; }
	public int getY2() { return y2; }
	
	public abstract void draw(Graphics g);
	public abstract void show(Graphics g);
	public abstract String insideState(Point p);
	
	// basicObj
	public abstract int getPortNum();
	public abstract void setName(String name); 
	public abstract void resetPosition(int moveX, int moveY); // basicObj/groupObj
	public abstract void showPort(Graphics g);
	public abstract Port getPort(int portIndex);
	
	// basicLine
	public abstract void resetPort(Port port, baseShape line);
	public abstract void resetStartEnd(Point p);
	public abstract void resetPosition();  
	
	// groupObj
	public abstract void resetSelectedShape(); 
	public abstract void setGroupFlag(boolean flag);
	public abstract boolean returnGroupFlag();
	public abstract List<baseShape> getShapes();
	public abstract baseShape getSelectedShape();
}
