package x64.jni;

import intermediate.Register;
import x64.X64Context;
import x64.jni.helpers.CallJNIMethod;
import x64.operands.X64PseudoRegister;
import x64.pseudo.MovePseudoToReg;

import static x64.jni.JNIOffsets.NEW_STRING_UTF;

public interface NewStringUTF_JNI extends CallJNIMethod {

	/**
	 * Inserts the call NewStringUTF(JNIEnv, char*) into the function, with result holding the end value
	 * @param context The x64 function to add the instructions to
	 * @param chars The register holding the utf-8 characters
	 * @param result The IL register that holds the result of this call
	 */
	default void addNewStringUTF_JNI(X64Context context, X64PseudoRegister chars, Register result) {

		// arg 1
		context.loadJNI1();

		// arg 2
		context.addInstruction(
			new MovePseudoToReg(
				chars,
				context.argumentRegister(2)
			)
		);

		final X64PseudoRegister returned = result.toX64();

		addCallJNI(context, NEW_STRING_UTF, returned);
	}
}
