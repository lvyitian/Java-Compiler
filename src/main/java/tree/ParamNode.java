package tree;

import helper.ClassLookup;
import helper.CompileException;
import helper.Types;
import intermediate.InterFunction;
import org.jetbrains.annotations.NotNull;

public class ParamNode extends NodeImpl {
    public Types type;
    public VariableIdNode id;
    public boolean isVarargs; // if it is the ...
    
    public ParamNode(String fileName, int line) {
    	super(fileName, line);
    }
    
	@Override
	public void resolveImports(@NotNull ClassLookup c) throws CompileException {
		type = type.resolveImports(c, getFileName(), getLine());
	}

	@Override
	public void compile(@NotNull SymbolTable s, @NotNull InterFunction f) throws CompileException {
		// already dealt with in MethodNode or ConstructorNode.
		if (this.isVarargs) {
			throw new CompileException("Error: varargs not implemented yet.", getFileName(), getLine());
		}
	}
}
