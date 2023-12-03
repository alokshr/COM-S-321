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
		  { 0b10001011000, InstructionHeaders.ADD },
		  { 0b1001000100 , InstructionHeaders.ADDI },
		  { 0b1011000100 , InstructionHeaders.ADDIS },
		  { 0b10101011000, InstructionHeaders.ADDS },
		  { 0b10001010000, InstructionHeaders.AND },
		  { 0b1001001000 , InstructionHeaders.ANDI },
		  { 0b1111001000 , InstructionHeaders.ANDIS },
		  { 0b1110101000 , InstructionHeaders.ANDS },
		  { 0b000101     , InstructionHeaders.B },
		  { 0b100101     , InstructionHeaders.BL },
		  { 0b11010110000, InstructionHeaders.BR },
		  { 0b10110101   , InstructionHeaders.CBNZ },
		  { 0b10110100   , InstructionHeaders.CBZ },
		  { 0b11111111110, InstructionHeaders.DUMP },
		  { 0b11001010000, InstructionHeaders.EOR },
		  { 0b1101001000 , InstructionHeaders.EORI },
		  { 0b11111111111, InstructionHeaders.HALT },
		  { 0b11111000010, InstructionHeaders.LDUR },
		  { 0b00111000010, InstructionHeaders.LDURB },
		  { 0b11111100010, InstructionHeaders.LDURD },
		  { 0b01111000010, InstructionHeaders.LDURH },
		  { 0b10111100010, InstructionHeaders.LDURS },
		  { 0b10111000100, InstructionHeaders.LDURSW },
		  { 0b11010011011, InstructionHeaders.LSL },
		  { 0b11010011010, InstructionHeaders.LSR },
		  { 0b10011011000, InstructionHeaders.MUL },
		  { 0b10101010000, InstructionHeaders.ORR },
		  { 0b1011001000 , InstructionHeaders.ORRI },
		  { 0b11111111100, InstructionHeaders.PRNL },
		  { 0b11111111101, InstructionHeaders.PRNT },
		  { 0b10011011010, InstructionHeaders.SMULH },
		  { 0b11111000000, InstructionHeaders.STUR },
		  { 0b00111000000, InstructionHeaders.STURB },
		  { 0b11111100000, InstructionHeaders.STURD },
		  { 0b01111000000, InstructionHeaders.STURH },
		  { 0b10111100000, InstructionHeaders.STURS },
		  { 0b10111000000, InstructionHeaders.STURSW },
		  { 0b11001011000, InstructionHeaders.SUB },
		  { 0b1101000100 , InstructionHeaders.SUBI },
		  { 0b1111000100 , InstructionHeaders.SUBIS },
		  { 0b11101011000, InstructionHeaders.SUBS },
		  { 0b10011011110, InstructionHeaders.UMULH }
	}).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	public static final Integer instructionsFloatingPointS = 0b00011110001;
	public static final Map<Integer, String> specialInstructionsFloatingPointS = Stream.of(new Object[][]{
		{ 0b001010, InstructionHeaders.FADDS },
		{ 0b001000, InstructionHeaders.FCMPS },
		{ 0b000110, InstructionHeaders.FDIVS },
		{ 0b000010, InstructionHeaders.FMULS },
		{ 0b001110, InstructionHeaders.FSUBS },
	}).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
	
	public static final Integer instructionsFloatingPointD = 0b00011110011;
	public static final Map<Integer, String> specialInstructionsFloatingPointD = Stream.of(new Object[][]{
		{ 0b001010, InstructionHeaders.FADDD },
		{ 0b001000, InstructionHeaders.FCMPD },
		{ 0b000110, InstructionHeaders.FDIVD },
		{ 0b000010, InstructionHeaders.FMULD },
		{ 0b001110, InstructionHeaders.FSUBD },
	}).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	public static final Integer instructionsDivision = 0b10011010110;
	public static final Map<Integer, String> specialInstructionsDivision = Stream.of(new Object[][]{
		{ 0b000010, InstructionHeaders.SDIV },
		{ 0b000011, InstructionHeaders.UDIV },
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
		
		// Interpret each 32-bit instruction
		ArrayList<String> textInstructions = new ArrayList<>();
		
		for (int l : instructionLines) {
			
		}
		
		// Make output file
		String outputFileName = binaryFile.getName();
		
		reader.close();
	}
}
