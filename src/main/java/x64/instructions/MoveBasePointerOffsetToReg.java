package x64.instructions;

import org.jetbrains.annotations.NotNull;
import x64.operands.BasePointerOffset;
import x64.operands.X64Register;

public class MoveBasePointerOffsetToReg extends BinaryBasePointerOffsetToReg {


	public MoveBasePointerOffsetToReg(@NotNull BasePointerOffset source,
									  @NotNull X64Register destination) {
		super("mov", source, destination);
	}
}