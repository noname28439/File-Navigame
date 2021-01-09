package main;

import java.io.File;
import java.util.ArrayList;

public class FileManager {
	
	public static String currentFilePath = "C:\\Users\\Nutzer\\Desktop\\Tools\\NavigameTest";
	
	public static ArrayList<File> getFiles() {
		ArrayList<File> found = new ArrayList<>();
		File dir = new File(currentFilePath);
		if(dir.isDirectory()) {
			for(int i = 0; i<dir.listFiles().length;i++) {
				File currentFile = dir.listFiles()[i];
				found.add(currentFile);
			}
		}else {
			System.err.println(currentFilePath+" is no Directory!");
		}
		return found;
	}
	
	
	
	
	
	
}
