package mipssim.swain91.git;

/**
 * Delegates the object creation of {@link RegisterInstruction}, {@link ImmediateInstruction},
 * and {@link JumpInstruction} based on the given {@link InstructionString} object data.
 * Returns a new MIPS instruction based on the data.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class InstructionFactory {

    private InstructionFactory() {}

    private static class InstructionFactoryHelper {
        private static final InstructionFactory INSTANCE = new InstructionFactory();
    }

    public static InstructionFactory getInstance() {
        return InstructionFactoryHelper.INSTANCE;
    }

    public Instruction createInstruction(InstructionString is) {
        String type = is.getType();

        switch(type) {
            case "R":
                return new RegisterInstructionFactory(is).createInstruction();
            case "J":
                return new JumpInstructionFactory(is).createInstruction();
            default:
                return new ImmediateInstructionFactory(is).createInstruction();
        }
    }
}
