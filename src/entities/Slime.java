package entities;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.plaf.FileChooserUI;
import javax.transaction.xa.Xid;
import javax.xml.xpath.XPath;

import gfx.ImageLoader;
import gfx.ImageRotator;
import main.Collision;
import main.FileBox;
import main.Keyboard;
import main.Main;
import main.Player;
import main.World;

public class Slime extends Entity{

	public int x = 100, y = 100;
	public float xspeed = 0, yspeed = 0;
	public int size = 50*2;
	final int entitySpeed = 3;
	
	static int maxXspeed = 10;
	
	
	public static BufferedImage skin = ImageLoader.loadImage("Entity_SlimeStacked_");
	static ImageRotator skinRotator = new ImageRotator(skin);
	
	
	public Slime(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public void draw(Graphics g) {
		if(rotation==0) {
			g.drawImage(skin, x, y, size, size,  null);
		}else {
			g.drawImage(skinRotator.getRotatedImage(rotation), x, y, size, size,  null);
		}
		
	}
	
	
	
	boolean isOnFloor() {
		return y+size>Main.frame.getHeight()-(World.floorheight*0.8);
	}
	
	boolean isAtLeftWall() {
		return x<0;
	}
	boolean isAtRightWall() {
		return x+size>Main.frame.getWidth();
	}
	
	boolean isAtWall() {
		return isAtLeftWall()||isAtRightWall();
	}
	
	boolean overspeeded = false;
	
	int lastx = 0, lasty = 0;
	
	boolean onBox = false;
	
	boolean jump = false,
			left = false,
			right = false;
	
	int uptime = 0, lefttime = 0, righttime = 0;
	
	boolean spin = false;
	int rotation = 0;
	boolean spinDirectionRight = true;
	
	boolean canDamageFileBox = false;
	
	public void update() {
		
		
		//AI
		
		if(new Random().nextInt(50)==0) {
			int choice = new Random().nextInt(3);
			switch (choice) {
			case 0:
				uptime += new Random().nextInt(60);
				if(new Random().nextInt(3)==1&&!spin) {
					spin = true;
					spinDirectionRight = new Random().nextBoolean();
					canDamageFileBox = true;
				}
					
				break;
			case 1:
				lefttime += new Random().nextInt(60);
				break;
			case 2:
				righttime += new Random().nextInt(60);
				break;

			default:
				break;
			}
		}
		
		jump = false;
		left = false;
		right = false;
		
		if(uptime>0) {
			jump=true;
			uptime--;
		}
		if(lefttime>0) {
			left=true;
			lefttime--;
		}
		if(righttime>0) {
			right=true;
			righttime--;
		}
			
		
		if(spin) {
			if(spinDirectionRight)
				rotation+=7;
			else
				rotation-=7;
			if(rotation>=360||rotation<=-360) {
				rotation = 0;
				spin=false;
			}
				
		}
		
		if(!spin)
			canDamageFileBox = false;
		
		//Physics
		
		
		x+=xspeed;
		y+=yspeed;
		
		
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
			if(left) {
				xspeed-=entitySpeed;
			}
			if(right) {
				xspeed+=entitySpeed;
			}
		}else {
			if(left) {
				xspeed-=0.5;
			}
			if(right) {
				xspeed+=0.5;
			}
		}
		
		
		if(isAtLeftWall()) {
			x=0;
			xspeed=0;
			if(jump) {
				yspeed-=20;
				xspeed+=30;
				overspeeded=true;
			}
		}
			
		if(isAtRightWall()) {
			x=Main.frame.getWidth()-size;
			xspeed=0;
			if(jump) {
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
				yspeed=-(int)(yspeed*0.01);
				
				
			if(!Keyboard.isKeyPressed(KeyEvent.VK_A)&&!Keyboard.isKeyPressed(KeyEvent.VK_D))
				xspeed*=0.3;
			overspeeded=false;
			if(jump) {
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
		
		if(Collision.rectToRect(x, y, size, size, Player.x, Player.y, Player.size, Player.size))
			Player.freezetime = 100;
		
		for(FileBox cb : World.szeneFiles) {
			if(Collision.rectToRect(x, y, size, size, cb.x, cb.y, cb.drawSize, cb.drawSize)) {
				if(canDamageFileBox&&!cb.file.isDirectory()) {
					canDamageFileBox=false;
					cb.damage++;
				}
			}
		}
		
	}
	
	
}
