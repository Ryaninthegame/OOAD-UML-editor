package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class groupObject extends baseShape {
	private List<baseShape> shapeInGroup = new ArrayList<baseShape>();
	private Rectangle boundArea = new Rectangle();
	private baseShape selectedShape = null;
	public boolean groupObjFlag = false;
	
	public void addShapes(baseShape shape) { shapeInGroup.add(shape); }
	public void setGroupFlag(boolean flag) { groupObjFlag = flag; }
	public void setName(String name) { selectedShape.setName(name); }
	public void resetSelectedShape() { selectedShape = null; }
	public boolean returnGroupFlag() { return groupObjFlag; }
	public List<baseShape> getShapes() { return shapeInGroup; }
	public baseShape getSelectedShape() { return selectedShape; }
	
	// 顯示被 group 的 object
	public void draw(Graphics g) { 
		for (int i=0; i<shapeInGroup.size(); i++) {
			baseShape shape = shapeInGroup.get(i);
			shape.draw(g);
		}
	}
	
	// group 後的 color
	public void show(Graphics g) {
		int transparency = 50; 
		int offset = 10;
		g.setColor(new Color(110, 220, 220, transparency));
		g.fillRect(boundArea.x-offset, boundArea.y-offset, boundArea.width+offset*2, boundArea.height+offset*2);
		g.drawRect(boundArea.x-offset, boundArea.y-offset, boundArea.width+offset*2, boundArea.height+offset*2);
		if (selectedShape != null) 
			selectedShape.show(g);
	}
	
	public void resetPosition(int moveX, int moveY) {
		for (int i=0; i<shapeInGroup.size(); i++) {
			baseShape tempShape = shapeInGroup.get(i);
			tempShape.resetPosition(moveX, moveY);
		}
		resetbounds(moveX, moveY);
	}
	
	public String insideState(Point mousePoint) {
		for (int i=0; i<shapeInGroup.size(); i++) {
			baseShape tempShape = shapeInGroup.get(i);
			String judgeInside = tempShape.insideState(mousePoint);
			if (judgeInside != null) {
				selectedShape = tempShape;
				return "insideGroup";
			}
		}
		return null;
	}
	
	public void resetbounds(int moveX, int moveY) {
		boundArea.setLocation(boundArea.x + moveX, boundArea.y + moveY);
		x1 = boundArea.x;
		y1 = boundArea.y;
		x2 = boundArea.x + boundArea.width;
		y2 = boundArea.y + boundArea.height;
	}
	
	public void setboundArea() {
		int leftX = Integer.MAX_VALUE; 
		int rightX = Integer.MIN_VALUE;
		int upY = Integer.MAX_VALUE;
		int bottomY = Integer.MIN_VALUE;

		for (int i=0; i<shapeInGroup.size(); i++) {
			baseShape shape = shapeInGroup.get(i);
			if (shape.getX1()<leftX) { leftX = shape.getX1(); }
			if (shape.getX2()>rightX) { rightX = shape.getX2(); }
			if (shape.getY1()<upY) { upY = shape.getY1(); }
			if (shape.getY2()>bottomY) { bottomY = shape.getY2(); }
		}
		Point upLeft = new Point(leftX, upY);
		Point bottomRight = new Point(rightX, bottomY);
		boundArea.setBounds(upLeft.x, upLeft.y, Math.abs(upLeft.x-bottomRight.x), Math.abs(upLeft.y-bottomRight.y));
	}
	
	@Override public void resetPosition() {}
	@Override public void showPort(Graphics g) {}
	@Override public Port getPort(int portIndex) { return null; }
	@Override public int getPortNum() { return 0; }
	@Override public void resetPort(Port port, baseShape line) {}
	@Override public void resetStartEnd(Point p) {}
}
