package mode;

import java.awt.event.MouseEvent;
import shape.baseShape;
import shape.classObject;
import shape.usecaseObject;

public class objectMode extends mode{
	private String objType = null;
	
	public objectMode(String objType) {
		this.objType = objType;
	}
	
	public void mousePressed(MouseEvent e) {
		if(objType=="class") {
			baseShape tempObj = new classObject(e.getX(), e.getY());
			canvas.addShape(tempObj);
			canvas.repaint();
		}
		else if(objType=="usecase") {
			baseShape tempObj = new usecaseObject(e.getX(), e.getY());
			canvas.addShape(tempObj);
			canvas.repaint();
		}
	}
}

