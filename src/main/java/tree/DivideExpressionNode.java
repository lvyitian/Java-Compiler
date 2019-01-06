package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.BinaryOpStatement;
import intermediate.InterFunction;
import intermediate.Register;

/** left / right */
public class DivideExpressionNode implements Expression {
    public Expression left;
    public Expression right;
    public String fileName;
    public int line;
    
    public DivideExpressionNode(String fileName, int line) {
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
		left.resolveImports(c);
		right.resolveImports(c);
	}
	
	@Override
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		left.compile(s, f);
		Register leftResult = f.allocator.getLast();
		
		right.compile(s, f);
		Register rightResult = f.allocator.getLast();
		
		// get the result
		Register result = f.allocator.getNext(Register.getLarger(leftResult.type, rightResult.type));
		f.statements.add(new BinaryOpStatement(leftResult, rightResult, result, '/', fileName, line));
		
	}
}
