package main;


import java.awt.Toolkit;

public class Main {

	public static void main(String[] args) {
		
		
		Frame frame = new Frame();
		
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		frame.makestrat();
		
		
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
