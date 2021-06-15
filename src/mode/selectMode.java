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
			// inside 由 basicLine / basicObj / groupObj 實作
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
		// 設定物件被 group 後的拖曳行為
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
		// 設定 group area 的區域
		else {
			if ((e.getX()>mousePoint.x) && (e.getY()>mousePoint.y)) // 左上到右下
				canvas.selectedArea.setBounds(mousePoint.x, mousePoint.y, Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()<mousePoint.x) && (e.getY()<mousePoint.y)) // 右下到左上
				canvas.selectedArea.setBounds(e.getX(), e.getY(), Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()>mousePoint.x) && (e.getY()<mousePoint.y)) // 左下到右上
				canvas.selectedArea.setBounds(mousePoint.x, e.getY(), Math.abs(moveX), Math.abs(moveY));
			else if ((e.getX()<mousePoint.x) && (e.getY()>mousePoint.y)) // 右上到左下
				canvas.selectedArea.setBounds(e.getX(), mousePoint.y, Math.abs(moveX), Math.abs(moveY));
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (canvas.selectedObj != null) {
			if (judgeInside == "insideLine") {
				selectedLine = canvas.selectedObj;
				// 如果重新拖曳已連線的線條，判斷是否要重新連接到新的 port
				Point mousePoint = e.getPoint();
				for (int i=0; i<setShape.size(); i++) {
					baseShape tempShape = setShape.get(i);
					int portIndex;
					// inside 由 basicLine / basicObj / groupObj 實作
					String judgeInside = tempShape.insideState(mousePoint);
					if (judgeInside != null && judgeInside != "insideLine") {
						// 不同的 group 的 object 重新連線
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
