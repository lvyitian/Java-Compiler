package intermediate;

import java.util.HashMap;

import helper.CompileException;
import helper.Types;
import helper.UsageCheck;
import org.jetbrains.annotations.NotNull;
import x64.X64Context;
import x64.pseudo.MovePseudoToPseudo;

public class CopyStatement implements InterStatement {
	@NotNull private final Register src;
	@NotNull private final Register dest;
	
	@NotNull private final String fileName;
	private final int line;
	
	public CopyStatement(@NotNull Register src, @NotNull Register dest, @NotNull String fileName, int line) {
		this.src = src;
		this.dest = dest;
		this.fileName = fileName;
		this.line = line;
	}
	
	@Override
	public String toString() {
		return "copy " + dest.toString() + " = " + src.toString() + ";";
	}

	@Override
	public void typeCheck(@NotNull HashMap<Register, Types> regs, @NotNull HashMap<String, Types> locals,
						  @NotNull HashMap<String, Types> params, @NotNull InterFunction func) throws CompileException {
		
		UsageCheck.verifyDefined(src, regs, fileName, line);

		dest.setType(src.getType());
		regs.put(dest, dest.getType());
	}

	@Override
	public void compile(@NotNull X64Context context) throws CompileException {
		// simple move from source to destination
		context.addInstruction(
			new MovePseudoToPseudo(
				src.toX64(),
				dest.toX64()
			)
		);
	}
}
