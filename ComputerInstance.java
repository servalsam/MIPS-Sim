package mipssim.swain91.git;

/**
 * ComputerInstance is a puppeteering class that prevents instantiation
 * of the Computer class when utilized as a Singleton design pattern.
 * 
 * <p>
 * All of the methods from {@link Computer} class are utilized by
 * ComputerInstance.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public enum ComputerInstance {
	
    // Prevent instantiation of Computer class
	INSTANCE;
	private final Computer computer = new Computer();
    
	/**
	 * {@link Computer#fetch()}
	 */
    public void fetch() {
        computer.fetch();
    }

    public int getInstructionCount() {
        return computer.getInstructionCount();
    }

    public int getPc() {
        return computer.getPc();
    }
    
    public Instruction getIr() {
    	return computer.getIr();
    }

    /**
     * {@link Computer#insertInstruction(Instruction newInstruction)}
     */
    public void insertInstruction(Instruction newInstruction) {
        computer.insertInstruction(newInstruction);
    }

    /**
     * {@link Computer#decode(InstructionString is)}
     */
    public int decode(InstructionString is) {
        return computer.decode(is);
    }

    /**
     * {@link Computer#execute()}
     */
    public void execute() {
        int opcode = decode(computer.getIr().getInstructionString());

        if (opcode == 0) {
            String functionString = ((RegisterInstruction) computer.getIr()).getFunction();
            int function = computer.getIr().getInstructionString().twosComplement(functionString);
            switch (function) {
                case 8:
                    jr((RegisterInstruction) computer.getIr());
                    break;
                case 20:
                    add((RegisterInstruction) computer.getIr());
                    break;
                case 21:
                    addu((RegisterInstruction) computer.getIr());
                    break;
                case 24:
                    and((RegisterInstruction) computer.getIr());
                    break;
                case 25:
                    or((RegisterInstruction) computer.getIr());
                    break;
            }
        }
        switch(opcode) {
            case 2:
                jump((JumpInstruction) computer.getIr());
                break;
            case 4:
                beq((ImmediateInstruction) computer.getIr());
                break;
            case 5:
                bne((ImmediateInstruction) computer.getIr());
                break;
            case 8:
                addi((ImmediateInstruction) computer.getIr());
                break;
            case 9:
                addiu((ImmediateInstruction) computer.getIr());
                break;
            case 12:
                andi((ImmediateInstruction) computer.getIr());
                break;
            case 13:
                ori((ImmediateInstruction) computer.getIr());
                break;
            case 23:
                lw((ImmediateInstruction) computer.getIr());
                break;
            case 43:
                sw((ImmediateInstruction) computer.getIr());
                break;
            default:
                break;
        }
    }

    /**
     * {@link Computer#add(RegisterInstruction)}
     */
    public void add(RegisterInstruction instruction) {
        int temp1 = computer.getGpr()[instruction.getSource()];
        int temp2 = computer.getGpr()[instruction.getTarget()];
        int sum = temp1 + temp2;
        computer.getGpr()[instruction.getDestination()] = sum;
    }

    /**
     * {@link Computer#addu(RegisterInstruction)}
     */
    public void addu(RegisterInstruction instruction) {
        long temp1 = computer.getGpr()[instruction.getSource()] & 0x00000000ffffffffL;
        long temp2 = computer.getGpr()[instruction.getTarget()] & 0x00000000ffffffffL;
        computer.getGpr()[instruction.getDestination()] = (int) (temp1 + temp2);
    }

    /**
     * {@link Computer#and(RegisterInstruction)}
     */
    public void and(RegisterInstruction instruction) {
        computer.getGpr()[instruction.getDestination()] =
                computer.getGpr()[instruction.getSource()] &
                        computer.getGpr()[instruction.getTarget()];
    }

    /**
     * {@link Computer#or(RegisterInstruction)}
     */
    public void or(RegisterInstruction instruction) {
        computer.getGpr()[instruction.getDestination()] =
                computer.getGpr()[instruction.getSource()] |
                        computer.getGpr()[instruction.getTarget()];
    }

    /**
     * {@link Computer#jr(RegisterInstruction)}
     */
    public void jr(RegisterInstruction instruction) {
        int address = instruction.getSource();
        if (address >= 0 && address < computer.getInstructionCount())
            computer.setPc(address);
    }

    /**
     * {@link Computer#addi(ImmediateInstruction)}
     */
    public void addi(ImmediateInstruction instruction) {
        computer.getGpr()[instruction.getTarget()] = computer.getGpr()[instruction.getSource()] + instruction.getImmediateSigned();
    }

    /**
     * {@link Computer#addiu(ImmediateInstruction)}
     */
    public void addiu(ImmediateInstruction instruction) {
        long temp1 = computer.getGpr()[instruction.getSource()] & 0x00000000ffffffffL;
        long temp2 = instruction.getImmediateUnsigned() & 0x00000000ffffffffL;
        computer.getGpr()[instruction.getTarget()] = (int) (temp1 + temp2);
    }

    /**
     * {@link Computer#andi(ImmediateInstruction)}
     */
    public void andi(ImmediateInstruction instruction) {
        computer.getGpr()[instruction.getTarget()] = computer.getGpr()[instruction.getSource()] & instruction.getImmediateSigned();
    }

    /**
     * {@link Computer#ori(ImmediateInstruction)}
     */
    public void ori(ImmediateInstruction instruction) {
        computer.getGpr()[instruction.getTarget()] = computer.getGpr()[instruction.getSource()] | instruction.getImmediateSigned();
    }

    /**
     * {@link Computer#lw(ImmediateInstruction)}
     */
    public void lw(ImmediateInstruction instruction) {
    	int address = computer.getGpr()[instruction.getSource()] + instruction.getImmediateSigned();
    	if (address >= 0 && address < computer.getMem().length)
    		computer.getGpr()[instruction.getTarget()] = computer.getMem()[instruction.getSource() + instruction.getImmediateSigned()];
    }


    /**
     * {@link Computer#sw(ImmediateInstruction)}
     */
    public void sw(ImmediateInstruction instruction) {
    	int address = computer.getGpr()[instruction.getSource()] + instruction.getImmediateSigned();
    	if (address >= 0 && address < computer.getMem().length)
    		computer.getMem()[instruction.getSource() + instruction.getImmediateSigned()] = computer.getGpr()[instruction.getTarget()];
    }


    /**
     * {@link Computer#beq(ImmediateInstruction)}
     */
    public void beq(ImmediateInstruction beqInstruction) {
        int address = beqInstruction.getImmediateSigned();
        if (address >= 0 && address < computer.getInstructionCount()) {
            if (computer.getGpr()[beqInstruction.getSource()] == computer.getGpr()[beqInstruction.getTarget()]) {
                computer.setPc(address);
            }
        }
    }


    /**
     * {@link Computer#bne(ImmediateInstruction)}
     */
    public void bne(ImmediateInstruction bneInstruction) {
        int address = bneInstruction.getImmediateSigned();
        if (address >= 0 && address < computer.getInstructionCount()) {
            if (computer.getGpr()[bneInstruction.getSource()] != computer.getGpr()[bneInstruction.getTarget()]) {
                computer.setPc(address);
            }
        }
    }


    /**
     * {@link Computer#jump(JumpInstruction)}
     */
    public void jump(JumpInstruction jInstruction) {
        int address = jInstruction.getJumpAddress();
        if (address >= 0 && address < computer.getInstructionCount())
            computer.setPc(address);

    }


    /**
     * {@link Computer#displayRegisters()}
     */
    public void displayRegisters() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < computer.getGpr().length; i ++) {
            if (i % 8 == 0 && i !=0) {
                sb.append("\n");
            }
            if(i < computer.getGpr().length - 1) {
                sb.append(computer.getGpr()[i]);
                sb.append(", ");
            } else {
                sb.append(computer.getGpr()[i]);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }


    /**
     * {@link Computer#displayInstructions()}
     */
    public void displayInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < computer.getInstructionCount(); i ++) {
            if(i < computer.getInstructionCount() - 1) {
                sb.append(computer.getInstructions()[i]);
                sb.append(", \n");
            } else {
                sb.append(computer.getInstructions()[i]);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }


    /**
     * {@link Computer#displayMemory()}
     */
    public void displayMemory() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < computer.getMem().length; i ++) {
            if (i % 8 == 0 && i !=0) {
                sb.append("\n");
            }
            if(i < computer.getMem().length - 1) {
                sb.append(computer.getMem()[i]);
                sb.append(", ");
            } else {
                sb.append(computer.getMem()[i]);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}
