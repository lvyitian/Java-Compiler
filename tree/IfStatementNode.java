package tree;

import java.io.IOException;

import helper.ClassLookup;

public class IfStatementNode implements Node {
    public ExpressionNode expression;
    public StatementNode statement;
    public StatementNode elsePart;
    
	@Override
	public void resolveNames(ClassLookup c) throws IOException {
		expression.resolveNames(c);
		statement.resolveNames(c);
		if (elsePart != null) {
			elsePart.resolveNames(c);
		}
	}
}
