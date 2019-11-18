package mipssim.swain91.git;

/**
 * Factory pattern-based class for {@link ImmediateInstruction} objects. 
 * Allows immediate instructions to determine for themselves their own characteristics.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class ImmediateInstructionFactory implements AbstractInstructionFactory {

    private InstructionString is;

    public ImmediateInstructionFactory(InstructionString is) {
        this.is = is;
    }

    @Override
    public Instruction createInstruction() {
        return new ImmediateInstruction(this.is);
    }
}
