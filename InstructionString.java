package mipssim.swain91.git;
import java.util.Arrays;

/**
 * The container class for binary string data. Parses data in the following ways:
 * 
 * <ul>
 * <li> Extracts the op code, function code, and register data for {@link RegisterInstruction}.
 * <li> Extracts the opcode, register data, and immediate value for {@link ImmediateInstruction}.
 * <li> Extracts the opcode and jump address for {@link JumpInstruction}.
 * <li> Creates unsigned and signed data values.
 * <li> Peforms sign extension when required.
 * </ul>
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class InstructionString {

    private String bits;
    private String type;

    public InstructionString(String newBits) {
        this.bits = newBits;
        determineType(parseOpcode());
    }

    public String parseOpcode() {
        return this.bits.substring(0, 6);
    }

    public String parseFunction() { return this.bits.substring(26, 32); }

    public int parseJumpAddress() {
        return (int) twosComplement(slice(this.bits.substring(6, 32)));
    }

    public int parseImmediateSigned() {
        return twosComplement(slice(this.bits.substring(16, 32)));
    }

    public int parseImmediateUnsigned() {
        return (int) Long.parseUnsignedLong(this.bits.substring(16, 32), 2);
    }

    public int twosComplement(String bits) {
        return (int) Long.parseLong(signExtend(bits), 2);
    }

    public boolean determineNegative(String bits) {
        return (bits.charAt(16) == '1');
    }

    public int parseSource() {
        return (int) Long.parseLong(bits.substring(6, 11), 2);
    }

    public int parseTarget() {
        return (int) Long.parseLong(bits.substring(11, 16), 2);
    }

    public int parseDestination() {
        return (int) Long.parseLong(bits.substring(16, 21), 2);
    }

    public int parseShift() {
        return (int) Long.parseLong(bits.substring(21, 26), 2);
    }

    public String getBits() { return this.bits; }

    public String getType() {
        return this.type;
    }

    private void determineType(String opcode) {
        int op = (int) Long.parseLong(opcode, 2);

        switch (op) {
            case 0:
                this.type = "R";
                break;
            case 2:
                this.type = "J";
                break;
            default:
                this.type = "I";
        }
    }

    public String signExtend(String bits) {
        int n = 32 - bits.length();
        char[] ext = new char[n];
        Arrays.fill(ext, bits.charAt(0));

        return new String(ext) + bits;
    }

    private String slice(String bits) {
        int n = 1;
        int index = 1;
        while(bits.charAt(index) != '1' && index < bits.length() - 1) {
            index++;
            n++;
        }

        return bits.charAt(0) + bits.substring(n, bits.length());
    }
}
