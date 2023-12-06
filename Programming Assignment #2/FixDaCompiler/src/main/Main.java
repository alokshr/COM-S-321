package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import instructions.BinToLEG;
import instructions.Instruction;
import instructions.InstructionType;

public class Main {
	
	public static void main(String[] args) throws IOException{
		// Get binary file
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments given");
		}
		
		File binaryFile = new File(args[0]);

		// Pull 32-bit lines from the file
		FileInputStream reader = new FileInputStream(binaryFile);
		
		ArrayList<Integer> bitList = new ArrayList<Integer>();
		
		byte[] buffer = new byte[4];

		while (reader.read(buffer) != -1) {
			int value = 
				((buffer[0] & 0xFF) << 24) |
				((buffer[1] & 0xFF) << 16) |
				((buffer[2] & 0xFF) << 8)  |
				((buffer[3] & 0xFF) << 0);
			
			bitList.add(value);	
		}
		
		reader.close();
		
		// Interpret each 32-bit instruction
		// HashMap<Integer, Instruction> instructionMap = new HashMap<Integer, Instruction>();
		// ArrayList<Instruction> outputInstructionsList = new ArrayList<Instruction>(instructionMap.values());
		
		ArrayList<Instruction> outputInstructionsList = new ArrayList<Instruction>();
		
		for (int bits : bitList) {
			Instruction instruction = BinToLEG.getLEGv8Code(bits);
			
			//instructionMap.put(instruction.instructionNum, instruction)
			outputInstructionsList.add(instruction);
		}
		
		var readFromInstructionList = new ArrayList<>(outputInstructionsList);
		HashSet<String> labelList = new HashSet<>();
		
		for (Instruction instr : readFromInstructionList) {
			if (instr.type == InstructionType.B || instr.type == InstructionType.CB) {
				
				// Grab label & number
				String label = instr.debugString.replaceAll("\\b(?!label).+ ", "");
				int labelNum = Integer.valueOf(label.substring(5));
				
				if (!labelList.contains(label)) {
					labelList.add(label);
					
					// Find where number is the same
					var foundInstruction = outputInstructionsList.stream().filter((inst -> inst.instructionNum == labelNum)).collect(Collectors.toList());
					int labelInstructionIndex = outputInstructionsList.indexOf(foundInstruction.get(0));
					
					// Input new label at found index + 1
					outputInstructionsList.add(labelInstructionIndex, new Instruction(label + ":", -1, InstructionType.L));
				}
			}
		}
		
		for(Instruction debug : outputInstructionsList) {
			System.out.println(debug.debugString);
		}
		
		
		
		// Make output file
		String outputFileName = binaryFile.getName().replaceAll("[.].*", "") + ".legv8asm";
		
		File outputFile = new File(outputFileName);
		outputFile.setWritable(true);
		
		FileWriter fw = new FileWriter(outputFile);
		
		for (Instruction instr : outputInstructionsList) {
			fw.write(instr.debugString + "\n");
		}
		
		fw.flush();
		fw.close();
	}
}
