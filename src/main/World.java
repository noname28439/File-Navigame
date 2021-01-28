package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

import entities.Entity;
import entities.Shuricane;
import entities.Slime;

import java.util.ArrayList;

public class World {

	public static int floorheight = 100;
	
	public static ArrayList<FileBox> szeneFiles = new ArrayList<>();
	
	public static ArrayList<Entity> entities = new ArrayList<>();
	
	public static boolean drawNames = true;
	
	public static FileBox GetFileBoxByFileNamed(String searchName) {
		for(FileBox cf : szeneFiles) {
			if(cf.name.equalsIgnoreCase(searchName))
				return cf;
		}
		return null;
	}
	
	public static void load() {
		ArrayList<File> env = FileManager.getFiles();
		
		for(int i = 0; i<env.size();i++) {
			int spawnx = new Random().nextInt(Main.frame.getWidth());
			int spawny =  new Random().nextInt(Main.frame.getHeight());
			
			System.err.println(env.get(i).getName().toLowerCase());
			if(env.get(i).getName().toLowerCase().contains("filenavigame")) {
				entities.add(new Slime(spawnx, spawny));
			}
			szeneFiles.add(new FileBox(env.get(i), spawnx,spawny));
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
			int spawnx = new Random().nextInt(Main.frame.getWidth());
			int spawny =  new Random().nextInt(Main.frame.getHeight());
			
			if(env.get(i).getName().toLowerCase().contains("filenavigame")) {
				for(int ii = 0; ii<new Random().nextInt(3)+1;ii++)
					entities.add(new Slime(spawnx, spawny));
			}
			szeneFiles.add(new FileBox(env.get(i), spawnx,spawny));
			if(new Random().nextInt(20)==0) {		//Random: ~500
				entities.add(new Shuricane( new Random().nextInt(Main.frame.getWidth()-100), new Random().nextInt(Main.frame.getHeight()), true));
			}
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
		
		for(int i = 0; i<entities.size();i++) {
			entities.get(i).draw(g);
		}
		
		if(Player.grabbed!=null)
			Player.grabbed.draw(g);
		
		
		Player.draw(g);
		
		//g.setColor(Color.black);
		//g.drawString("Dir: "+FileManager.currentFilePath, 100, 100);
		
	}
	
	public static void update() {
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_N)) {
			Keyboard.keys[KeyEvent.VK_N]=false;
			drawNames=!drawNames;
		}
		
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_F5)) {
			Keyboard.keys[KeyEvent.VK_F5]=false;
			changeDirectory("");
		}
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_F2)) {
			Keyboard.keys[KeyEvent.VK_F2]=false;
			entities.add(new Slime(100, 100));
		}
			
		
		Player.update();
		
		for(int i = 0; i<entities.size();i++) {
			entities.get(i).update();
		}
		
	}
	
}
