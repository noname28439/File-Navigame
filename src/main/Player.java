package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.plaf.FileChooserUI;
import javax.transaction.xa.Xid;
import javax.xml.xpath.XPath;

import gfx.ImageLoader;

public class Player {

	public static int x = 100, y = 100;
	public static float xspeed = 0, yspeed = 0;
	public static int size = 50*2;
	static final int playerSpeed = 3;
	
	static int maxXspeed = 10;
	
	static BufferedImage skin = ImageLoader.loadImage("Player");
	
	public static FileBox grabbed = null;
	
	
	public static void draw(Graphics g) {
		g.setColor(Color.BLACK);
		//g.drawRect( x, y, size, size);
		g.drawImage(skin, x, y, size, size,  null);
		
	}
	
	
	
	static boolean isOnFloor() {
		return y+size>Main.frame.getHeight()-(World.floorheight*0.8);
	}
	
	static boolean isAtLeftWall() {
		return x<0;
	}
	static boolean isAtRightWall() {
		return x+size>Main.frame.getWidth();
	}
	
	static boolean isAtWall() {
		return isAtLeftWall()||isAtRightWall();
	}
	
	static boolean overspeeded = false;
	
	static int lastx = 0, lasty = 0;
	
	static boolean onBox = false;
	
	public static void update() {
		x+=xspeed;
		y+=yspeed;
		
		if(grabbed!=null) {
			//System.err.println(((size-grabbed.drawSize)/2));
			grabbed.x = x+((size-grabbed.drawSize)/2);
			grabbed.y = y+size/2;
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_SHIFT)) {
			if(grabbed!=null) {
				if(!World.szeneContainsFileNamed(grabbed.name)) {
				String createPath = FileManager.currentFilePath+"\\"+grabbed.name;
				try {
					Files.copy(Paths.get(grabbed.file.getAbsolutePath()), Paths.get(createPath));
				} catch (IOException | java.lang.NullPointerException e) {e.printStackTrace();}
				World.szeneFiles.add(new FileBox(new File(createPath), grabbed.x, grabbed.y));
				grabbed=null;
				}else {
					grabbed = null;
				}
			}
		}
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
			Keyboard.keys[KeyEvent.VK_ENTER] = false;
			if(grabbed!=null) {
				String newName = new JOptionPane().showInputDialog("Filename: ");
				grabbed.rename(newName);
			}	
		}
		
		
		
		boolean onGround = false;
		
		
		if(y+size>Main.frame.getHeight()-(World.floorheight*0.8))
			onGround = true;
		
		if(!overspeeded) {
			if(xspeed>maxXspeed)
				xspeed=maxXspeed;
			
			if(xspeed<-maxXspeed)
				xspeed=-maxXspeed;
		}
		
		
		
		
		if(onGround) {
			if(Keyboard.isKeyPressed(KeyEvent.VK_A)) {
				xspeed-=playerSpeed;
			}
			if(Keyboard.isKeyPressed(KeyEvent.VK_D)) {
				xspeed+=playerSpeed;
			}
		}else {
			if(Keyboard.isKeyPressed(KeyEvent.VK_A)) {
				xspeed-=0.5;
			}
			if(Keyboard.isKeyPressed(KeyEvent.VK_D)) {
				xspeed+=0.5;
			}
		}
		
		
		if(isAtLeftWall()) {
			x=0;
			xspeed=0;
			if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
				Keyboard.keys[KeyEvent.VK_SPACE] = false;
				yspeed-=20;
				xspeed+=30;
				overspeeded=true;
			}
		}
			
		if(isAtRightWall()) {
			x=Main.frame.getWidth()-size;
			xspeed=0;
			if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
				Keyboard.keys[KeyEvent.VK_SPACE] = false;
				yspeed-=20;
				xspeed-=30;
				overspeeded=true;
			}
		}
			
		

		
		if(onGround) {
			//y=Main.frame.getHeight()-(int)(World.floorheight*0.8)-size;
			y=lasty;
			if(yspeed<10)
				yspeed=0;
			else
				yspeed=-(int)(yspeed*0.5);
				
				
			if(!Keyboard.isKeyPressed(KeyEvent.VK_A)&&!Keyboard.isKeyPressed(KeyEvent.VK_D))
				xspeed*=0.3;
			overspeeded=false;
			if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
				Keyboard.keys[KeyEvent.VK_SPACE] = false;
				yspeed-=30;
			}
		}else {
			yspeed++;
			xspeed*=0.95;
		}
		
		onBox=false;
		for(FileBox cb : World.szeneFiles) {
			if(Collision.rectToRect(x, y, size, size, cb.x, cb.y, cb.drawSize, cb.drawSize)) {
//				x=lastx;
//				y=lasty;
//				onBox = true;
			}
		}
		
		
		lastx = x;
		lasty = y;
		
		
		
	}
	
	
}
