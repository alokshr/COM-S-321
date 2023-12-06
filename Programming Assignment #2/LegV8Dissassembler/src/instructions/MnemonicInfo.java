package instructions;

public class MnemonicInfo {
	public String mnemonic;
	public int opcodeEnd;
	
	public MnemonicInfo(String mnemonic, int opcodeEnd) {
		this.mnemonic = mnemonic;
		this.opcodeEnd = opcodeEnd;
	}
}