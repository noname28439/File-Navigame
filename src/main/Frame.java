package main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.tools.DocumentationTool.Location;

public class Frame extends JFrame{
	
	private BufferStrategy strat;
	
	
	public static int floorheight = Toolkit.getDefaultToolkit().getScreenSize().height -50;
	
	public Frame() {
		super("Game");
		
		addKeyListener(new Keyboard());
		addMouseListener(new Keyboard());
		addMouseMotionListener(new Keyboard());
		
	
		
		
		
	}
	
	public void makestrat() {
		createBufferStrategy(2);
		
		strat = getBufferStrategy();
	}
	
	public void repaint() {
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}
	
	
	
	
	public void draw(Graphics g) {

		
		
		
	}
	
	public void update(float tslf) {
		
	}
	
}
