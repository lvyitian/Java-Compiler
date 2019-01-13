package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.LoadLiteralStatement;

public class LiteralExpressionNode extends NodeImpl implements Expression {
	
    public String value;
    
    public LiteralExpressionNode(String fileName, int line) {
    	super(fileName, line);
    }

	@Override
	public void resolveImports(ClassLookup c) throws CompileException {
		// nothing to do
	}

	@Override
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		// the IL code does the work here
		f.statements.add(new LoadLiteralStatement(value, f.allocator, getFileName(), getLine()));
	}
}
