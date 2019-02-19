package x64.instructions;

import org.jetbrains.annotations.NotNull;
import x64.operands.X64Register;

/** This class represents a binary instruction with a memory at register source and register destination */
public abstract class BinaryAbsoluteRegToReg extends Instruction {

    @NotNull private final X64Register source;
    @NotNull private final X64Register destination;
    @NotNull private final String name;


    /**
     * Represents a binary operation with a memory at register source and register destination.
     * @param name The binary instruction's name, like 'add'
     * @param source The register source.
     * @param destination The register destination.
     */
    public BinaryAbsoluteRegToReg(@NotNull String name, @NotNull X64Register source,
								  @NotNull X64Register destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
    }

    /** Represents how this instruction should be represented */
    @Override
    public final String assemblyRepresentation() {
        return '\t' + name + destination.getSuffix() + " (" +
                source.toString() + "), " + destination.toString();
    }
}
