package tree;

import java.io.IOException;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.RegisterAllocator;

/** expr ++ */
public class PostIncrementExpressionNode implements Expression {
    public Expression expr;

	@Override
	public void resolveImports(ClassLookup c) throws IOException {
		expr.resolveImports(c);
	}

	@Override
	public void compile(SymbolTable s, InterFunction f, RegisterAllocator r, CompileHistory c) throws CompileException {
		// construct an AssignmentNode:  expr += 1;
		AssignmentNode n = new AssignmentNode();
		n.left = expr;
		n.type = AssignmentNode.PLUSASSIGN;
		LiteralExpressionNode literal = new LiteralExpressionNode();
		literal.value = "1";
		n.right = literal;
		
		// compile it
		n.compile(s, f, r, c);
	}

}
