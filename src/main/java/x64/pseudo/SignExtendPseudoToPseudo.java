package x64.pseudo;

import org.jetbrains.annotations.NotNull;
import x64.allocation.AllocationContext;
import x64.allocation.RegistersUsed;
import x64.instructions.Instruction;
import x64.instructions.MoveRegToBPOffset;
import x64.instructions.SignExtendBPOffsetToReg;
import x64.instructions.SignExtendRegToReg;
import x64.operands.X64PseudoRegister;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SignExtendPseudoToPseudo implements PseudoInstruction {

	@NotNull private final X64PseudoRegister source, destination;

	public SignExtendPseudoToPseudo(@NotNull X64PseudoRegister source, @NotNull X64PseudoRegister destination) {
		// move with sign extension
		this.source = source;
		this.destination = destination;
	}

	@Override
	public void markRegisters(int i, RegistersUsed usedRegs) {
		usedRegs.markUsed(source,i);
		usedRegs.markDefined(destination, i);
	}

	@Override
	public @NotNull List<@NotNull Instruction> allocate(@NotNull AllocationContext context) {

		if (context.isRegister(source)) {
			if (context.isRegister(destination)) {
				// simple move source to destination
				return Collections.singletonList(
					new SignExtendRegToReg(
						context.getRegister(source),
						context.getRegister(destination),
						source.getSuffix(),
						destination.getSuffix()
					)
				);
			} else {
				// there isn't a sign extend to memory, only source can be memory
				return Arrays.asList(
					new SignExtendRegToReg(
						context.getRegister(source),
						context.getScratchRegister(),
						source.getSuffix(),
						destination.getSuffix()
					),
					new MoveRegToBPOffset(
						context.getScratchRegister(),
						context.getBasePointer(destination),
						destination.getSuffix()
					)
				);
			}
		} else {
			if (context.isRegister(destination)) {
				// can be done with one instruction too
				return Collections.singletonList(
					new SignExtendBPOffsetToReg(
						context.getBasePointer(source),
						context.getRegister(destination),
						source.getSuffix(),
						destination.getSuffix()
					)
				);
			} else {
				// both of these are base pointer offsets, but can only have 1 mem operand
				// the only complicated one, one way is to move to the temp immediate and then sign extend
				// also only source can be memory, so have to do this:
				// 1. local source sign extend to temporary immediate
				// 2. move temporary immediate to base pointer destination
				return Arrays.asList(
					new SignExtendBPOffsetToReg(
						context.getBasePointer(source),
						context.getScratchRegister(),
						source.getSuffix(),
						destination.getSuffix()
					),
					new MoveRegToBPOffset(
						context.getScratchRegister(),
						context.getBasePointer(destination),
						destination.getSuffix()
					)
				);
			}
		}
	}

	@Override
	public String toString() {
		return "\tmovs " + source + ", " + destination;
	}
}
