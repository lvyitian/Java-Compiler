package x64.directives;

/** Represents the .global NAME directive, useful for imports and exports. */
public class GlobalSymbol implements Directive {
    private final String name;

    public GlobalSymbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ".global " + name;
    }
}