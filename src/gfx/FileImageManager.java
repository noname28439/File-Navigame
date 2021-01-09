package gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

public class FileImageManager {

	
	static HashMap<String, BufferedImage> loaded = new HashMap<>();
	
	static BufferedImage randomuster = ImageLoader.loadImage("Randomuster");
	
	public static void load() {
		
		
		
	}
	
	public static BufferedImage getFileImage(String extension) {
		System.out.println("Contains: "+loaded.containsKey(extension));
		if(loaded.containsKey(extension))
			return loaded.get(extension);
		else {
			if(ImageLoader.loadImage("File_"+extension+"_")!=null) {
				loaded.put(extension, ImageLoader.loadImage("File_"+extension+"_"));
				return ImageLoader.loadImage("File_"+extension+"_");
			}
		}
		return randomuster;
		
	}
	
}
