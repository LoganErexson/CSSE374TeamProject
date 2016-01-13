package problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends AbstractClassDataVisitor {
	
	private String level = "";
	public ClassMethodVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodBodyVisitor toDecorate = new MethodBodyVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc,
				signature, exceptions));
		
		Type type = Type.getReturnType(desc);
		addAccessLevel(access);
		IMethodData m = new MethodData(name, type, this.level, Type.getArgumentTypes(desc), signature);
		
		toDecorate.setMethod(m);
		
		this.getClassData().addMethod(m);
		return toDecorate;
	}

	public void addAccessLevel(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			this.level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			this.level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			this.level = "-";
		} else {
			this.level = "+";
		}
		// TODO: ADD this information to your representation of the current method.
	}


}