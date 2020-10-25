package mipssim.swain91.git;

/**
 * The base class for jump instruction representation in MIPS. Acts as a
 * container class for variables required to execute a instruction.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class JumpInstruction implements Instruction {
	
    private InstructionString instructionString;
    private String opcode;
    private String name;
    private int jumpAddress;

    public JumpInstruction(InstructionString is) {
        this.instructionString = is;
        this.opcode = is.parseOpcode();
        this.jumpAddress = is.parseJumpAddress();

        int op = (int) Long.parseLong(this.opcode, 2);
        determineName(op);
    }

    @Override
    public InstructionString getInstructionString() {
        return this.instructionString;
    }

    @Override
    public String getOpcode() {
        return this.opcode;
    }

    public String getName() { return this.name; }

    public int getJumpAddress() {
        return this.jumpAddress;
    }

    @Override
    public void determineName(int opcode) {
        switch (opcode) {
            case 2:
                this.name = "jump";
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return this.name + " address: " + this.jumpAddress;
    }
}
