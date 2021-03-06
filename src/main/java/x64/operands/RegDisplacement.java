package x64.operands;

import org.jetbrains.annotations.NotNull;

/** Represents a memory displacement from a register with a known constant integer offset */
public class RegDisplacement {

    public final int offset;

    @NotNull public final X64Register register;

    public RegDisplacement(int offset, @NotNull X64Register register) {
        this.offset = offset;
        this.register = register;
    }

    @Override
    public String toString() {
        return offset + "(" + register.toString() + ")";
    }
}
