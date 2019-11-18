package mipssim.swain91.git;

/**
 * Factory pattern-based class for {@link JumpInstruction} objects. 
 * Allows immediate instructions to determine for themselves their own characteristics.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class JumpInstructionFactory implements AbstractInstructionFactory{

    private InstructionString is;

    public JumpInstructionFactory(InstructionString is) {
        this.is = is;
    }

    @Override
    public Instruction createInstruction() {
        return new JumpInstruction(this.is);
    }
}
