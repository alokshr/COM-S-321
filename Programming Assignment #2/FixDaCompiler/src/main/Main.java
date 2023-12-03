package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	
	public static final Map<Integer, String> instructions = Stream.of(new Object[][]{
			  { 0b10001011000, InstrucitonHeaders.ADD },
			  { 0b1001000100 , InstrucitonHeaders.ADDI },
			  { 0b1011000100 , InstrucitonHeaders.ADDIS },
			  { 0b10101011000, InstrucitonHeaders.ADDS },
			  { 0b10001010000, InstrucitonHeaders.AND },
			  { 0b1001001000 , InstrucitonHeaders.ANDI },
			  { 0b1111001000 , InstrucitonHeaders.ANDIS },
			  { 0b1110101000 , InstrucitonHeaders.ANDS },
			  { 0b000101     , InstrucitonHeaders.B },
			  { 0b100101     , InstrucitonHeaders.BL },
			  { 0b11010110000, InstrucitonHeaders.BR },
			  { 0b10110101   , InstrucitonHeaders.CBNZ },
			  { 0b10110100   , InstrucitonHeaders.CBZ },
			  { 0b11111111110, InstrucitonHeaders.DUMP },
			  { 0b11001010000, InstrucitonHeaders.EOR },
			  { 0b1101001000 , InstrucitonHeaders.EORI },
			  { 0b00011110011, InstrucitonHeaders.FADDD },
			  { 0b00011110001, InstrucitonHeaders.FADDS },
			  { 0b00011110011, InstrucitonHeaders.FCMPD },
			  { 0b00011110001, InstrucitonHeaders.FCMPS },
			  { 0b00011110011, InstrucitonHeaders.FDIVD },
			  { 0b00011110001, InstrucitonHeaders.FDIVS },
			  { 0b00011110011, InstrucitonHeaders.FMULD },
			  { 0b00011110001, InstrucitonHeaders.FMULS },
			  { 0b00011110011, InstrucitonHeaders.FSUBD },
			  { 0b00011110001, InstrucitonHeaders.FSUBS },
			  { 0b11111111111, InstrucitonHeaders.HALT },
			  { 0b11111000010, InstrucitonHeaders.LDUR },
			  { 0b00111000010, InstrucitonHeaders.LDURB },
			  { 0b11111100010, InstrucitonHeaders.LDURD },
			  { 0b01111000010, InstrucitonHeaders.LDURH },
			  { 0b10111100010, InstrucitonHeaders.LDURS },
			  { 0b10111000100, InstrucitonHeaders.LDURSW },
			  { 0b11010011011, InstrucitonHeaders.LSL },
			  { 0b11010011010, InstrucitonHeaders.LSR },
			  { 0b10011011000, InstrucitonHeaders.MUL },
			  { 0b10101010000, InstrucitonHeaders.ORR },
			  { 0b1011001000 , InstrucitonHeaders.ORRI },
			  { 0b11111111100, InstrucitonHeaders.PRNL },
			  { 0b11111111101, InstrucitonHeaders.PRNT },
			  { 0b10011010110, InstrucitonHeaders.SDIV },
			  { 0b10011011010, InstrucitonHeaders.SMULH },
			  { 0b11111000000, InstrucitonHeaders.STUR },
			  { 0b00111000000, InstrucitonHeaders.STURB },
			  { 0b11111100000, InstrucitonHeaders.STURD },
			  { 0b01111000000, InstrucitonHeaders.STURH },
			  { 0b10111100000, InstrucitonHeaders.STURS },
			  { 0b10111000000, InstrucitonHeaders.STURSW },
			  { 0b11001011000, InstrucitonHeaders.SUB },
			  { 0b1101000100 , InstrucitonHeaders.SUBI },
			  { 0b1111000100 , InstrucitonHeaders.SUBIS },
			  { 0b11101011000, InstrucitonHeaders.SUBS },
			  { 0b10011010110, InstrucitonHeaders.UDIV },
			  { 0b10011011110, InstrucitonHeaders.UMULH }
			}).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
	
	public static void main(String[] args) throws IOException{
		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		File binaryFile = new File(args[0]);
		
		// Open stream to read the bits of the file
		FileInputStream reader = new FileInputStream(binaryFile);
		
		ArrayList<Integer> instructionLines = new ArrayList<Integer>();
		
		// Pull 32-bit lines from the file
		byte[] buffer = new byte[4];

		while (reader.read(buffer) != -1) {
			int value = 
				((buffer[0] & 0xFF) << 24) |
				((buffer[1] & 0xFF) << 16) |
				((buffer[2] & 0xFF) << 8)  |
				((buffer[3] & 0xFF) << 0);
			
			instructionLines.add(value);	
		}
		
		// Make output file
		String outputFileName = binaryFile.getName();

		// TODO Interpret each 32-bit instruction
		
		// TODO Detect opcodes
		
		reader.close();
	}
}
