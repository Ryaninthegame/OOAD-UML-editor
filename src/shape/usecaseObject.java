package shape;

import java.awt.Font;
import java.awt.Graphics;

public class usecaseObject extends basicObject{
	public usecaseObject(int x1, int y1) {
		this.height = 90;
		this.width = 120;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
		createPorts();
	}
	
	public void draw(Graphics g) {
		stringWidth = g.getFontMetrics(new Font(Font.DIALOG, Font.BOLD, 14)).stringWidth(objectName);
		if(stringWidth>defaultUseCaseWidth) {
			this.width = stringWidth+15;
			this.x2 = x1 + width;
			this.y2 = y1 + height;
		} 
		else {
			this.height = 90;
			this.width = 120;
			this.x2 = x1 + width;
			this.y2 = y1 + height;
		}
		double space = (Math.abs(x1-x2)-stringWidth)/2;
		g.drawOval(x1, y1, width, height);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 14));	
		g.drawString(objectName, x1+(int)space, y1+50);
	}
}
