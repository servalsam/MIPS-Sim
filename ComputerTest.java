package mipssim.swain91.git;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Computer} and {@link ComputerInstance} classes.
 * 
 * <p>
 * ComputerInstance cannot be tested without Dependency Injection, however it
 * utilizes all of the same methods on a Computer object.
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public class ComputerTest {
	
	@BeforeAll
	static void setup() {
		System.out.println("Conducting Computer.java tests...");
	}
	
	@Test
	void shouldBeConstructed() {
		Computer test = new Computer();
		
		assertEquals(0, test.getInstructionCount());
		assertEquals(0, test.getPc());
		assertArrayEquals(new int[32], test.getGpr());
		assertArrayEquals(new int[64], test.getMem());
		assertArrayEquals(new Instruction[64], test.getInstructions());
	}
	
	@Test
	void shouldFetchNextInstruction() {
		Computer test = new Computer();
		Instruction instruct = (Instruction) new ImmediateInstruction(new InstructionString("00100000000000000000000000000001"));
		test.insertInstruction(instruct);
		test.fetch();
		
		assertEquals(instruct, test.getIr());
	}
	
	@Test
	void shouldUpdatePcCount() {
		Computer test = new Computer();
		
		test.fetch();
		
		assertEquals(1, test.getPc());
	}
	
	@Test
	void shouldSetPc() {
		Computer test = new Computer();
		test.fetch();
		test.setPc(0);
		
		assertEquals(0, test.getPc());
	}
	
	@Test
	void shouldDecode() {
		Computer test = new Computer();
		InstructionString instruct = new InstructionString("00000000000000000000000000010100");
		
		assertEquals(0, test.decode(instruct));
	}
	
	@Test
	void shouldExectuteJumpRegister() {
		Computer test = new Computer();
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000000000000000001000"));
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		
		assertEquals(regtest, test.getIr());
		assertEquals(0, test.getPc());
	}
	
	@Test
	void shouldNotExectuteJrNegativeValue() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000001111111111111111"));
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000000000000000001000"));
		test.insertInstruction(immedtest);
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(-1, test.getGpr()[0]);
		assertEquals(2, test.getPc());

	}
	
	@Test
	void shouldNotExectuteJrOutOfBounds() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000000000000000001000"));
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000000000000000001000"));
		test.insertInstruction(immedtest);
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(8, test.getGpr()[0]);
		assertEquals(2, test.getPc());

	}
	
	
	@Test
	void shouldExectuteAdd() {
		Computer test = new Computer();
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000000000000000010100"));
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		
		assertEquals(regtest, test.getIr());
		assertEquals(0, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteAddu() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000000000000000001000"));
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000000000000000010101"));
		test.insertInstruction(immedtest);
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(16, test.getGpr()[0]);
	}
	
	@Test
	void shouldExectureAnd() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000000000000000001000"));
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000010000100000011000"));
		test.insertInstruction(immedtest);
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(0, test.getGpr()[1]);
	}
	
	@Test
	void shouldExecuteOr() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000000000000000001000"));
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000010000100000011001"));
		test.insertInstruction(immedtest);
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(8, test.getGpr()[1]);
	}
	
	@Test
	void shouldExecuteNoRegisterInstruction() {
		Computer test = new Computer();
		RegisterInstruction regtest = new RegisterInstruction(new InstructionString("00000000000000010000100000111111"));
		test.insertInstruction(regtest);
		test.fetch();
		test.execute();
		
		assertEquals(null, test.getIr().getName());
	}
	
	@Test
	void shouldExecuteJump() {
		Computer test = new Computer();
		JumpInstruction jumptest = new JumpInstruction(new InstructionString("00001000000000000000000000000000"));
		test.insertInstruction(jumptest);
		test.fetch();
		test.execute();
		
		assertEquals(0, test.getPc());
	}
	
	@Test
	void shouldNotExecuteJumpOutOfBounds() {
		Computer test = new Computer();
		JumpInstruction jumptest = new JumpInstruction(new InstructionString("00001000000000000000000000000011"));
		test.insertInstruction(jumptest);
		test.fetch();
		test.execute();
		
		assertEquals(1, test.getPc());
	}
	
	@Test
	void shouldNotExecuteJumpNegativeValue() {
		Computer test = new Computer();
		JumpInstruction jumptest = new JumpInstruction(new InstructionString("00001011111111111111111111111111"));
		test.insertInstruction(jumptest);
		test.fetch();
		test.execute();
		
		assertEquals(1, test.getPc());
	}
	
	//beq here
	@Test
	void shouldExecuteBeq() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000001"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010000000000010000000000000000"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(0, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBeqNotEqual() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000011"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010000000000010000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBeqInvalidAddress() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000001"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010000000000010000000000000111"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBeqNegativeAddress() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000001"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010000000000011000000000000111"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	//bne here
	
	@Test
	void shouldExecuteBne() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000010"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010100000000010000000000000000"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(0, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBneNotEqual() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000001"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010100000000010000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBneInvalidAddress() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000011"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010100000000010000000000000111"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	@Test
	void shouldNotExecuteBneNegativeAddress() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00100100001000010000000000000011"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("00010100000000011000000000000111"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		for (int i = 0; i < 3; i++) {
			test.fetch();
			test.execute();
		}
		
		assertEquals(3, test.getPc());
	}
	
	@Test
	void shouldExecuteAddi() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100000000000001111111111111111"));
		test.insertInstruction(immedtest);
		test.fetch();
		test.execute();
		
		assertEquals(-1, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteAddiu() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100100000000001111111111111111"));
		test.insertInstruction(immedtest);
		test.fetch();
		test.execute();
		
		assertEquals(65535, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteAndi() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100100000000001111111111111111"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00110000000000000000000000000000"));
		test.insertInstruction(immedtest);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		
		assertEquals(0, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteOri() {
		Computer test = new Computer();
		ImmediateInstruction immedtest = new ImmediateInstruction(new InstructionString("00100100000000001111111111111101"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("00110100000000000000000000000010"));
		test.insertInstruction(immedtest);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(65535, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteLw() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("10101100000000000000000000000001"));
		ImmediateInstruction immedtest3 = new ImmediateInstruction(new InstructionString("01011100000000000000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.insertInstruction(immedtest3);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(1, test.getGpr()[0]);
	}
	
	@Test
	void shouldNotExecuteLwMaxMemReached() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100000000000000000001111111111"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("01011100000000000000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(1023, test.getGpr()[0]);
	}
	
	@Test
	void shouldNotExecuteLwNegativeMem() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100000000000001111111111111111"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("01011100000000000000000000000000"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();

		
		assertEquals(-1, test.getGpr()[0]);
	}
	
	@Test
	void shouldExecuteSw() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("10101100000000000000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(0, test.getMem()[1]);
	}
	
	@Test
	void shouldExecuteSwMaxMemReached() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100000000000000000001111111111"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("10101100000000000000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(0, test.getMem()[1]);
	}
	
	@Test
	void shouldExecuteSwNegativeMemory() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100000000000001111111111111111"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("10101100000000000000000000000000"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		test.fetch();
		test.execute();
		test.fetch();
		test.execute();
		
		assertEquals(0, test.getMem()[1]);
	}
	
	@Test
	void shouldDisplayInstructions() {
		Computer test = new Computer();
		ImmediateInstruction immedtest1 = new ImmediateInstruction(new InstructionString("00100100000000000000000000000001"));
		ImmediateInstruction immedtest2 = new ImmediateInstruction(new InstructionString("10101100000000000000000000000001"));
		test.insertInstruction(immedtest1);
		test.insertInstruction(immedtest2);
		
		assertEquals("[addiu $s: 0, $t: 0, i: 1, \nsw $s: 0, $t: 0, i: 1]", test.displayInstructions());
		
	}
	
	@Test
	void shouldDisplayRegisters() {
		Computer test = new Computer();
		
		assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, \n"
						+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
						+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
						+ "0, 0, 0, 0, 0, 0, 0, 0]", test.displayRegisters());
	}
	
	@Test
	void shouldDisplayMemory() {
		Computer test = new Computer();
		
		assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0, \n"
				+ "0, 0, 0, 0, 0, 0, 0, 0]", test.displayMemory());
	}
}
