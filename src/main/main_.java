package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class main_ extends JFrame{
	private int windowWidth;
	private int windowHeight;
	private Canvas canvas;
	private tool toolbar = new tool();
	private menu menubar = new menu();
	
	public main_() {
		canvas = Canvas.getInstance();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(menubar, BorderLayout.NORTH);
		getContentPane().add(toolbar, BorderLayout.WEST);
		getContentPane().add(canvas, BorderLayout.CENTER);
		this.setTitle("OOAD Mid-term Project");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.windowWidth = canvas.returnWindowWidth();
		this.windowHeight = canvas.returnWindowHeight();
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new main_();
	}
}
