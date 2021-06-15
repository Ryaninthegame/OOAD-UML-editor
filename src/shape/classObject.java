package shape;

import java.awt.Font;
import java.awt.Graphics;

public class classObject extends basicObject{
	public classObject(int x1, int y1) {
		this.height = 120;
		this.width = 100;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
		createPorts();
	}
	
	public void draw(Graphics g) {
		int portion = height / 3;
		stringWidth = g.getFontMetrics(new Font(Font.DIALOG, Font.BOLD, 14)).stringWidth(objectName);
		if(stringWidth>defaultClassWidth) {
			this.width = stringWidth+15;
			this.x2 = x1 + width;
			this.y2 = y1 + height;
		}
		else {
			this.height = 120;
			this.width = 100;
			this.x2 = x1 + width;
			this.y2 = y1 + height;
		}
		double space = (Math.abs(x1-x2)-stringWidth)/2;
		g.drawRect(x1, y1, width, height);
		g.drawLine(x1, y1+portion, x2, y1+portion);
		g.drawLine(x1, y1+portion*2, x2, y1+portion*2);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 14));	
		g.drawString(objectName, x1+(int)space, y1+25);
		canvas.repaint();
	}
}
