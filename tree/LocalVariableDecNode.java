package tree;

import java.io.IOException;
import java.util.ArrayList;

import helper.ClassLookup;
import helper.CompileException;
import intermediate.InterFunction;
import intermediate.PutLocalStatement;
import intermediate.RegisterAllocator;
import intermediate.StartScopeStatement;

public class LocalVariableDecNode implements Node {
    public TypeNode type;
    public ArrayList<VariableDecNode> declarators;
    
	@Override
	public void resolveImports(ClassLookup c) throws IOException {
		type.resolveImports(c);
		for (VariableDecNode d : declarators) {
			if (d.init != null) {
				d.init.resolveImports(c);
			}
		}
	}

	@Override
	public void compile(SymbolTable s, InterFunction f, RegisterAllocator r, CompileHistory c) throws CompileException {
		// place in symbol table and add intermediate code
		for (VariableDecNode d : declarators) {
			s.putEntry(d.id.name, type.interRep());
			f.statements.add(new StartScopeStatement(d.id.name, type.interRep()));
			if (d.init != null) {
				// put in the code to compute initial value.
				d.init.compile(s, f, r, c);
				// store the initial value into the local variable.
				f.statements.add(new PutLocalStatement(r.getLast(), d.id.name));
			}
		}
	}
}
