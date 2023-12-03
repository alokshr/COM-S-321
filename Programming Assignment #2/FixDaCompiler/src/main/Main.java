package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		File binaryFile = new File(args[0]);
		
		FileInputStream reader;
		try {
			reader = new FileInputStream(binaryFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}
		
		
	}
}
