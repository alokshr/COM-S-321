package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static String byteToBinaryString(byte value) {
		StringBuilder binaryString = new StringBuilder(8); // Assuming you want 8 bits (1 byte)
		
		for (int i = 7; i >= 0; i--) {
			int bit = (value >> i) & 1; // Shift the bits and extract the least significant bit
			binaryString.append(bit);
		}
		
		return binaryString.toString();
	}
	
	public static void main(String[] args) throws IOException{

		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		FileInputStream fis = null;
		
		String fileName = args[0]; // Replace with your binary file's name

        try {
			int lineNumber = 0;
			fis = new FileInputStream(fileName);
            byte[] buffer = new byte[4]; // Assuming each 32-bit integer is 4 bytes
			
            while (fis.read(buffer) != -1) {
				String _32BString = "";
				for(byte b : buffer) {
					_32BString += byteToBinaryString(b);
				}
				System.out.println(lineNumber + ": " +_32BString);
				lineNumber += 1;
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } finally {
			try {
				if(fis != null) {
					fis.close();
				}
			} catch (IOException e) {
                System.err.println("Error closing the file: " + e.getMessage());
            }
		}
	}
}
