package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException{
		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		File binaryFile = new File(args[0]);
		
		// Open stream to read the bits of the file
		FileInputStream reader = null;
		
		try {
			reader = new FileInputStream(binaryFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		
		byte[] instructionLine = new byte[4];
		
		reader.read(instructionLine);
	}
}
