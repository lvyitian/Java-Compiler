package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.ReturnRegStatement;
import intermediate.ReturnVoidStatement;

public class ReturnStatementNode implements StatementNode {
    // could be null
    public Expression expression;
    public String fileName;
    public int line;
    
    public ReturnStatementNode(String fileName, int line) {
    	this.fileName = fileName;
    	this.line = line;
    }
    
    @Override
    public String getFileName() {
    	return fileName;
    }
    
    @Override
    public int getLine() {
    	return line;
    }

	@Override
	public void resolveImports(ClassLookup c) throws CompileException {
		if (expression != null) expression.resolveImports(c);
	}

	@Override
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		// compile in the expression
		if (expression != null) {
			expression.compile(s, f);
			f.statements.add(new ReturnRegStatement(f.allocator.getLast(), fileName, line));
		} else {
			// just compile in the return statement.
			f.statements.add(new ReturnVoidStatement(fileName, line));
		}
	}
}
