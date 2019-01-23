package intermediate;

import java.util.HashMap;

import helper.CompileException;
import helper.TypeChecker;
import helper.Types;
import helper.UsageCheck;
import x64.X64File;
import x64.X64Function;
import x64.instructions.MoveInstruction;
import x64.operands.X64RegisterOperand;

/** PutLocal name = %register */
public class PutLocalStatement implements InterStatement {
	Register r;
	private String localName;
	
	private final String fileName;
	private final int line;
	
	/**
	 * Creates a new put local variable statement.
	 * @param r The register to use it's value
	 * @param localName The local variable to set.
	 */
	public PutLocalStatement(Register r, String localName, String fileName, int line) {
		this.r = r;
		this.localName = localName;
		this.fileName = fileName;
		this.line = line;
	}
	
	@Override
	public String toString() {
		return "PutLocal " + localName + " = " + r.toString() + ";";
	}

	@Override
	public void typeCheck(HashMap<Register, Types> regs, HashMap<String, Types> locals,
						  HashMap<String, Types> params, InterFunction func) throws CompileException {
		
		UsageCheck.verifyDefined(r, regs, fileName, line);
		if (!locals.containsKey(localName)) {
			throw new CompileException("local variable: " + localName + " is not defined.",
					fileName, line);
		}
		TypeChecker.canDirectlyAssign(locals.get(localName), r.getType(), fileName, line);
	}

	@Override
	public void compile(X64File assemblyFile, X64Function function) throws CompileException {
		final X64RegisterOperand destination = function.getLocalVariable(localName);

		// copy the result over to the destination
		function.addInstruction(
			new MoveInstruction(r.toX64(), destination)
		);
	}
}
