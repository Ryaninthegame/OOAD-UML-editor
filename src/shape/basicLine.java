package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.List;

import main.Canvas;

public abstract class basicLine extends baseShape{
	private String selectedFlag = null;
	protected Port[] arrayPort = new Port[2];
	protected Canvas canvas = Canvas.getInstance(); 
	
	public int getPortNum() { return arrayPort.length; }
	public Port getPort(int portIndex) { return arrayPort[portIndex]; }
	
	public void setPorts(Port port_1, Port port_2) {
		arrayPort[0] = port_1;
		arrayPort[1] = port_2;
	}
	
	// class 與 usecase 線條顏色
	public void show(Graphics g) {
		g.setColor(Color.red); 
		draw(g);
	}
	
	public void resetPosition(){
		x1 = (int) arrayPort[0].getCenterX(); 
		y1 = (int) arrayPort[0].getCenterY();
		x2 = (int) arrayPort[1].getCenterX();
		y2 = (int) arrayPort[1].getCenterY();
	}
	
	public void resetStartEnd(Point p) { 
		canvas.setShowPortFlag_(true);
		if(selectedFlag == "tail"){
			x1 = p.x;
			y1 = p.y;
		}
		else if(selectedFlag == "head") {
			x2 = p.x;
			y2 = p.y;
		}
	}
	
	// 判斷滑鼠點擊下去的座標是否在 line 裡面, 若是的話則判斷滑鼠點擊的座標距離 tail 比較近還是 head 比較近
	public String insideState(Point mousePoint) {
		double pickDistance = 5.0;
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		double pointToLine = line.ptLineDist(mousePoint.getX(), mousePoint.getY());
		if(pointToLine <= pickDistance) {
			double distTail = Math.sqrt(Math.pow((mousePoint.x-x1), 2) + Math.pow((mousePoint.y-y1), 2));
			double distHead = Math.sqrt(Math.pow((mousePoint.x-x2), 2) + Math.pow((mousePoint.y-y2), 2));
			if(distTail < distHead) 
				selectedFlag = "tail";
			else
				selectedFlag = "head";
			return "insideLine";
		}
		else
			return null;
	}
	
	// 重新拖曳 obj 與 obj 之間的線條
	public void resetPort(Port port, baseShape line) { 
		canvas.setShowPortFlag_(false);
		port.addLine(line);
		if(selectedFlag == "tail"){
			arrayPort[0].removeLine(line);
			arrayPort[0] = port;
		}
		else if(selectedFlag == "head"){
			arrayPort[1].removeLine(line);
			arrayPort[1] = port;
		}
	}
	
	@Override public void resetPosition(int moveX, int moveY) {}
	@Override public void resetSelectedShape() {}
	@Override public baseShape getSelectedShape() { return null; }
	@Override public void setName(String name) {}
	@Override public void showPort(Graphics g) {}
	@Override public boolean returnGroupFlag() {return false;}
	@Override public void setGroupFlag(boolean flag) {}
	@Override public List<baseShape> getShapes() { return null;	}
}
