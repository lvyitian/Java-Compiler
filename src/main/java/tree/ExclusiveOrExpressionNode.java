package tree;

import java.util.ArrayList;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.BinaryOpStatement;
import intermediate.InterFunction;
import intermediate.Register;

/** Chain of ^ of the operands (not short-circuiting, aka bitwise or also)*/
public class ExclusiveOrExpressionNode implements Expression {
    public ArrayList<Expression> expressions = new ArrayList<Expression>();
    public String fileName;
    public int line;
    
    public ExclusiveOrExpressionNode(String fileName, int line) {
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
		for (Expression e : expressions) {
			e.resolveImports(c);
		}
	}

	@Override
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		// compile in the first one
		expressions.get(0).compile(s, f);

		// use its result in the next one
		Register result = f.allocator.getLast();
		for (int i = 1; i < expressions.size(); i++) {
			expressions.get(i).compile(s, f);
			// add in XOR of the last two
			Register current = result;
			Register two = f.allocator.getLast();
			result = f.allocator.getNext(Register.getLarger(current.type, two.type));
			// add the XOR statement
			f.statements.add(new BinaryOpStatement(current, two, result, '^', fileName, line));
		}
	}
}
