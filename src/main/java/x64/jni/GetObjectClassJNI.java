package x64.jni;

import x64.X64Function;
import x64.instructions.CallFunctionPointerInstruction;
import x64.instructions.MoveInstruction;
import x64.operands.RegisterRelativePointer;
import x64.operands.X64NativeRegister;
import x64.operands.X64PreservedRegister;
import x64.operands.X64RegisterOperand;

import static x64.jni.JNIOffsets.GET_OBJECT_CLASS;
import static x64.operands.X64RegisterOperand.of;

public interface GetObjectClassJNI {

    /**
     * Adds the code returned = GetObjectClass(JNI, obj);
     * @param function The x64 function to add the code to
     * @param object The x64 register that holds the reference to the object
     * @return A new x64 register that holds the object's class.
     */
    default X64RegisterOperand addGetObjectClass(X64Function function, X64RegisterOperand object) {
        // result = JNIEnv -> GetObjectClass(JNIEnv, obj);

        function.loadJNI1();
        function.addInstruction(
            new MoveInstruction(
                object,
                X64NativeRegister.RSI
            )
        );

        X64RegisterOperand temp = of(X64PreservedRegister.newTempQuad(function.getNextFreeRegister()));

        // mov GetObjectClass_Offset(%javaEnvOne), %temp3
        function.addInstruction(
            new MoveInstruction(
                new RegisterRelativePointer(GET_OBJECT_CLASS.getOffset(), function.javaEnvPointer),
                temp
            )
        );

        // call *%temp
        function.addInstruction(
            new CallFunctionPointerInstruction(
                temp
            )
        );

        // mov %result, %final result
        X64RegisterOperand result = of(X64PreservedRegister.newTempQuad(function.getNextFreeRegister()));
        function.addInstruction(
            new MoveInstruction(
                X64NativeRegister.RAX,
                result
            )
        );
        return result;
    }
}
