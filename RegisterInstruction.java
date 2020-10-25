package mipssim.swain91.git;

/**
 * The base class for register instruction representation in MIPS. Acts as a
 * container class for variables required to execute a instruction.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class RegisterInstruction implements Instruction {
	
    private InstructionString instructionString;
    private String opcode;
    private String function;
    private String name;
    private int shift;
    private int destination;
    private int source;
    private int target;

    public RegisterInstruction(InstructionString is) {
        this.instructionString = is;
        this.opcode = is.parseOpcode();
        this.function = is.parseFunction();
        this.shift = is.parseShift();
        this.destination = is.parseDestination();
        this.source = is.parseSource();
        this.target = is.parseTarget();

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

    public String getFunction() { return this.function; }

    public String getName() { return this.name; }

    public int getDestination() { return this.destination; }

    public int getSource() {
        return this.source;
    }

    public int getTarget() {
        return this.target;
    }

    public int getShift() {
        return this.shift;
    }


    @Override
    public void determineName(int opcode) {
        int function = (int) Long.parseLong(this.function, 2);
        if (opcode == 0) {
            switch (function) {
                case 8:
                    this.name = "jr";
                    break;
                case 20:
                    this.name = "add";
                    break;
                case 21:
                    this.name = "addu";
                    break;
                case 24:
                    this.name = "and";
                    break;
                case 25:
                    this.name = "or";
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return this.name + " $s: " + this.source + ", $t: " + this.target + ", $d: " + this.destination;
    }

}
