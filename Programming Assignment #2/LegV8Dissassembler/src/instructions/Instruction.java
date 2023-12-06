package instructions;

public class Instruction {
	public String debugString;
	public InstructionType type;
	public int instructionNum;
	
	public Instruction(String debugString, int instructionNum, InstructionType type) {
		this.debugString = debugString;
		this.instructionNum = instructionNum;
		this.type = type;
	}
}
