package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends AbstractClassDataVisitor {
	
	private String level = "";
	private List<IMethodCallData> calledByMethod = new ArrayList<>();
	private IMethodCallData callData;
	public ClassMethodVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
		this.callData = new MethodCallData();
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
		if(this.callData.getName()!=null&&this.callData.getName().equals(name)){
			this.callData.setMethod(m);
			toDecorate.setMethodCalls(this.calledByMethod);
			toDecorate.setClassName(this.callData.getMethodClass());
		}
		this.classData.addMethod(m);
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
	}

	public List<IMethodCallData> getMethodCalls() {
		return this.calledByMethod;
	}

	public void setCallData(IMethodCallData callData) {
		this.callData = callData;
	}
	public IMethodCallData getCallData(){
		return this.callData;
	}


}