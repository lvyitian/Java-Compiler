package x64.instructions;

import org.jetbrains.annotations.NotNull;
import x64.X64InstructionSize;
import x64.operands.RegIndexing;
import x64.operands.X64Register;

public class BinaryRegToArrayIndex extends Instruction {

	BinaryRegToArrayIndex(@NotNull String opcode, @NotNull X64Register source,
									@NotNull RegIndexing destination, @NotNull X64InstructionSize size) {

		super("\t" + opcode + size + " " + source.assemblyRep(size) + ", " + destination);
	}
}
