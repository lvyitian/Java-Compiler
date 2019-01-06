package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.Register;
import intermediate.UnaryOpStatement;

/** ! expr */
public class LogicalNotExpressionNode implements Expression {
    public Expression expr;
    public String fileName;
    public int line;
    
    public LogicalNotExpressionNode(String fileName, int line) {
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
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		expr.compile(s, f);
		f.statements.add(new UnaryOpStatement(f.allocator.getLast(),
				f.allocator.getNext(Register.BYTE), UnaryOpStatement.LOGNOT, fileName, line));
	}
}
