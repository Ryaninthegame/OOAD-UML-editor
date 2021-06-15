package main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import mode.mode;
import mode.lineMode;
import mode.objectMode;
import mode.selectMode;

@SuppressWarnings("serial")
public class tool extends JPanel{
	private int toolNum = 6;
	private JButton holdBtn = null;
	private Canvas canvas;

	public tool() {
		canvas = Canvas.getInstance();
		Border borderLine = BorderFactory.createLineBorder(Color.white);
		setLayout(new GridLayout(toolNum, 0, 0, 0));
		this.setBackground(Color.white);
		this.setBorder(borderLine);
		
		ImageIcon selectIcon = new ImageIcon(".\\image\\select.png");
		ImageIcon assIcon = new ImageIcon(".\\image\\association line.png");
		ImageIcon genIcon = new ImageIcon(".\\image\\generalization line.png");
		ImageIcon comIcon = new ImageIcon(".\\image\\composition line.png");
		ImageIcon classIcon = new ImageIcon(".\\image\\class.png");
		ImageIcon usecaseIcon = new ImageIcon(".\\image\\use case.png");
		ToolBtn selectBtn = new ToolBtn("select", "Select", selectIcon, new selectMode());
		ToolBtn assBtn = new ToolBtn("associate", "Association", assIcon, new lineMode("associate"));
		ToolBtn genBtn = new ToolBtn("general", "Generalization", genIcon, new lineMode("general"));
		ToolBtn comBtn = new ToolBtn("composite", "Composition", comIcon, new lineMode("composite"));
		ToolBtn classBtn = new ToolBtn("class", "Class", classIcon, new objectMode("class"));
		ToolBtn usecaseBtn = new ToolBtn("usecase", "Use case", usecaseIcon, new objectMode("usecase"));
		add(selectBtn);
		add(assBtn);
		add(genBtn);
		add(comBtn);
		add(classBtn);
		add(usecaseBtn);
	}
	
	private class ToolBtn extends JButton{
		mode ToolMode;
		public ToolBtn(String ToolName, String name, ImageIcon icon, mode ToolMode) {
			this.ToolMode = ToolMode;
			setToolTipText(ToolName);
			setIcon(icon);
			setText(name);
			setHorizontalTextPosition(JButton.CENTER);
			setVerticalTextPosition(JButton.BOTTOM);
			setForeground(Color.white);
			setFocusable(false);
			setBackground(Color.BLACK);
			setBorderPainted(false);
			addActionListener(new toolListener());
		}
		class toolListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(holdBtn != null)
					holdBtn.setBackground(Color.black);
				holdBtn = (JButton) e.getSource();
				holdBtn.setBackground(Color.GRAY);
				canvas.currentMode = ToolMode;
				canvas.setCurrentMode();
				canvas.resetSelectedArea();
				canvas.repaint();
			}
		}
	}
}
