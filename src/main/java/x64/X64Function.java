package x64;

import org.jetbrains.annotations.Nullable;
import x64.allocation.RegisterTransformer;
import x64.directives.*;
import x64.instructions.*;
import x64.operands.Immediate;
import x64.operands.X64RegisterOperand;

import java.util.*;
import java.util.stream.Collectors;

import static x64.allocation.CallingConvention.*;
import static x64.operands.X64NativeRegister.RSP;
import static x64.operands.X64RegisterOperand.of;

public class X64Function {

	private final ArrayList<Instruction> header = new ArrayList<>();
	private ArrayList<Instruction> contents = new ArrayList<>();
	private final X64Context context;

	@Nullable private RegisterTransformer.AllocationUnit au = null;

	X64Function(String javaClass, String javaMethod, X64RegisterOperand jniEnvPointer, X64Context context) {
		this.context = context;

		header.add(new SegmentChange(SegmentChange.TEXT));
		final String symbolName = SymbolNames.getFieldName(javaClass, javaMethod);
		header.add(new GlobalSymbol(symbolName));
		// linux define
		// .type Symbol_Name, @function
		if (!isMicrosoft && !isMac) {
			header.add(new TypeDirective(symbolName));
		}
		header.add(new ByteAlignment(16));
		header.add(new LabelInstruction(symbolName));

		// save the first argument, the java environment pointer to a dedicated virtual register.
		contents.add(new MoveInstruction(argumentRegister(1), jniEnvPointer));
	}

	/** Adds an instruction to this function */
	public void addInstruction(Instruction instruction) {
		this.contents.add(instruction);
	}



	/** Allocates the registers, transforming pseudo-registers to real ones */
	void allocateRegisters() {
		au = new RegisterTransformer(contents, context).allocate();
	}

	@Override
	public String toString() {

		final List<Instruction> allInstructions = new ArrayList<>(header);

		if (au != null) allInstructions.addAll(au.prologue);

		if (needsToAllocate32BytesForArgs())
			allInstructions.add(new SubtractInstruction(new Immediate(32), RSP));

		allInstructions.addAll(contents);

		if (needsToAllocate32BytesForArgs())
			allInstructions.add(new AddInstruction(new Immediate(32), RSP));

		if (au != null) allInstructions.addAll(au.epilogue);

		allInstructions.add(ReturnInstruction.instance);

		return allInstructions.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}
}
