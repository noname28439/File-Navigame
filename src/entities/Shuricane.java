package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.ImageLoader;
import gfx.ImageRotator;
import main.Collision;
import main.Player;
import main.World;

public class Shuricane extends Entity{

	public static BufferedImage skin = ImageLoader.loadImage("Entity_Shuricane_");
	static ImageRotator skinRotator = new ImageRotator(skin);
	
	int rotation = 0;
	
	int size = 30;
	int x, y;
	
	boolean flying = false;
	public float xspeed = 0;
	public float yspeed = 0;
	
	int livetime = 0;
	
	public Shuricane(int x, int y, boolean isItem) {
		this.x = x;
		this.y = y;
		flying = !isItem;
	}
	
	@Override
	public void draw(Graphics g) {
		if(rotation==0) {
			g.drawImage(skin, x, y, size, size,  null);
		}else {
			g.drawImage(skinRotator.getRotatedImage(rotation), x, y, size, size,  null);
		}
			
		
	}

	@Override
	public void update() {
		
		for(int i = 0; i<World.entities.size();i++) {
			Entity ce = World.entities.get(i);
			if(ce instanceof Slime) {
				Slime cs = (Slime) ce;
				if(Collision.rectToRect(x, y, size, size, cs.x, cs.y, cs.size, cs.size)) {
					World.entities.remove(ce);
					World.entities.remove(this);
				}
			}
			
		}
		
		
		
		
		if(!flying)
			if(Collision.rectToRect(x, y, size, size, Player.x, Player.y, Player.size, Player.size)) {
				Player.shuricanes+=5;
				World.entities.remove(this);
			}
				
		
		if(flying) {
			x+=xspeed;
			y+=yspeed;
			
			rotation+=10;
			
			
			livetime++;
			if(livetime>250)
				World.entities.remove(this);
			
		}
		
	}

}
