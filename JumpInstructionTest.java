package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 * Test class for the {@link JumpInstruction} class
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class JumpInstructionTest {

	@BeforeAll
	static void setup() {
		System.out.println("Conducting JumpInstruction.java tests...");
	}
	
	@Test
	void shouldBeConstructed() {
		InstructionString is = new InstructionString("00001000000000000000000000001000");
		JumpInstruction test = new JumpInstruction(is);
		
		assertEquals("000010", test.getOpcode());
		assertEquals("jump", test.getName());
		assertEquals(is, test.getInstructionString());
		assertEquals(8, test.getJumpAddress());
	}
	
	@Test
	void shouldBeJump() {
		InstructionString is = new InstructionString("00001000000000000000000000001000");
		JumpInstruction test = new JumpInstruction(is);
		
		assertEquals("jump", test.getName());
	}
	
	@Test
	void shouldNotHaveName() {
		InstructionString is = new InstructionString("00000000000000000000000000001000");
		JumpInstruction test = new JumpInstruction(is);
		
		assertEquals(null, test.getName());
	}
	
	@Test
	void shouldOutputString() {
		InstructionString is = new InstructionString("00001000000000000000000000001000");
		JumpInstruction test = new JumpInstruction(is);
		
		assertEquals("jump address: 8", test.toString());
	}
}
