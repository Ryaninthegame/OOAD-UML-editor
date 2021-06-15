package shape;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Port extends Rectangle{
	private List<baseShape> setLine = new ArrayList<baseShape>(); 
	
	public void addLine(baseShape line) { setLine.add(line); }
	public void removeLine(baseShape line) { setLine.remove(line); }
	
	public void resetLines() {
		for(int i=0; i<setLine.size(); i++){
			baseShape line = setLine.get(i);
			line.resetPosition();
		}
	}
	
	public void setUpPort(int center_x, int center_y, int offset) {
		int x = center_x - offset;
		int y = center_y - offset;
		int width = offset * 2;
		int height = offset * 2;
		setBounds(x, y, width, height);
	}
}
