package mipssim.swain91.git;

/**
 * The base class for immediate instruction representation in MIPS. Acts as a
 * container class for variables required to execute a instruction.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class ImmediateInstruction implements Instruction {
	
    private InstructionString instructionString;
    private String opcode;
    private String name;
    private int source;
    private int target;
    private int immediateSigned;
    private int immediateUnsigned;

    public ImmediateInstruction(InstructionString is) {
        this.instructionString = is;
        this.opcode = is.parseOpcode();
        this.source = is.parseSource();
        this.target = is.parseTarget();
        this.immediateSigned = is.parseImmediateSigned();
        this.immediateUnsigned = is.parseImmediateUnsigned();

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

    public int getSource() { return this.source; }

    public int getTarget() {
        return this.target;
    }

    public int getImmediateSigned() { return this.immediateSigned; }

    public int getImmediateUnsigned() {
        return this.immediateUnsigned;
    }

    public String getName() { return this.name; }

    @Override
    public void determineName(int opcode) {
        switch (opcode) {
            case 4:
                this.name = "beq";
                break;
            case 5:
                this.name = "bne";
                break;
            case 8:
                this.name = "addi";
                break;
            case 9:
                this.name = "addiu";
                break;
            case 12:
                this.name = "andi";
                break;
            case 13:
                this.name = "ori";
                break;
            case 23:
                this.name = "lw";
                break;
            case 43:
                this.name = "sw";
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return this.name + " $s: " + this.source + ", $t: " + this.target + ", i: " + this.immediateSigned;
    }
}
