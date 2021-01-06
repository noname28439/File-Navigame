package gfx;

import java.awt.image.BufferedImage;

public class ImageRegistry {

	public static BufferedImage Icon_Sonkgylotte = ImageLoader.loadImage("SonkgylotteIcon");
	public static BufferedImage Icon_Freeze = ImageLoader.loadImage("Freeze");
	public static BufferedImage Icon_Weiser = ImageLoader.loadImage("SchwanzusLongusKopf");
	
	public static BufferedImage Boost_SchwanzusLongus = ImageLoader.loadImage("SchwanzusLongus");
	public static ImageRotator Boost_SchwanzusLongus_Rotator = new ImageRotator(Boost_SchwanzusLongus);
	
	public static BufferedImage Boost_Sonkgylotte = ImageLoader.loadImage("StolzerSonkgylotte");
	public static ImageRotator Boost_Sonkgylotte_Rotator = new ImageRotator(Boost_Sonkgylotte);
	
	public static BufferedImage Geist_Penisgeist = ImageLoader.loadImage("Geist_PenisGeist");
	public static ImageRotator Rotator_Penisgeist = new ImageRotator(Geist_Penisgeist);
	
	public static BufferedImage Geist_eins = ImageLoader.loadImage("Geist_1");
	public static ImageRotator Rotator_eins = new ImageRotator(Geist_eins);
	
	public static BufferedImage Hintergrund = ImageLoader.loadImage("GeisterHintergrund");
	
	public static int size = (int) Geist_Penisgeist.getHeight();
	
	
}
