package mipssim.swain91.git;

/**
 * Base interface for {@link RegisterInstructionFactory}, {@link ImmediateInstructionFactory},
 * and {@link JumpInstructionFactory}. 
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public interface AbstractInstructionFactory {

    public Instruction createInstruction();
}
