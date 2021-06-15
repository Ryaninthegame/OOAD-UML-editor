package mode;

import java.awt.Point;

import java.awt.event.MouseEvent;
import java.util.List;
import shape.baseShape;

public class selectMode extends mode {
	private List<baseShape> setShape;
	private Point mousePoint = null;
	private String judgeInside = null;
	private baseShape selectedLine = null;
	
	public void mousePressed(MouseEvent e) {
		mousePoint = e.getPoint();
		setShape = canvas.getShapeList();
		canvas.resetSelectedArea(); 
		
		for (int i=setShape.size()-1; i>=0; i--) {
			baseShape tempShape = setShape.get(i);
			// inside �� basicLine / basicObj / groupObj ��@
			judgeInside = tempShape.insideState(e.getPoint());
			if (judgeInside != null) {
				canvas.selectedObj = tempShape;
				break;
			}
		}
		canvas.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		int moveX = e.getX() - mousePoint.x;
		int moveY = e.getY() - mousePoint.y;
		// �]�w����Q group �᪺�즲�欰
		if (canvas.selectedObj != null) {
			if (judgeInside == "insideLine") {
				selectedLine = canvas.selectedObj;
				selectedLine.resetStartEnd(e.getPoint());
			}
			else 
				canvas.selectedObj.resetPosition(moveX, moveY);
			mousePoint.x = e.getX();
			mousePoint.y = e.getY();
		}
		// �]�w group area ���ϰ�
		else {
			if ((e.getX()>mousePoint.x) && (e.getY()>mousePoint.y)) // ���W��k�U
				canvas.selectedArea.setBounds(mousePoint.x, mousePoint.y, Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()<mousePoint.x) && (e.getY()<mousePoint.y)) // �k�U�쥪�W
				canvas.selectedArea.setBounds(e.getX(), e.getY(), Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()>mousePoint.x) && (e.getY()<mousePoint.y)) // ���U��k�W
				canvas.selectedArea.setBounds(mousePoint.x, e.getY(), Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()<mousePoint.x) && (e.getY()>mousePoint.y)) // �k�W�쥪�U
				canvas.selectedArea.setBounds(e.getX(), mousePoint.y, Math.abs(moveX), Math.abs(moveY));
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (canvas.selectedObj != null) {
			if (judgeInside == "insideLine") {
				selectedLine = canvas.selectedObj;
				// �p�G���s�즲�w�s�u���u���A�P�_�O�_�n���s�s����s�� port
				Point mousePoint = e.getPoint();
				for (int i=0; i<setShape.size(); i++) {
					baseShape tempShape = setShape.get(i);
					int portIndex;
					// inside �� basicLine / basicObj / groupObj ��@
					String judgeInside = tempShape.insideState(mousePoint);
					if (judgeInside != null && judgeInside != "insideLine") {
						// ���P�� group �� object ���s�s�u
						if (judgeInside == "insideGroup") {
							tempShape = tempShape.getSelectedShape();
							portIndex = Integer.parseInt(tempShape.insideState(mousePoint));		
						}
						else
							portIndex = Integer.parseInt(judgeInside);
						selectedLine.resetPort(tempShape.getPort(portIndex), selectedLine);
						selectedLine.resetPosition();
					}
				}
			}
		}
		else 
			canvas.selectedArea.setSize(Math.abs(e.getX()-mousePoint.x), Math.abs(e.getY()-mousePoint.y));
		canvas.repaint();
	}
}
