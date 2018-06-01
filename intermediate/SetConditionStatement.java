package intermediate;

public class SetConditionStatement implements InterStatement {
	public static final int GREATEREQUAL = 0;
	public static final int GREATER = 1;
	public static final int LESSEQUAL = 2;
	public static final int LESS = 3;
	public static final int EQUAL = 4; // if they are equal, ==
	public static final int NOTEQUAL = 5;
	
	int type;
	Register left;
	Register right;
	Register result;
	
	public SetConditionStatement(int type, Register left, Register right, Register result) {
		this.type = type;
		this.left = left;
		this.right = right;
		this.result = result;
	}
	
	@Override
	public String toString() {
		String leftPart = "setCondition " + result.toString() + " = " + left.toString();
		if (type == GREATEREQUAL) {
			return leftPart + " >= " + right.toString() + ";";
		} else if (type == GREATER) {
			return leftPart + " > " + right.toString() + ";";
		} else if (type == LESSEQUAL) {
			return leftPart + " <= " + right.toString() + ";";
		} else if (type == LESS){
			return leftPart + " < " + right.toString() + ";";
		} else if (type == EQUAL){
			return leftPart + " == " + right.toString() + ";";
		} else {
			return leftPart + " != " + right.toString() + ";";
		}
	}
}