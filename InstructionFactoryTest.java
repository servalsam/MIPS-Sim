package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link InstructionFactory} class.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class InstructionFactoryTest {
	
	@BeforeAll
	static void setup() {
		
	}
	
	@Test
	void shouldCreateRegisterInstruction() {
		InstructionFactory testFactory = InstructionFactory.getInstance();
		InstructionString testString = new InstructionString("00000000000000000000000000001000");
		
		assertTrue(testFactory.createInstruction(testString) instanceof RegisterInstruction);
	}
	
	@Test
	void shouldCreateJumpInstruction() {
		InstructionFactory testFactory = InstructionFactory.getInstance();
		InstructionString testString = new InstructionString("00001000000000000000000000001000");
		
		assertTrue(testFactory.createInstruction(testString) instanceof JumpInstruction);
	}
	
	@Test
	void shouldCreateImmediateInstruction() {
		InstructionFactory testFactory = InstructionFactory.getInstance();
		InstructionString testString = new InstructionString("00010000000000000000000000000001");
		
		assertTrue(testFactory.createInstruction(testString) instanceof ImmediateInstruction);
	}
}
