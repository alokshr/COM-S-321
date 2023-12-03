package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	@SuppressWarnings("serial")
	public static final HashMap<Integer, String> instructions = new HashMap<>() {
		{
			put(0b10001011000, "ADD");
			put(0b1001000100, "ADDI");
			put(0b1011000100, "ADDIS");
			put(0b10101011000, "ADDS");
			put(0b10001010000, "AND");
			put(0b1001001000, "ANDI");
			put(0b1111001000, "ANDIS");
			put(0b1110101000, "ANDS");
			put(0b000101, "B");
			put(0b100101, "BL");
			put(0b11010110000, "BR");
			put(0b10110101, "CBNZ");
			put(0b10110100, "CBZ");
			put(0b11111111110, "DUMP");
			put(0b11001010000, "EOR");
			put(0b1101001000, "EORI");
			put(0b00011110011, "FADDD");
			put(0b00011110001, "FADDS");
			put(0b00011110011, "FCMPD");
			put(0b00011110001, "FCMPS");
			put(0b00011110011, "FDIVD");
			put(0b00011110001, "FDIVS");
			put(0b00011110011, "FMULD");
			put(0b00011110001, "FMULS");
			put(0b00011110011, "FSUBD");
			put(0b00011110001, "FSUBS");
			put(0b11111111111, "HALT");
			put(0b11111000010, "LDUR");
			put(0b00111000010, "LDURB");
			put(0b11111100010, "LDURD");
			put(0b01111000010, "LDURH");
			put(0b10111100010, "LDURS");
			put(0b10111000100, "LDURSW");
			put(0b11010011011, "LSL");
			put(0b11010011010, "LSR");
			put(0b10011011000, "MUL");
			put(0b10101010000, "ORR");
			put(0b1011001000, "ORRI");
			put(0b11111111100, "PRNL");
			put(0b11111111101, "PRNT");
			put(0b10011010110, "SDIV");
			put(0b10011011010, "SMULH");
			put(0b11111000000, "STUR");
			put(0b00111000000, "STURB");
			put(0b11111100000, "STURD");
			put(0b01111000000, "STURH");
			put(0b10111100000, "STURS");
			put(0b10111000000, "STURSW");
			put(0b11001011000, "SUB");
			put(0b1101000100, "SUBI");
			put(0b1111000100, "SUBIS");
			put(0b11101011000, "SUBS");
			put(0b10011010110, "UDIV");
			put(0b10011011110, "UMULH");
		}
	};

	public static void main(String[] args) throws IOException {
		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}

		File binaryFile = new File(args[0]);

		// Pull 32-bit lines from the file
		FileInputStream reader = new FileInputStream(binaryFile);
		ArrayList<Integer> instructionLines = new ArrayList<Integer>();

		byte[] buffer = new byte[4];

		while (reader.read(buffer) != -1) {
			int value = ((buffer[0] & 0xFF) << 24) | ((buffer[1] & 0xFF) << 16) | ((buffer[2] & 0xFF) << 8)
					| ((buffer[3] & 0xFF) << 0);

			instructionLines.add(value);
		}

		// Make output file

		// TODO Interpret each 32-bit instruction

		// TODO Detect opcodes

		reader.close();
	}
}
