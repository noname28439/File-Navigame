package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import entities.Slime;
import gfx.FileImageManager;
import gfx.ImageLoader;

public class FileBox {

	public static BufferedImage break1 = ImageLoader.loadImage("Break_1");
	public static BufferedImage break2 = ImageLoader.loadImage("Break_2");
	public static BufferedImage break3 = ImageLoader.loadImage("Break_3");
	
	
	
	public File file;
	public int x;
	public int y;
	int size = 100;
	public int drawSize;
	public int damage = 0;
	String name = "";
	String extension = "";
	BufferedImage skin;
	
	public FileBox(File file, int x, int y) {
		this.file = file;
		this.x = x;
		this.y = y;	
		size = (int) file.length()/10+10;
		System.out.println(size);
		name = file.getName();
		System.out.println("Name: "+name);
		if(name.split("\\.").length>=2)
			extension = name.split("\\.")[name.split("\\.").length-1];
		
		System.out.println("Ext: "+extension);
		
		if(size>100) {
			drawSize=100;
		}else {
			drawSize=size;
		}
		if(drawSize<20)
			drawSize = 30;
		setSkin();
		
	}
	
	void setSkin() {
		if(file.isDirectory()) {
			if(file.getName().equalsIgnoreCase(".."))
				skin = FileImageManager.getFileImage("..");
			else
				skin = ImageLoader.loadImage("DirImage");
		}else
			if(!extension.equalsIgnoreCase(""))
				skin = FileImageManager.getFileImage(extension);
	}
	
	public void rename(String newName) {
		name = newName;
		if(name.split("\\.").length>=2)
			extension = name.split("\\.")[name.split("\\.").length-1];
		
		setSkin();
		
	}
	
	public void draw(Graphics g) {
		
		//x+=new Random().nextInt(3)-1;
		//y+=new Random().nextInt(3)-1;
		
		
		boolean mouseOver = Collision.rectToRect(x, y, drawSize, drawSize, Keyboard.getMousex(), Keyboard.getMousey(), 1, 1);
		boolean reachedByPlayer = Collision.circleToRect(Player.x, Player.y, 250, x, y, drawSize, drawSize);
		
		if(mouseOver&&Keyboard.isKeyPressed(KeyEvent.VK_C)&&reachedByPlayer) {
			Keyboard.keys[KeyEvent.VK_C] = false;
			if(!file.isDirectory())
				Player.grabbed = new FileBox(file, x, y);
		}
		
		if(mouseOver&&Keyboard.getButton()==2&&reachedByPlayer) {
			Keyboard.button=-1;
			if(!file.isDirectory())
				damage++;
		}
		
		if(file.isDirectory()) {
			g.setColor(new Color(204, 0, 255));
			if(mouseOver&&Keyboard.getButton()==1&&reachedByPlayer) {
				Keyboard.button=-1;
				World.changeDirectory("\\"+name);
			}	
		}else {
			g.setColor(new Color(255, 153, 0));
			if(mouseOver&&Keyboard.getButton()==1&&reachedByPlayer) {
				Keyboard.button=-1;
				
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(file.getName().toLowerCase().contains("filenavigame")) {
			g.drawImage(Slime.skin, x, y-(int)(drawSize*0.5),drawSize, drawSize, null);
		}
		
		g.drawImage(skin, x, y, drawSize, drawSize, null);
		//g.fillRect(x, y, drawSize, drawSize);
		if(mouseOver&&reachedByPlayer) {
			g.setColor(Color.RED);
			g.drawRect(x, y, drawSize, drawSize);
		}else {
			g.setColor(Color.black);
			//g.drawRect(x, y, drawSize, drawSize);
		}
			
		
			
		
		if(World.drawNames)
		if(size>100)
			g.drawString(name+"("+size+")", x, y);
		else
			g.drawString(name, x, y);
		
		
		switch (damage) {
			case 1:
				g.drawImage(break1, x, y,drawSize, drawSize,  null);
				break;
			case 2:
				g.drawImage(break2, x, y,drawSize, drawSize, null);
				break;
			case 3:
				g.drawImage(break3, x, y,drawSize, drawSize, null);
				break;
			case 4:
				file.delete();
				World.szeneFiles.remove(this);
				break;
	
			default:
				break;
		}
		
		
		
		
	}
	
	
	
}
