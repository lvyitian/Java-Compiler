package tree;

import java.io.IOException;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.RegisterAllocator;

public class VariableIdNode implements Node {
    public String name;
    public int numDimensions; // 0 for not array
    
	@Override
	public void resolveImports(ClassLookup c) throws IOException {
		// nothing needed
	}

	@Override
	public void compile(SymbolTable s, InterFunction f, RegisterAllocator r) throws CompileException {
		// nothing needed
	}
}
