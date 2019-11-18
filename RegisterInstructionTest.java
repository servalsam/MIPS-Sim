package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 * Test class for the {@link RegisterInstruction} class.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class RegisterInstructionTest {

	@BeforeAll
	static void setup() {
		System.out.println("Conducting RegisterInstruction.java tests...");
	}
	
	@Test
	void shouldBeConstructed() {
		InstructionString is = new InstructionString("00000000000000000000000000001000");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("000000", test.getOpcode());
		assertEquals("001000", test.getFunction());
		assertEquals("jr", test.getName());
		assertEquals(0, test.getShift());
		assertEquals(0, test.getSource());
		assertEquals(0, test.getTarget());
		assertEquals(0, test.getDestination());
		assertEquals(is, test.getInstructionString());
	}
	
	@Test
	void shouldBeJumpRegister() {
		InstructionString is = new InstructionString("00000000000000000000000000001000");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("jr", test.getName());
	}
	
	@Test
	void shouldBeAdd() {
		InstructionString is = new InstructionString("00000000000000000000000000010100");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("add", test.getName());
	}
	
	@Test
	void shouldBeAddu() {
		InstructionString is = new InstructionString("00000000000000000000000000010101");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("addu", test.getName());
	}
	
	@Test
	void shouldBeAnd() {
		InstructionString is = new InstructionString("00000000000000000000000000011000");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("and", test.getName());
	}
	
	@Test
	void shouldBeOr() {
		InstructionString is = new InstructionString("00000000000000000000000000011001");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("or", test.getName());
	}
	
	@Test
	void shouldHaveNoNameInvalidFunc() {
		InstructionString is = new InstructionString("00000000000000000000000000111111");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals(null, test.getName());
	}
	
	@Test
	void shouldHaveNoNameInvalidOp() {
		InstructionString is = new InstructionString("00000100000000000000000000001000");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals(null, test.getName());
	}
	
	@Test
	void shouldOutputCorrectString() {
		InstructionString is = new InstructionString("00000000000000000000000000010100");
		RegisterInstruction test = new RegisterInstruction(is);
		
		assertEquals("add $s: 0, $t: 0, $d: 0", test.toString());
	}
}
