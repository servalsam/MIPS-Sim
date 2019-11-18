================================================================================
* Author: Samuel Wainright
* Current revision: v1.3
* Revision date: 11/13/2019
* Program Name: MIPS-Sim
================================================================================

Project Decisions:
-----------------
-Working at a machine code level that translates machine code to
assembly context.
-No token delimiters.
-Separate instruction memory, separate data memory, 64 blocks (array slots).
-Console only GUI.
-Excutes all instructions at a time.
-Reads instructions from a file that is programmed from binary instructions.
-Enum Singleton-variant design pattern for the Computer class; the ComputerInstance class is the Singleton itself.
-All instructions are parsed via a Factory design pattern.
-Note: Due to the static nature of Enums and the Singleton pattern, ComputerInstance.java cannot be tested, however, it is a manifestation of Computer.java as it uses all the same methods.

Directions of Use:
-----------------
-The program accepts input through the 'instructions.txt' file.
-All instructions are 32-bit binary words; each word is handled differently based on the instruction type.
-There are 32 general purpose registers, accessed by their array number in binary.
-There are 64 memory blocks, accessed by their array number in binary.
-There is an accessible instruction array, where it's instructions are loaded from 'instructions.txt'. It's storage size is 64 blocks. This array can be accessed with instructions: bne, beq, jr, and jump.
-To start the simmulation, run the driver class, Simulator.java. It will print to the console: the instructions list, the resulting register values, and the resulting memory locations and all their respective values.

-Register Instructions are of the following format with their bit allotment:
[ 6 bits ][ 5 bits ][ 5 bits ][   5 bits    ][  5 bits ][    6 bits     ]
[ opcode ][ source ][ target ][ destination ][  shift  ][ function code ]

-Immediate Instructions are of the following format with their bit allotment:
[ 6 bits ][ 5 bits ][ 5 bits ][                16 bits                  ]
[ opcode ][ source ][ target ][            immediate value              ]

-Jump Instructions are of the following format with their bit allotment:
[ 6 bits ][                          26 bits                            ]
[ opcode ][                       jump address                          ]

Instruction Quick Reference:
---------------------------

Register Instructions:
________________________
Name | Opcode | Function|
------------------------
jr   | 000000 |  001000 |
add  | 000000 |  010100 |
addu | 000000 |  010101 |
and  | 000000 |  011000 |
or   | 000000 |  011001 |
------------------------

Immediate Instructions:
______________
Name | Opcode |
---------------
beq  | 000100 |
bne  | 000101 |
addi | 001000 |
addiu| 001001 |
andi | 001100 |
ori  | 001101 |
lw   | 010111 |
sw   | 101011 |
--------------

Jump Instructions:
______________
Name | Opcode |
---------------
jump | 000010 |
--------------

Key Instruction notes:
---------------------
-lw and sw are special in the way they function.
-lw uses the stored array value with the source as its index, plus the immediate value as the address. The value is loaded into the target index's register.
-sw uses the stored array value with the source of its index, plus the immediate value as the address. The addressed memory is stored with the value of the target index's value from the register. 
-jump DOES NOT allow negative indexing. To travel backwards with instructions, set the jump value to an earlier instruction number.

Example Output:
--------------
-The 'instructions.txt' file contains sample output by default.
-A synopsis of instructions follows, by line number: 
1.) A value of 10 is added to the [0] index register. 
2.) The [0] index register is added to the [0] index register again for a total of 20.
3.) A jump to the [4] index instruction.
4.) The final instruction is executed due to the jump, which is an add instruction. The same value at [0] index register is added to the [0] index register for a total of 40. 