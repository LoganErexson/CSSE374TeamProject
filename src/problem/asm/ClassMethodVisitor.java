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
		MethodVisitor toDecorate = super.visitMethod(access, name, desc,
				signature, exceptions);
		
		addAccessLevel(access);
		addReturnType(desc);
		addArguments(desc);
		String type= Type.getReturnType(desc).getClassName();
		this.classData.addMethod(new MethodData(name, type, this.level, Type.getArgumentTypes(desc)));
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

	void addReturnType(String desc) {
		String returnType = Type.getReturnType(desc).getClassName();
		// TODO: ADD this information to your representation of the current method.
	}

	void addArguments(String desc) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			// TODO: ADD this information to your representation of the current method.
		}
	}

}