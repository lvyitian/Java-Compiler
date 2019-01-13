package tree;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.BranchStatmentFalse;
import intermediate.ChooseStatement;
import intermediate.InterFunction;
import intermediate.LabelStatement;
import intermediate.Register;

/** left && right */
public class ConditionalAndExpressionNode extends NodeImpl implements Expression {
    public Expression left;
    public Expression right;

    public ConditionalAndExpressionNode(String fileName, int line) {
    	super(fileName, line);
    }

	@Override
	public void resolveImports(ClassLookup c) throws CompileException {
		left.resolveImports(c);
		right.resolveImports(c);
	}

	@Override
	public void compile(SymbolTable s, InterFunction f) throws CompileException {
		left.compile(s, f);
		LabelStatement end = new LabelStatement("L_" + f.allocator.getNextLabel());
		Register leftResult = f.allocator.getLast();
		// if left is false, jump to end
		f.statements.add(new BranchStatmentFalse(end, leftResult, getFileName(), getLine()));
		
		// compile in right half
		right.compile(s, f);
		Register rightResult = f.allocator.getLast();
		
		f.statements.add(end);

		// keep in SSA, need choose statement
		Register newReg = f.allocator.getNext(Register.BOOLEAN); // result is boolean
		f.statements.add(new ChooseStatement(leftResult, rightResult, newReg, getFileName(), getLine()));
	}
}
