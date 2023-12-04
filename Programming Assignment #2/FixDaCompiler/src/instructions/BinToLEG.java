package instructions;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.InstructionHeaders;

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

	
	public static String getLEGv8Code(int instructionBits) {
		MnemonicInfo mi = getMnemonic(instructionBits);
		
		String mnemonic = mi.mnemonic;
		if (mnemonic == null) return null;
		
		int argumentBits = instructionBits & (0xFFFFFFFF >>> mi.opcodeEnd);
		
		switch (getType(mnemonic)) {
			case R:
				return decodeRType(mnemonic, argumentBits);
			case I:
				return decodeIType(mnemonic, argumentBits);
			case D:
				return decodeDType(mnemonic, argumentBits);
			case B:
				return decodeBType(mnemonic, argumentBits);
			case CB:
				return decodeCBType(mnemonic, argumentBits);
			case IW:
				return decodeIWType(mnemonic, argumentBits);
			default:
				throw new IllegalArgumentException("Ya fucked up");
		}
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
			case InstructionHeaders.LDURB:
				return InstructionType.D;
			case InstructionHeaders.LDURD:
				return InstructionType.D;
			case InstructionHeaders.LDURH:
				return InstructionType.D;
			case InstructionHeaders.LDURS:
				return InstructionType.D;
			case InstructionHeaders.LDURSW:
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
			case InstructionHeaders.SMULH:
				return InstructionType.R;
			case InstructionHeaders.STUR:
				return InstructionType.D;
			case InstructionHeaders.STURB:
				return InstructionType.D;
			case InstructionHeaders.STURD:
				return InstructionType.D;
			case InstructionHeaders.STURH:
				return InstructionType.D;
			case InstructionHeaders.STURS:
				return InstructionType.D;
			case InstructionHeaders.STURSW:
				return InstructionType.D;
			case InstructionHeaders.SUB:
				return InstructionType.R;
			case InstructionHeaders.SUBI:
				return InstructionType.I;
			case InstructionHeaders.SUBIS:
				return InstructionType.I;
			case InstructionHeaders.SUBS:
				return InstructionType.R;
			case InstructionHeaders.UMULH:
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
	
	//TODO add emulator specific instructions
	private static String decodeRType(String mnemonic, int argumentBits) {
		int rd = argumentBits & 0x1F;
		
		if (mnemonic.equals("LSL") || mnemonic.equals("LSR")) {
			int shamt = (argumentBits >>> 10) & 0x2F;
			return String.format("%s X%d, #%d", mnemonic, rd, shamt);
		}

		int rn = (argumentBits >>> 5) & 0x1F;
		int rm = (argumentBits >>> 16) & 0x1F;
		
		if (mnemonic.equals("BR")) {
			return String.format("%s %s", mnemonic, getRegisterName(rn));
		}
		
		if (mnemonic.equals("PRNL")) {
			return mnemonic;
		}
		
		if (mnemonic.equals("PRNT")) {
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
		
		return String.format("%s %d", mnemonic, br_addr);
	}
	
	private static String decodeCBType(String mnemonic, int argumentBits) {
		int rt = argumentBits & 0x1F;
		int br_addr = (argumentBits >>> 5) & 0x7FFFF;
		
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
			return String.format("%s %s", mnemonic + conditional, br_addr);
		}
		
		return String.format("%s %s, %d", mnemonic, getRegisterName(rt), br_addr);
	}
	
	private static String decodeIWType(String mnemonic, int argumentBits) {
		return "";
	}
	
}
