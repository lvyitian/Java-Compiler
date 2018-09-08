package tree;

import java.io.IOException;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.BinaryOpStatement;
import intermediate.CopyStatement;
import intermediate.InterFunction;
import intermediate.LoadLiteralStatement;
import intermediate.Register;
import intermediate.RegisterAllocator;

/** -- expr */
public class PreDecrementExpressionNode implements Expression {
    public Expression expr;
    public String fileName;
    public int line;
    
    public PreDecrementExpressionNode(String fileName, int line) {
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
	public void resolveImports(ClassLookup c) throws IOException {
		expr.resolveImports(c);		
	}

	@Override
	public void compile(SymbolTable s, InterFunction f, RegisterAllocator r, CompileHistory c) throws CompileException {
		// get new one = expr
		// expr -- (but have to use the previous answer - 1, can't calculate 2x)
		// copy new new one from new one
		expr.compile(s, f, r, c);
		Register result = r.getLast();
		
		// subtract 1
		f.statements.add(new LoadLiteralStatement("1", r, fileName, line));
		Register one = r.getLast();
		
		f.statements.add(new BinaryOpStatement(result, one, r.getNext(result.type), '-', fileName, line));
		Register minusOne = r.getLast();
		// compile in the store to the address
		if (!(expr instanceof LValue)) {
			throw new CompileException("Can't assign the expression.", fileName, line);
		}
		((LValue)expr).compileAddress(s, f, r, c);
		// store it back
		f.statements.add(new CopyStatement(minusOne, r.getLast(), fileName, line));
		
		// result is before the subtraction
		f.statements.add(new CopyStatement(result, r.getNext(result.type), fileName, line));
	}
}
