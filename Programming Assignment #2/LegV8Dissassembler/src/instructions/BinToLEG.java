package instructions;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinToLEG {
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
		  { 0b01010100   , InstructionHeaders.BCOND },
		  { 0b100101     , InstructionHeaders.BL },
		  { 0b11010110000, InstructionHeaders.BR },
		  { 0b10110101   , InstructionHeaders.CBNZ },
		  { 0b10110100   , InstructionHeaders.CBZ },
		  { 0b11111111110, InstructionHeaders.DUMP },
		  { 0b11001010000, InstructionHeaders.EOR },
		  { 0b1101001000 , InstructionHeaders.EORI },
		  { 0b11111111111, InstructionHeaders.HALT },
		  { 0b11111000010, InstructionHeaders.LDUR },
		  { 0b11010011011, InstructionHeaders.LSL },
		  { 0b11010011010, InstructionHeaders.LSR },
		  { 0b10011011000, InstructionHeaders.MUL },
		  { 0b10101010000, InstructionHeaders.ORR },
		  { 0b1011001000 , InstructionHeaders.ORRI },
		  { 0b11111111100, InstructionHeaders.PRNL },
		  { 0b11111111101, InstructionHeaders.PRNT },
		  { 0b11111000000, InstructionHeaders.STUR },
		  { 0b11001011000, InstructionHeaders.SUB },
		  { 0b1101000100 , InstructionHeaders.SUBI },
		  { 0b1111000100 , InstructionHeaders.SUBIS },
		  { 0b11101011000, InstructionHeaders.SUBS },
	}).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	public static int labelNum = 0;
	
	public static Instruction getLEGv8Code(int instructionBits) {
		MnemonicInfo mi = getMnemonic(instructionBits);
		
		String mnemonic = mi.mnemonic;
		if (mnemonic == null) return null;
		
		int argumentBits = instructionBits & (0xFFFFFFFF >>> mi.opcodeEnd);
		Instruction instr = null;
		
		switch (getType(mnemonic)) {
			case R:
				instr = new Instruction(decodeRType(mnemonic, argumentBits), labelNum, InstructionType.R);
				break;
			case I:
				instr = new Instruction(decodeIType(mnemonic, argumentBits), labelNum, InstructionType.I);
				break;
			case D:
				instr = new Instruction(decodeDType(mnemonic, argumentBits), labelNum, InstructionType.D);
				break;
			case B:
				instr = new Instruction(decodeBType(mnemonic, argumentBits), labelNum, InstructionType.B);
				break;
			case CB:
				instr = new Instruction(decodeCBType(mnemonic, argumentBits), labelNum, InstructionType.CB);
				break;
			default:
				throw new IllegalArgumentException();
		}

		labelNum++;
		return instr;
	}
	
	private static InstructionType getType(String mnemonic) {
		switch (mnemonic) {
			case InstructionHeaders.ADD:
				return InstructionType.R;
			case InstructionHeaders.ADDI:
				return InstructionType.I;
			case InstructionHeaders.ADDIS:
				return InstructionType.I;
			case InstructionHeaders.ADDS:
				return InstructionType.R;
			case InstructionHeaders.AND:
				return InstructionType.R;
			case InstructionHeaders.ANDI:
				return InstructionType.I;
			case InstructionHeaders.ANDIS:
				return InstructionType.I;
			case InstructionHeaders.ANDS:
				return InstructionType.R;
			case InstructionHeaders.B:
				return InstructionType.B;
			case InstructionHeaders.BCOND:
				return InstructionType.CB;
			case InstructionHeaders.BL:
				return InstructionType.B;
			case InstructionHeaders.BR:
				return InstructionType.R;
			case InstructionHeaders.CBNZ:
				return InstructionType.CB;
			case InstructionHeaders.CBZ:
				return InstructionType.CB;
			case InstructionHeaders.DUMP:
				return InstructionType.R;
			case InstructionHeaders.EOR:
				return InstructionType.R;
			case InstructionHeaders.EORI:
				return InstructionType.I;
			case InstructionHeaders.HALT:
				return InstructionType.R;
			case InstructionHeaders.LDUR:
				return InstructionType.D;
			case InstructionHeaders.LSL:
				return InstructionType.R;
			case InstructionHeaders.LSR:
				return InstructionType.R;
			case InstructionHeaders.MUL:
				return InstructionType.R;
			case InstructionHeaders.ORR:
				return InstructionType.R;
			case InstructionHeaders.ORRI:
				return InstructionType.I;
			case InstructionHeaders.PRNL:
				return InstructionType.R;
			case InstructionHeaders.PRNT:
				return InstructionType.R;
			case InstructionHeaders.STUR:
				return InstructionType.D;
			case InstructionHeaders.SUB:
				return InstructionType.R;
			case InstructionHeaders.SUBI:
				return InstructionType.I;
			case InstructionHeaders.SUBIS:
				return InstructionType.I;
			case InstructionHeaders.SUBS:
				return InstructionType.R;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private static String getRegisterName(int register) {
		switch (register) {
			case 31:
				return "XZR";
			case 30:
				return "LR";
			case 29:
				return "FP";
			case 28:
				return "SP";
			default:
				return "X" + register;
		}
	}
	
	private static MnemonicInfo getMnemonic(int instructionBits) {
		String mnemonic = null;
		int opcodeEnd = 0;
		
		for (int i = 6; i <= 11; i++) {
			int opcode = instructionBits >>> (32-i);
			
			mnemonic = instructions.get(opcode);
			opcodeEnd = i;
			
			if (mnemonic != null) {
				return new MnemonicInfo(mnemonic, opcodeEnd);
			}
		}
		
		return new MnemonicInfo(mnemonic, opcodeEnd);
	}
	
	private static String decodeRType(String mnemonic, int argumentBits) {
		int rd = argumentBits & 0x1F;
		int rn = (argumentBits >>> 5) & 0x1F;
		int rm = (argumentBits >>> 16) & 0x1F;
		
		if (mnemonic.equals(InstructionHeaders.LSL) || mnemonic.equals(InstructionHeaders.LSR)) {
			int shamt = (argumentBits >>> 10) & 0x2F;
			return String.format("%s %s, %s, #%d", mnemonic, getRegisterName(rd), getRegisterName(rn), shamt);
		}
		
		if (mnemonic.equals(InstructionHeaders.BR)) {
			return String.format("%s %s", mnemonic, getRegisterName(rn));
		}
		
		if (mnemonic.equals(InstructionHeaders.PRNL)) {
			return mnemonic;
		}
		
		if (mnemonic.equals(InstructionHeaders.PRNT)) {
			return String.format("%s %s", mnemonic, getRegisterName(rd));
		}
		
		return String.format("%s %s, %s, %s", mnemonic, getRegisterName(rd), getRegisterName(rn), getRegisterName(rm));
	}

	private static String decodeIType(String mnemonic, int argumentBits) {
		int rd = argumentBits & 0x1F;
		int rn = (argumentBits >>> 5) & 0x1F;
		int immed = (argumentBits >>> 10) & 0xFFF;
		
		return String.format("%s %s, %s, #%d", mnemonic, getRegisterName(rd), getRegisterName(rn), immed);	
	}
	
	// TODO figure out what the op field is for
	private static String decodeDType(String mnemonic, int argumentBits) {
		int rt = argumentBits & 0x1F;
		int rn = (argumentBits >>> 5) & 0x1F;
		int op = (argumentBits >>> 10) & 0x3;
		int dt_addr = (argumentBits >>> 12) & 0x1FF;
		
		return String.format("%s %s, [%s, #%d]", mnemonic, getRegisterName(rt), getRegisterName(rn), dt_addr);	
	}
	
	private static String decodeBType(String mnemonic, int argumentBits) {
		int br_addr = argumentBits & 0x03FFFFFF;
		
		// If addr is negative, take two's complement to find its integer value
		if (((br_addr >>> 25) & 0x1) == 1) {
			br_addr = -(((br_addr ^ 0xFFFFFF) + 1) & 0xFFFFFF);
		}
				
		String br_label = "label" + (labelNum + br_addr);
		
		return String.format("%s %s", mnemonic, br_label);
	}
	
	private static String decodeCBType(String mnemonic, int argumentBits) {
		int rt = argumentBits & 0x1F;
		int br_addr = (argumentBits >>> 5) & 0x7FFFF;
		
		// If addr is negative, take two's complement to find its integer value
		if (((br_addr >>> 25) & 0x1) == 1) {
			br_addr = -(((br_addr ^ 0xFFFFFF) + 1) & 0xFFFFFF);
		}
				
		String br_label = "label" + (labelNum + br_addr);
		
		if (mnemonic.equals(InstructionHeaders.BCOND)) {
			String conditional = null;
			
			switch (rt) {
			case 0:
				conditional = "EQ";
				break;
			case 1:
				conditional = "NE";
				break;
			case 2:
				conditional = "HS";
				break;
			case 3:
				conditional = "LO";
				break;
			case 4:
				conditional = "MI";
				break;
			case 5:
				conditional = "PL";
				break;
			case 6:
				conditional = "VS";
				break;
			case 7:
				conditional = "VC";
				break;
			case 8:
				conditional = "HI";
				break;
			case 9:
				conditional = "LS";
				break;
			case 10:
				conditional = "GE";
				break;
			case 11:
				conditional = "LT";
				break;
			case 12:
				conditional = "GT";
				break;
			case 13:
				conditional = "LE";
				break;
			}
			return String.format("%s %s", mnemonic + conditional, br_label);
		}
		
		return String.format("%s %s, %s", mnemonic, getRegisterName(rt), br_label);
	}
	
}
