package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class menu extends JMenuBar {
	private Canvas canvas = Canvas.getInstance();
	private JMenu menu;
	private	JMenuItem menuItem;
	private int screenWidth = 1653;
	private int screenHeight = 865;
	private String path = "C:\\Users\\DART\\Desktop\\";
	private String fileName;
	
	public menu() {
		menu = new JMenu("File");
		add(menu);
		
		menuItem = new JMenuItem("New");
		menu.add(menuItem);
		menuItem.addActionListener(new resetCanvasListener());
		
		menuItem = new JMenuItem("Save");
		menu.add(menuItem);
		menuItem.addActionListener(new saveListener());

		// -----------------------------------------------------------------------
		
		menu = new JMenu("Edit");
		add(menu);
		
		menuItem = new JMenuItem("Change object name");
		menu.add(menuItem);
		menuItem.addActionListener(new changeNameListener());
		
		menuItem = new JMenuItem("Group");
		menu.add(menuItem);
		menuItem.addActionListener(new groupObjectListener());
		
		menuItem = new JMenuItem("Ungroup");
		menu.add(menuItem);
		menuItem.addActionListener(new ungroupObjectListener());
		
		menuItem = new JMenuItem("Delete");
		menu.add(menuItem);
		menuItem.addActionListener(new deleteObjectListener());
	}
	
	private void changeNameForm() {
		JFrame inputTextFrame = new JFrame("Change Object Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));
		
		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JTextField Text =  new JTextField();
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton confirm = new JButton("OK");
		panel.add(confirm);
		
		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);
		
		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.changeObjName(Text.getText());
				inputTextFrame.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});
	}
	
	public void savePic() {
		JFrame inputTextFrame = new JFrame("File Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));
		
		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JTextField Text =  new JTextField();
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton confirm = new JButton("OK");
		panel.add(confirm);
		
		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);
		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileName = Text.getText();
				savaFunction();
				inputTextFrame.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});
    }
	
	public void savaFunction() {
		File file = new File(path + fileName + ".png");
		try {
	        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_BGR);
	        canvas.printAll(image.getGraphics());
	        ImageIO.write(image, "png", file);
	    } 
		catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	class ungroupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.unGroup();
		}
	}

	class groupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.groupShape();
		}
	}
	
	class deleteObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.deleteObject();
		}
	}
	
	class resetCanvasListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			canvas.resetCanvas();
		} 
	}
	
	class changeNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeNameForm();
		}
	}
	
	class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			savePic();
		}
	}
}

