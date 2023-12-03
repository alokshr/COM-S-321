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
			  { 0b10001011000, "ADD"       },
			  { 0b1001000100 , "ADDI"      },
			  { 0b1011000100 , "ADDIS"     },
			  { 0b10101011000, "ADDS"      },
			  { 0b10001010000, "AND"       },
			  { 0b1001001000 , "ANDI"      },
			  { 0b1111001000 , "ANDIS"     },
			  { 0b1110101000 , "ANDS"      },
			  { 0b000101     , "B"         },
			  { 0b100101     , "BL"        },
			  { 0b11010110000, "BR"        },
			  { 0b10110101   , "CBNZ"      },
			  { 0b10110100   , "CBZ"       },
			  { 0b11111111110, "DUMP"      },
			  { 0b11001010000, "EOR"       },
			  { 0b1101001000 , "EORI"      },
			  { 0b00011110011, "FADDD"     },
			  { 0b00011110001, "FADDS"     },
			  { 0b00011110011, "FCMPD"     },
			  { 0b00011110001, "FCMPS"     },
			  { 0b00011110011, "FDIVD"     },
			  { 0b00011110001, "FDIVS"     },
			  { 0b00011110011, "FMULD"     },
			  { 0b00011110001, "FMULS"     },
			  { 0b00011110011, "FSUBD"     },
			  { 0b00011110001, "FSUBS"     },
			  { 0b11111111111, "HALT"      },
			  { 0b11111000010, "LDUR"      },
			  { 0b00111000010, "LDURB"     },
			  { 0b11111100010, "LDURD"     },
			  { 0b01111000010, "LDURH"     },
			  { 0b10111100010, "LDURS"     },
			  { 0b10111000100, "LDURSW"    },
			  { 0b11010011011, "LSL"       },
			  { 0b11010011010, "LSR"       },
			  { 0b10011011000, "MUL"       },
			  { 0b10101010000, "ORR"       },
			  { 0b1011001000 , "ORRI"      },
			  { 0b11111111100, "PRNL"      },
			  { 0b11111111101, "PRNT"      },
			  { 0b10011010110, "SDIV"      },
			  { 0b10011011010, "SMULH"     },
			  { 0b11111000000, "STUR"      },
			  { 0b00111000000, "STURB"     },
			  { 0b11111100000, "STURD"     },
			  { 0b01111000000, "STURH"     },
			  { 0b10111100000, "STURS"     },
			  { 0b10111000000, "STURSW"    },
			  { 0b11001011000, "SUB"       },
			  { 0b1101000100 , "SUBI"      },
			  { 0b1111000100 , "SUBIS"     },
			  { 0b11101011000, "SUBS"      },
			  { 0b10011010110, "UDIV"      },
			  { 0b10011011110, "UMULH"     }
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
		
		
		
		for () {
			
		}
		
		// TODO Interpret each 32-bit instruction
		
		// TODO Detect opcodes
		
		reader.close();
	}
}
