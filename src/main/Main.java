package main;


import java.awt.Toolkit;

public class Main {
	
	public static Frame frame;

	public static void main(String[] args) {
		
		
		frame = new Frame();
		
		frame.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setUndecorated(false);
		frame.setVisible(true);
		
		frame.makestrat();
		
		World.load();
		
		
		long lastFrame = System.currentTimeMillis();
		
		while(true) {
			
			long thisFrame = System.currentTimeMillis();
			
			float timesincelastframe = (float) ((thisFrame - lastFrame) / 1000.0);
			lastFrame = thisFrame;
			
			frame.update(timesincelastframe);
			
			frame.repaint();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
				
				
				e.printStackTrace();
			}
		}
		
	}
	
}
