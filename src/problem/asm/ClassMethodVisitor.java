package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends AbstractClassDataVisitor {
	
	private String level = "";
	private String neededMethod;
	private List<IMethodCallData> calledByNeeded = new ArrayList<>();
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
		if(this.neededMethod!=null&&this.neededMethod.equals(name)){
			toDecorate.setMethodCalls(this.calledByNeeded);
			toDecorate.setClassName(this.classData.getName());
		}
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

	public String getNeededMethod() {
		return this.neededMethod;
	}

	public void setNeededMethod(String neededMethod) {
		this.neededMethod = neededMethod;
	}

	public List<IMethodCallData> getMethodCalls() {
		return this.calledByNeeded;
	}


}