package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		//File binaryFile = new File(args[0]);
		
		String fileName = args[0]; // Replace with your binary file's name

        try (FileInputStream fis = new FileInputStream(fileName)) {
            int byteRead;

            while ((byteRead = fis.read()) != -1) {
                String binaryString = Integer.toBinaryString(byteRead);
                // Ensure each byte is represented as 8 bits (0-padded if necessary)
                binaryString = String.format("%8s", binaryString).replace(' ', '0');
                System.out.print(binaryString);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
		
		
	}
}
