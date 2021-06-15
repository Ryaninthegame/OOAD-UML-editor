package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.swing.JPanel;
import mode.lineMode;
import mode.mode;
import shape.groupObject;
import shape.baseShape;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	public mode currentMode = null;
	private static Canvas instance = null; 
	private EventListener listener = null;
	// shapeInCanvas 裡放置各個用 baseShape 實作出來的的物件, 
	// 由於父類別可以指向子類別, 且父類別的 function 都由子類別實作出來了, 
	// 故 call 父類別的 abstract function 可直接呼叫到子類別中已實作好的 function
	private List<baseShape> shapeInCanvas = new ArrayList<baseShape>();
	private List<baseShape> tempshapeInCanvas = new ArrayList<baseShape>();
	public baseShape dragLine = null;
	public baseShape selectedObj = null;
	public Rectangle selectedArea = new Rectangle();
	public static lineMode showPortMode;
	public Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	public int windowWidth = (int) Math.floor(screen.width/1.05); // 1828
	public int windowHeight = (int) Math.floor(screen.height/1.1); // 981
	
	public void addShape(baseShape shape) { shapeInCanvas.add(shape); } 
	public List<baseShape> getShapeList() { return shapeInCanvas; }
	public baseShape getSelectedObject() { return selectedObj; }
	public void setShowPortFlag_(boolean flag) { showPortMode.setShowPortFlag(flag); }
	public int returnWindowWidth() { return windowWidth; }
	public int returnWindowHeight() { return windowHeight; }
	
	// 讓多個 class 共用同個 canvas
	// 把 constructor 宣告成私有的，只有自己 class 裡的 method 才能呼叫。
	private Canvas() {}
	public static synchronized Canvas getInstance() {
		if (instance == null) {
			instance = new Canvas();
			showPortMode = new lineMode(null);
		}
		return instance;
	}

	public void setCurrentMode() {
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}
	
	public void resetSelectedArea() {
		if(selectedObj != null){
			selectedObj.resetSelectedShape();   
			selectedObj = null;
		}
		selectedArea.setBounds(0, 0, 0, 0);
	}
	
	public void groupShape() {
		tempshapeInCanvas.addAll(shapeInCanvas);
		groupObject group = new groupObject();
		for (int i=0; i<shapeInCanvas.size(); i++) {
			baseShape tempshape = shapeInCanvas.get(i);
			if (tempshape.groupSelected) {
				group.addShapes(tempshape);
				tempshapeInCanvas.remove(tempshape);
			}
		}
		shapeInCanvas = tempshapeInCanvas;
		tempshapeInCanvas = new ArrayList<baseShape>();
		group.setGroupFlag(true);
		group.setboundArea();
		shapeInCanvas.add(group);
	}
	
	public void unGroup() {
		if(selectedObj!=null && selectedObj.returnGroupFlag()) {
			List<baseShape> groupShapes = selectedObj.getShapes();
			for(int i=0; i<groupShapes.size(); i++) {
				baseShape tempShape = groupShapes.get(i);
				tempShape.groupSelected = false;
				shapeInCanvas.add(tempShape);
			}
			shapeInCanvas.remove(selectedObj);
			selectedObj.setGroupFlag(false);
		}
		repaint();
	}

	public void changeObjName(String name) {
		if(selectedObj != null){
			selectedObj.setName(name);
			selectedObj = null;
		}
	}
	
	public void deleteObject() {
		tempshapeInCanvas.addAll(shapeInCanvas);
		for(int i=0; i<shapeInCanvas.size(); i++) {
			baseShape tempShape = shapeInCanvas.get(i);
			if(tempShape==selectedObj) {
				if(tempShape.getPortNum()==4) {
					for(int j=0; j<shapeInCanvas.size(); j++) {
						baseShape tempLine = shapeInCanvas.get(j);
						if(tempLine.getPortNum()==2) {
							for(int L=0; L<2; L++) {
								for(int k=0; k<4; k++) {
									if(tempShape.getPort(k).getX()==tempLine.getPort(L).getX() && tempShape.getPort(k).getY()==tempLine.getPort(L).getY()) {
										tempshapeInCanvas.remove(tempLine);
									}
								}
							}
						}
					}
				}
				tempshapeInCanvas.remove(tempShape);
			}
			else if(tempShape.groupSelected) {
				tempshapeInCanvas.remove(tempShape);
			}
		}
		shapeInCanvas = tempshapeInCanvas;
		tempshapeInCanvas = new ArrayList<baseShape>();
		resetSelectedArea();
		repaint();
	}
	
	public void resetCanvas() {
		tempshapeInCanvas.addAll(shapeInCanvas);
		for(int i=0; i<shapeInCanvas.size(); i++) {
			baseShape tempShape = shapeInCanvas.get(i);
			tempshapeInCanvas.remove(tempShape);
		}
		shapeInCanvas = tempshapeInCanvas;
		tempshapeInCanvas = new ArrayList<baseShape>();
		resetSelectedArea();
		repaint();
	}
	
	public void paint(Graphics g) {
		Dimension dimension = getSize();
		g.setColor(Color.BLACK);
		// g.fillRect(x座標, y座標, 寬, 高);
		g.fillRect(0, 0, dimension.width, dimension.height);
		g.setColor(Color.white);
		//Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(1));
		
		boolean check;
		/* paint all shape objects */
		for (int i=shapeInCanvas.size()-1; i>=0; i--) {
			baseShape tempShape = shapeInCanvas.get(i);
			Point upperleft = new Point(tempShape.getX1(), tempShape.getY1());
			Point lowerright = new Point(tempShape.getX2(), tempShape.getY2());
			if (selectedArea.contains(upperleft) && selectedArea.contains(lowerright))
				check=true;
			else
				check=false;
			tempShape.draw(g);
			tempShape.groupSelected = false;
			if (!selectedArea.isEmpty() && check) 
				tempShape.groupSelected = true;
		}
		
		for(int i=shapeInCanvas.size()-1; i>=0; i--) {
			baseShape tempShape = shapeInCanvas.get(i);
			if(tempShape.groupSelected) {
				tempShape.show(g);
			}
		}

		if (dragLine != null) { dragLine.draw(g); }
		if (this.selectedObj != null) { selectedObj.show(g); }
		
		// 被點選的藍色區域
		if (!selectedArea.isEmpty()) {
			int transparency = 50; 
			g.setColor(new Color(30, 144, 255, transparency));
			g.fillRect(selectedArea.x, selectedArea.y, selectedArea.width, selectedArea.height);
			g.drawRect(selectedArea.x, selectedArea.y, selectedArea.width, selectedArea.height);
		}
		
		if(showPortMode.returnMode()) {
			for(int i=0; i<shapeInCanvas.size(); i++) {
				baseShape tempShape = shapeInCanvas.get(i);
				tempShape.showPort(g);
			}
		}
	}
}
