package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;

import main.Canvas;

public abstract class basicObject extends baseShape{
	private int offset = 3;
	private Port[] arrayPort = new Port[4];
	protected int width, height, stringWidth; 
	protected int defaultClassWidth = 100;
	protected int defaultUseCaseWidth = 120;
	protected String objectName = "default"; 
	protected Canvas canvas = Canvas.getInstance();
	
	public Port getPort(int portIndex) { return arrayPort[portIndex]; }
	public void setName(String name) { objectName = name; }
	public int getPortNum() { return arrayPort.length; }
	
	// class/usecase 被 preGroup 顯示 ports 與 color
	public void show(Graphics g) {
		for(int i=0; i<arrayPort.length; i++) {
			g.setColor(Color.red);
			g.fillRect(arrayPort[i].x, arrayPort[i].y, arrayPort[i].width, arrayPort[i].height);
		}
		draw(g);
	}
	
	// Line 在拖曳時顯示所有 clase/usecase 的 port
	public void showPort(Graphics g) {
		for(int i=0; i<arrayPort.length; i++) {
			g.setColor(Color.red);
			g.fillRect(arrayPort[i].x, arrayPort[i].y, arrayPort[i].width, arrayPort[i].height);
		}
	}
	
	// 判斷滑鼠點選是否在clase/usecase內
	public String insideState(Point mousePoint) {
		Point center = new Point((x1+x2)/2, (y1+y2)/2);
		Point[] arrayPoint = { new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2) };
		for (int i=0; i<arrayPoint.length; i++) {
			Polygon judgeArea = new Polygon();
			int nextIndex = (i+1)%4;
			judgeArea.addPoint(arrayPoint[i].x, arrayPoint[i].y);
			judgeArea.addPoint(arrayPoint[nextIndex].x, arrayPoint[nextIndex].y);
			judgeArea.addPoint(center.x, center.y);
			if (judgeArea.contains(mousePoint)) 
				return Integer.toString(i);
		}
		return null;
	}
	
	public void resetPosition(int moveX, int moveY) {
		x1 = x1 + moveX;
		y1 = y1 + moveY;
		x2 = x1 + width;
		y2 = y1 + height;
		int[][] point = {{(x1+x2)/2, y1-offset}, {x2+offset, (y1+y2)/2}, {(x1+x2)/2, y2+offset}, {x1-offset, (y1+y2)/2}};
		for(int i=0; i<arrayPort.length; i++) {
			arrayPort[i].setUpPort(point[i][0], point[i][1], offset);
			arrayPort[i].resetLines();
		}
	}
	
	// 設定 clase/usecase 上的 port
	protected void createPorts() {
		int[][] point = {{(x1+x2)/2, y1-offset}, {x2+offset, (y1+y2)/2}, {(x1+x2)/2, y2+offset}, {x1-offset, (y1+y2)/2}};
		for(int i=0; i<arrayPort.length; i++) {
			Port tempPort = new Port();
			tempPort.setUpPort(point[i][0], point[i][1], offset);
			arrayPort[i] = tempPort;
		}
	}
	
	@Override public void resetPosition() {}
	@Override public void resetSelectedShape() {}
	@Override public baseShape getSelectedShape() { return null; }
	@Override public boolean returnGroupFlag() { return false; }
	@Override public void setGroupFlag(boolean flag) {}
	@Override public List<baseShape> getShapes() { return null;	}
	@Override public void resetPort(Port port, baseShape line) {}
	@Override public void resetStartEnd(Point p) {}
}
