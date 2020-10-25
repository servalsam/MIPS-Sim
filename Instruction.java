package mipssim.swain91.git;

/**
 * The base interface for {@link RegisterInstruction}, {@link ImmediateInstruction}, and
 * {@link JumpInstruction}. Determines what the basis components of an instruction in MIPS.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public interface Instruction {
	
    InstructionString getInstructionString();
    String getOpcode();
    String getName();
    void determineName(int opCode);
}
