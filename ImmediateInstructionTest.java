package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ImmediateInstruction} class.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class ImmediateInstructionTest {

	
	@BeforeAll
	static void setup() {
		System.out.println("Conducting ImmediateInstruction tests...");
	}
	
	@Test
	void shouldBeConstructed() {
		InstructionString is = new InstructionString("00010000000000000000000000000001");
		ImmediateInstruction test = new ImmediateInstruction(is);

		assertEquals("000100", test.getOpcode());
		assertEquals("beq", test.getName());
		assertEquals(1, test.getImmediateSigned());
		assertEquals(1, test.getImmediateUnsigned());
		assertEquals(0, test.getSource());
		assertEquals(0, test.getTarget());
		assertEquals(is, test.getInstructionString());
	}
	
	@Test
	void shouldBeUnsigned() {
		InstructionString is = new InstructionString("00010000000000001111111111111111");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals(65535, test.getImmediateUnsigned());
	}
	
	@Test
	void shouldBeSigned() {
		InstructionString is = new InstructionString("00010000000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals(-2, test.getImmediateSigned());
	}
	
	@Test
	void shouldBeBeq() {
		InstructionString is = new InstructionString("00010000000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("beq", test.getName());
	}
	
	@Test
	void shouldBeBne() {
		InstructionString is = new InstructionString("00010100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("bne", test.getName());
	}
	
	@Test
	void shouldBeAddi() {
		InstructionString is = new InstructionString("00100000000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("addi", test.getName());
	}
	
	@Test
	void shouldBeAddiu() {
		InstructionString is = new InstructionString("00100100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("addiu", test.getName());
	}
	
	@Test
	void shouldBeAndi() {
		InstructionString is = new InstructionString("00110000000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("andi", test.getName());
	}
	
	@Test
	void shouldBeOri() {
		InstructionString is = new InstructionString("00110100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("ori", test.getName());
	}
	
	@Test
	void shouldBeLw() {
		InstructionString is = new InstructionString("01011100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("lw", test.getName());
	}
	
	@Test
	void shouldBeSw() {
		InstructionString is = new InstructionString("10101100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("sw", test.getName());
	}
	
	@Test
	void shouldNotHaveNameInvalidOp() {
		InstructionString is = new InstructionString("00000000000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals(null, test.getName());
	}
	
	@Test
	void shouldOutputString() {
		InstructionString is = new InstructionString("10101100000000001111111111111110");
		ImmediateInstruction test = new ImmediateInstruction(is);
		
		assertEquals("sw $s: 0, $t: 0, i: -2", test.toString());
	}
}
