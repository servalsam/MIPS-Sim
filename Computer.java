package mipssim.swain91.git;

/**
 * Computer is the base class for the MIPS simulation and is responsible
 * for the following tasks:
 * <ul>
 * <li> Initialization of data memory and management of memory.
 * <li> Initialization of data registers and management of registers.
 * <li> Insertions of given instructions.
 * <li> Keeping track of the next instruction.
 * <li> Updating the instruction pointer.
 * <li> Handling of logical and arithmetic operations.
 * <li> Displaying output to the console.
 * </ul>
 * <p>
 * The fetch method preludes any operation. The decode and execute follows.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public final class Computer {

    private final static int MAX_MEMORY = 64;
    private final static int MAX_REGISTERS = 32;
    private int pc = 0;
    private int ic = 0;

    private Instruction instructions[];
    private Instruction ir;
    private int[] gpr;
    private int[] mem;

    public Computer() {
        instructions = new Instruction[MAX_MEMORY];
        mem = new int[MAX_MEMORY];
        gpr = new int[MAX_REGISTERS];
    };

    /**
     * Fetches the next instruction from the instructions list with
     * the current instruction pointer, then increments the pointer (PC+1).
     */
    void fetch() {
        ir = instructions[pc];
        pc++;
    }

    int getInstructionCount() {
        return ic;
    }

    int getPc() {
        return this.pc;
    }
    
    Instruction getIr() {
    	return this.ir;
    }
    
    int[] getGpr() {
    	return this.gpr;
    }
    
    int[] getMem() {
    	return this.mem;
    }
    
    Instruction[] getInstructions() {
    	return this.instructions;
    }

    /**
     * Inserts a new instruction at the given instruction counter
     * position in the instructions list, then increments the counter.
     * 
     * @param newInstruction	the new instruction being inserted into the list
     */
    void insertInstruction(Instruction newInstruction) {
        instructions[ic] = newInstruction;
        ic++;
    }
    
    /**
     * Updates the instruction pointer to a new value.
     * 
     * @param newPc		the new instruction pointer as <code>int</code>
     */
    void setPc(int newPc) {
    	this.pc = newPc;
    }

    /**
     * Given an {@link InstructionString}, simulates the decode phase in
     * the MIPS architecture, where the object is parsed for its instruction
     * opcode.
     * 
     * @param is	the instruction string that needs to be decoded
     * @return 		the decoded value as an <code>int</code>
     */
    int decode(InstructionString is) {
        return (int) Long.parseLong(is.parseOpcode(), 2);
    }

    /**
     * The execute method simulates the execution phase in the MIPS
     * architecture, where the decode phase preludes execution to determine
     * the operation to be performed. The proper operation is 
     * executed based on the value returned from the decode method.
     * 
     * <p>
     * If the decode method returns 0, then an additional function integer is
     * required as several {@link RegisterInstruction} objects share the same
     * opcode.
     */
    void execute() {
        int opcode = decode(ir.getInstructionString());

        if (opcode == 0) {
            String functionString = ((RegisterInstruction) ir).getFunction();
            int function = ir.getInstructionString().twosComplement(functionString);
            switch (function) {
                case 8:
                    jr((RegisterInstruction) ir);
                    break;
                case 20:
                    add((RegisterInstruction) ir);
                    break;
                case 21:
                    addu((RegisterInstruction) ir);
                    break;
                case 24:
                    and((RegisterInstruction) ir);
                    break;
                case 25:
                    or((RegisterInstruction) ir);
                    break;
            }
        }
        switch(opcode) {
            case 2:
                jump((JumpInstruction) ir);
                break;
            case 4:
                beq((ImmediateInstruction) ir);
                break;
            case 5:
                bne((ImmediateInstruction) ir);
                break;
            case 8:
                addi((ImmediateInstruction) ir);
                break;
            case 9:
                addiu((ImmediateInstruction) ir);
                break;
            case 12:
                andi((ImmediateInstruction) ir);
                break;
            case 13:
                ori((ImmediateInstruction) ir);
                break;
            case 23:
                lw((ImmediateInstruction) ir);
                break;
            case 43:
                sw((ImmediateInstruction) ir);
                break;
            default:
                break;
        }
    }

    /**
     * Given a source, target, and destination registers in
     * {@link RegisterInstruction}, performs 'signed addition' with
     * the give source and target registers and stores it into the
     * destination register.
     * 
     * @param instruction	the {@link RegisterInstruction} containing register data and add opcode
     */
    void add(RegisterInstruction instruction) {
        int temp1 = gpr[instruction.getSource()];
        int temp2 = gpr[instruction.getTarget()];
        int sum = temp1 + temp2;
        gpr[instruction.getDestination()] = sum;
    }

    /**
     * Given a source, target, and destination registers in
     * {@link RegisterInstruction}, performs 'unsigned addition' with
     * the give source and target registers and stores it into the
     * destination register.
     * 
     * @param instruction	the {@link RegisterInstruction} containing register data and addu opcode
     */
    void addu(RegisterInstruction instruction) {
        long temp1 = gpr[instruction.getSource()] & 0x00000000ffffffffL;
        long temp2 = gpr[instruction.getTarget()] & 0x00000000ffffffffL;
        gpr[instruction.getDestination()] = (int) (temp1 + temp2);
    }

    /**
     * Given a source, target, and destination registers in
     * {@link RegisterInstruction}, performs 'binary and' with
     * the give source and target registers and stores it into the
     * destination register.
     * 
     * @param instruction	the {@link RegisterInstruction} containing register data and and opcode
     */
    void and(RegisterInstruction instruction) {
        gpr[instruction.getDestination()] =
                gpr[instruction.getSource()] &
                        gpr[instruction.getTarget()];
    }

    /**
     * Given a source, target, and destination registers in
     * {@link RegisterInstruction}, performs 'binary or' operation with
     * the give source and target registers and stores it into the
     * destination register.
     * 
     * @param instruction	the {@link RegisterInstruction} containing register data and or opcode
     */
    void or(RegisterInstruction instruction) {
        gpr[instruction.getDestination()] =
                gpr[instruction.getSource()] |
                        gpr[instruction.getTarget()];
    }

    /**
     * Given a source register in {@link RegisterInstruction}, 
     * performs a 'registry jump' with the given source register value
     * and sets the instruction pointer. 
     * 
     * <p>
     * The value of the address must be at least equal to the first instruction
     * count address and less than the last for a valid jump.
     * 
     * @param instruction	the {@link RegisterInstruction} containing register data and jr opcode
     */
    void jr(RegisterInstruction instruction) {
        int address = gpr[instruction.getSource()];

        if (address >= 0 && address < ic)
            pc = address;
    }

    /**
     * Given a immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'add immediate' operation of
     * the source register's value and the immediate value
     * and stores the value in target register.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and addi opcode
     */
    void addi(ImmediateInstruction instruction) {
        gpr[instruction.getTarget()] = gpr[instruction.getSource()] + instruction.getImmediateSigned();
    }

    /**
     * Given an immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'add immediate unsigned' operation of
     * the source register's value and the immediate value
     * and stores the value in target register.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and addiu opcode
     */
    void addiu(ImmediateInstruction instruction) {
        long temp1 = gpr[instruction.getSource()];
        String stringtemp = Long.toUnsignedString(temp1, 2);
        temp1 = Long.parseUnsignedLong(stringtemp, 2);
        long temp2 = instruction.getImmediateUnsigned();
        gpr[instruction.getTarget()] = (int) (temp1 + temp2);
    }

    /**
     * Given an immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'and immediate' operation of
     * the source register's value and the immediate value
     * and stores the value in target register.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and andi opcode
     */
    void andi(ImmediateInstruction instruction) {
        gpr[instruction.getTarget()] = gpr[instruction.getSource()] & instruction.getImmediateSigned();
    }

    /**
     * Given an immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'or immediate' operation of
     * the source register's value and the immediate value
     * and stores the value in target register.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and ori opcode
     */
    void ori(ImmediateInstruction instruction) {
        gpr[instruction.getTarget()] = gpr[instruction.getSource()] | instruction.getImmediateSigned();
    }

    /**
     * Given an immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'load word' operation. The
     * source value and immediate value are added to reach a location in memory's value,
     * that value is loaded into a target register.
     * 
     * <p>
     * The value of the memory location must not exceed the bounds of memory.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and lw opcode
     */
    void lw(ImmediateInstruction instruction) {
    	int address = gpr[instruction.getSource()] + instruction.getImmediateSigned();
    	if (address >= 0 && address < mem.length)
    		gpr[instruction.getTarget()] = mem[address];
    	
    }

    /**
     * Given an immediate value, target register, and source register from
     * {@link ImmediateInstruction}, performs the 'save word' operation. The
     * source value and immediate value are added to reach a location in memory's value,
     * that value of the target register is loaded into the memory location.
     * 
     * <p>
     * The value of the memory location must not exceed the bounds of memory.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and sw opcode
     */
    void sw(ImmediateInstruction instruction) {
    	int address = gpr[instruction.getSource()] + instruction.getImmediateSigned();
    	if (address >= 0 && address < mem.length)
    		mem[address] = gpr[instruction.getTarget()];
    }

    /**
     * Given an immediate value, source register, and target register from
     * {@link ImmediateInstruction}, if the value at the source register
     * equals the value at the target register, then the instruction pointer
     * is set with the immediate value.
     * 
     * <p>
     * The address from the immediate value must not exceed the index of the first
     * instruction on the list or the max instruction's index.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and beq opcode
     */
    void beq(ImmediateInstruction instruction) {
        int address = instruction.getImmediateSigned();
        if (gpr[instruction.getSource()] == gpr[instruction.getTarget()]) {
        	if (address >= 0 && address < ic) {
                pc = address;
            }
        }
    }

    /**
     * Given an immediate value, source register, and target register from
     * {@link ImmediateInstruction}, if the value at the source register
     * does not the value at the target register, then the instruction pointer
     * is set with the immediate value.
     * 
     * <p>
     * The address from the immediate value must not exceed the index of the first
     * instruction on the list or the max instruction's index.
     * 
     * @param instruction	the {@link ImmediateInstruction} containing register data, an immediate value, and bne opcode
     */
    void bne(ImmediateInstruction instruction) {
        int address = instruction.getImmediateSigned();
        if (address >= 0 && address < ic) {
            if (gpr[instruction.getSource()] != gpr[instruction.getTarget()]) {
                pc = address;
            }
        }
    }

    /**
     * Give a jump address from {@link JumpInstruction}, sets the instruction pointer
     * with the given address.
     * 
     * <p>
     * The address from the immediate value must not exceed the index of the first
     * instruction on the list or the max instruction's index.
     * 
     * @param instruction	the {@link JumpInstruction} containing a jump address and j opcode
     */
    void jump(JumpInstruction instruction) {
        int address = instruction.getJumpAddress();
        if (address >= 0 && address < ic)
            pc = address;

    }

    /**
     * Displays the data in the registers.
     * 
     * @return	the register data as a <code>String</code>.
     */
    String displayRegisters() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < gpr.length; i ++) {
            if (i % 8 == 0 && i !=0) {
                sb.append("\n");
            }
            if(i < gpr.length - 1) {
                sb.append(gpr[i]);
                sb.append(", ");
            } else {
                sb.append(gpr[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Displays the instruction list.
     * 
     * @return	the instruction list as a <code>String</code>.
     */
    String displayInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < ic; i ++) {
            if(i < ic - 1) {
                sb.append(instructions[i]);
                sb.append(", \n");
            } else {
                sb.append(instructions[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Displays the memory data elements.
     * 
     * @return	the memory data as a <code>String</code>.
     */
    String displayMemory() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < mem.length; i ++) {
            if (i % 8 == 0 && i !=0) {
                sb.append("\n");
            }
            if(i < mem.length - 1) {
                sb.append(mem[i]);
                sb.append(", ");
            } else {
                sb.append(mem[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
