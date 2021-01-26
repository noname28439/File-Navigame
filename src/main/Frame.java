package main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.tools.DocumentationTool.Location;

public class Frame extends JFrame{
	
	private BufferStrategy strat;
	
	

	
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
		World.draw(g);

		
		
	}
	
	public void update(float tslf) {
		if(Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		
		World.update();
		
	}
	
}
