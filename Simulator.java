package mipssim.swain91.git;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver class that instantiates a MIPS simulator and an instruction loader (ROM BIOS).
 * 
 * @author Samuel Wainright
 * @version 1.3
 */
public final class Simulator {
	
    public static void main(String[] args) {
    	
        ComputerInstance computer = ComputerInstance.INSTANCE;
        InstructionFactory instructionFactory = InstructionFactory.getInstance();

        try {
            File file = new File("src/instructions.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                computer.insertInstruction(instructionFactory.createInstruction(new InstructionString(sc.nextLine())));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        computer.fetch();
        System.out.println();
        System.out.println("Instructions:");
        computer.displayInstructions();
        while (computer.getPc() < computer.getInstructionCount()) {
            computer.execute();
            computer.fetch();
        }
        System.out.println();
        System.out.println("Registers:");
        computer.displayRegisters();
        System.out.println();
        System.out.println("Memory:");
        computer.displayMemory();
    }
}
