package intermediate;

import java.util.HashMap;

import helper.CompileException;
import helper.Types;
import org.jetbrains.annotations.NotNull;
import x64.X64Context;

/** Represents the ending of a scope of a local variable. */
public class EndScopeStatement implements InterStatement {
	@NotNull private final String name;
	
	/**
	 * Constructs a local variable scope ending statement.
	 * @param name The name of the local variable.
	 */
	public EndScopeStatement(@NotNull String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EndScope " + name + ";";
	}

	@Override
	public void typeCheck(@NotNull HashMap<Register, Types> regs, @NotNull HashMap<String, Types> locals,
						  @NotNull HashMap<String, Types> params, @NotNull InterFunction func) throws CompileException {
		
		// remove name from the locally defined variables
		locals.remove(name);
	}

	@Override
	public void compile(@NotNull X64Context context) throws CompileException {
		context.clearLocalVariable(name);
	}
}
