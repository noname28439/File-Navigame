package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;

public class World {

	public static int floorheight = 100;
	
	public static ArrayList<FileBox> szeneFiles = new ArrayList<>();
	
	public static boolean szeneContainsFileNamed(String searchName) {
		boolean isInSzene = false;
		for(FileBox cf : szeneFiles) {
			if(cf.name.equalsIgnoreCase(searchName))
				isInSzene = true;
		}
		return isInSzene;
	}
	
	public static void load() {
		ArrayList<File> env = FileManager.getFiles();
		
		for(int i = 0; i<env.size();i++) {
			szeneFiles.add(new FileBox(env.get(i), new Random().nextInt(Main.frame.getWidth()), new Random().nextInt(Main.frame.getHeight())));
		}
		szeneFiles.add(new FileBox(new File(".."), new Random().nextInt(Main.frame.getWidth()-100), new Random().nextInt(Main.frame.getHeight())));
	}
	
	public static void changeDirectory(String change) {
		FileManager.currentFilePath+=change;
		while (szeneFiles.size()>0) {
			szeneFiles.remove(0);
			
		}
		ArrayList<File> env = FileManager.getFiles();
		for(int i = 0; i<env.size();i++) {
			szeneFiles.add(new FileBox(env.get(i), new Random().nextInt(Main.frame.getWidth()-100), new Random().nextInt(Main.frame.getHeight())));
		}
		szeneFiles.add(new FileBox(new File(".."), new Random().nextInt(Main.frame.getWidth()-100), new Random().nextInt(Main.frame.getHeight())));
	}
	
	
	
	public static void draw(Graphics g) {
		
		g.setColor(new Color(102, 204, 255));
		g.fillRect(0, 0, Main.frame.getWidth(), Main.frame.getHeight());
		
		g.setColor(new Color(0, 153, 51));
		g.fillRect(0, Main.frame.getHeight()-floorheight, Main.frame.getWidth(), floorheight);
		
		for(int i = 0; i<szeneFiles.size();i++) {
			szeneFiles.get(i).draw(g);
		}
		if(Player.grabbed!=null)
			Player.grabbed.draw(g);
		
		
		Player.draw(g);
		
		g.setColor(Color.black);
		g.drawString("Dir: "+FileManager.currentFilePath + " ==> "+szeneFiles.toString(), 100, 100);
		
	}
	
	public static void update() {
		
		
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_F5)) {
			Keyboard.keys[KeyEvent.VK_F5]=false;
			changeDirectory("");
		}
			
		
		Player.update();
		
	}
	
}
