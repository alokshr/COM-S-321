package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws IOException{
		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		File binaryFile = new File(args[0]);
		
		// Open stream to read the bits of the file
		FileInputStream reader = new FileInputStream(binaryFile);
		
		ArrayList<Integer> instructionLines = new ArrayList<Integer>();
		
		byte[] buffer = new byte[4];

		while (reader.read(buffer) != -1) {
			int value = 
				((buffer[0] & 0xFF) << 24) |
				((buffer[1] & 0xFF) << 16) |
				((buffer[2] & 0xFF) << 8)  |
				((buffer[3] & 0xFF) << 0);
			
			instructionLines.add(value);	
		}
		
		
		
		reader.close();
	}
}
