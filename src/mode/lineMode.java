package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import shape.basicLine;
import shape.associationLine;
import shape.baseShape;
import shape.compositionLine;
import shape.generalizationLine;

public class lineMode extends mode {
	static boolean showPortFlag = false;
	private Point tailPoint;
	private Point headPoint;
	private String lineType = null;
	private List<baseShape> setShape;
	private int tailPort = -1, headPort = -1;
	private baseShape tailShape = null, headShape = null;

	public lineMode(String lineType) { this.lineType = lineType; }
	public void setShowPortFlag(boolean flag) { showPortFlag = flag; }
	public boolean returnMode() { return showPortFlag; }
	
	public void mousePressed(MouseEvent e) {
		setShape = canvas.getShapeList();
		tailPoint = searchObj(e.getPoint(), "tail");
		showPortFlag = true;
	}
	
	public void mouseDragged(MouseEvent e) {
		if (tailPoint != null) {
			basicLine line;
			switch(lineType) {
			case "associate":
				line = new associationLine(tailPoint.x, tailPoint.y, e.getX(), e.getY());
				canvas.dragLine = line;
				canvas.repaint();
				break;
			case "general":
				line = new generalizationLine(tailPoint.x, tailPoint.y, e.getX(), e.getY());
				canvas.dragLine = line;
				canvas.repaint();
				break;
			case "composite":
				line = new compositionLine(tailPoint.x, tailPoint.y, e.getX(), e.getY());
				canvas.dragLine = line;
				canvas.repaint();
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		showPortFlag = false;
		if (tailPoint != null) {
			headPoint = searchObj(e.getPoint(), "head");
			if (headPoint != null && headPoint.getX()!=tailPoint.getX() && headPoint.getY()!=tailPoint.getY()) {
				basicLine line = null;
				switch(lineType) {
				case "associate":
					line = new associationLine(tailPoint.x, tailPoint.y, headPoint.x, headPoint.y);
					break;
				case "general":
					line = new generalizationLine(tailPoint.x, tailPoint.y, headPoint.x, headPoint.y);
					break;
				case "composite":
					line = new compositionLine(tailPoint.x, tailPoint.y, headPoint.x, headPoint.y);
					break;
				}
				canvas.addShape(line);
				line.setPorts(tailShape.getPort(tailPort), headShape.getPort(headPort));
				tailShape.getPort(tailPort).addLine(line);
				headShape.getPort(headPort).addLine(line);
			}
			canvas.repaint();
			canvas.dragLine = null;
			tailPoint = null;
		}
	}

	// 判斷連接到哪個 class/usecase
	private Point searchObj(Point mousePoint, String target) {
		for (int i=0; i<setShape.size(); i++) {
			baseShape tempShape = setShape.get(i);
			int portIndex;
			String judgeInside = tempShape.insideState(mousePoint);
			if (judgeInside != null && judgeInside != "insideLine") {
				if(judgeInside == "insideGroup"){  
					tempShape = tempShape.getSelectedShape();
					portIndex = Integer.parseInt(tempShape.insideState(mousePoint));
				}
				else {
					portIndex = Integer.parseInt(judgeInside);
				}
				switch (target) {
				case "tail":
					tailShape = tempShape;
					tailPort = portIndex;
					break;
				case "head":
					headShape = tempShape;
					headPort = portIndex;
					break;
				}
				Point portLocation = new Point();
				portLocation.setLocation(tempShape.getPort(portIndex).getCenterX(), tempShape.getPort(portIndex).getCenterY());
				return portLocation;
			}
		}
		return null;
	}
}
