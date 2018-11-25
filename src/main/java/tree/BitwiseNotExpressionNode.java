package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.RegisterAllocator;
import intermediate.UnaryOpStatement;

/** ~ expr */
public class BitwiseNotExpressionNode implements Expression {
    public Expression expr;
    public String fileName;
    public int line;
    
    public BitwiseNotExpressionNode(String fileName, int line) {
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
		expr.resolveImports(c);
	}

	@Override
	public void compile(SymbolTable s, InterFunction f, RegisterAllocator r, CompileHistory c) throws CompileException {
		expr.compile(s, f, r, c);
		// take bitwise not of the result.
		f.statements.add(new UnaryOpStatement(r.getLast(), r.getNext(r.getLast().type), '~', 
				fileName, line));
	}
}