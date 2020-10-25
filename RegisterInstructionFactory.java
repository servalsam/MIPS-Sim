package mipssim.swain91.git;

/**
 * Factory pattern-based class for {@link RegisterInstruction} objects. 
 * Allows immediate instructions to determine for themselves their own characteristics.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class RegisterInstructionFactory implements AbstractInstructionFactory {

    private InstructionString is;

    RegisterInstructionFactory(InstructionString is) {
        this.is = is;
    }

    @Override
    public Instruction createInstruction() {
        return new RegisterInstruction(this.is);
    }
}
