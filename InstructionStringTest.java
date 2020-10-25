package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

/**
 * Test class for the {@link InstructionString} class.
 * 
 * @author Samuel Servane
 * @version 1.3
 */
public class InstructionStringTest {

	@BeforeAll
	static void setup() {
		System.out.println("Conducting InstructionString.java tests...");
	}
	
	@Test
	void shouldHaveBitString() {
		InstructionString test = new InstructionString("00100100000000000000000000001010");
		
		assertEquals("00100100000000000000000000001010", test.getBits());
	}
	
	@Test
	void shouldParseOpcode() {
		InstructionString test = new InstructionString("00100100000000000000000000001010");
		
		assertEquals("001001", test.parseOpcode());
	}
	
	@Test
	void shouldParseFunction( ) {
		InstructionString test = new InstructionString("00100100000000000000000000001010");
		
		assertEquals("001010", test.parseFunction());
	}
	
	@Test
	void shouldParseJumpAddress() {
		InstructionString test = new InstructionString("00001000000000000000000000000110");
		
		assertEquals(6, test.parseJumpAddress());
	}
	
	@Test
	void shouldParseImmediateSignedPositive() {
		InstructionString test = new InstructionString("00100100000000000000000000001010");
		
		assertEquals(10, test.parseImmediateSigned());
	}
	
	@Test
	void shouldParseImmediateSignedNegative() {
		InstructionString test = new InstructionString("00100100000000001111111111110110");
		
		assertEquals(-10, test.parseImmediateSigned());
	}
	
	@Test
	void shouldParseImmediateUnsigned() {
		InstructionString test = new InstructionString("00100100000000001111111111111111");
		
		assertEquals(65535, test.parseImmediateUnsigned());
	}
	
	@Test
	void shouldDetermineIfNegative() {
		InstructionString test = new InstructionString("00100100000000001111111111111111");
		
		assertEquals(true, test.determineNegative(test.getBits()));
	}
	
	@Test
	void shouldDetermineIfNotNegative() {
		InstructionString test = new InstructionString("00100100000000000111111111111111");
		
		assertEquals(false, test.determineNegative(test.getBits()));
	}
	
	@Test
	void shouldBeTypeR() {
		InstructionString[] instructions = {
				new InstructionString("00000000000000000000000000001000"),
				new InstructionString("00000000000000000000000000010100"),
				new InstructionString("00000000000000000000000000010101"),
				new InstructionString("00000000000000000000000000011000"),
				new InstructionString("00000000000000000000000000011001")
				
		};
		
		for (int i = 0; i < instructions.length; i++) {
			assertEquals("R", instructions[i].getType());
		}
	}
	
	@Test
	void shouldBeTypeI() {
		InstructionString[] instructions = {
				new InstructionString("00010000000000000000000000000001"),
				new InstructionString("00010100000000000000000000000001"),
				new InstructionString("00100000000000000000000000000001"),
				new InstructionString("00100100000000000000000000000001"),
				new InstructionString("00110000000000000000000000000001"),
				new InstructionString("00110100000000000000000000000001"),
				new InstructionString("01011100000000000000000000000001"),
				new InstructionString("10101100000000000000000000000001")
				
		};
		
		for (int i = 0; i < instructions.length; i++) {
			assertEquals("I", instructions[i].getType());
		}
	}
	
	@Test
	void shouldBeTypeJ() {
		InstructionString test = new InstructionString("00001000000000000000000000000001");
		
		assertEquals("J", test.getType());
	}
	
	@Test
	void shouldParseSource() {
		InstructionString test = new InstructionString("00000011111000000000000000001000");
		
		assertEquals(31, test.parseSource());
	}
	
	@Test
	void shouldParseTarget() {
		InstructionString test = new InstructionString("00000000000111110000000000001000");
		
		assertEquals(31, test.parseTarget());
	}
	
	@Test
	void shouldParseDestination() {
		InstructionString test = new InstructionString("00000000000000001111100000001000");
		
		assertEquals(31, test.parseDestination());
	}
	
	@Test
	void shouldParseShift() {
		InstructionString test = new InstructionString("00000000000000000000011111001000");
		
		assertEquals(31, test.parseShift());
	}
}
