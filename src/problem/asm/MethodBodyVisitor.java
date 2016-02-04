package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodBodyVisitor extends MethodVisitor{
	private IMethodData method;
	private List<IMethodCallData> methodCalls = new ArrayList<>();
	private String className;

	public MethodBodyVisitor(int api, MethodVisitor decorated) {
		super(api, decorated);
	}
	
	public void setMethod(IMethodData method){
		this.method = method;
	}

	@Override
	public void visitMethodInsn(int op, String owner, String name, String desc, boolean imp) {
		super.visitMethodInsn(op, owner, name, desc, imp);
		this.method.addUsedClass(StringParser.parseClassName(owner));
		IMethodCallData callData = new MethodCallData();
		callData.setMethodClass(owner);
		callData.setName(name);
		if(imp){
			callData.setDepth(0);
		}
		else if((owner!= null)&&owner.contains("[")){
			callData.setDepth(0);
		}
		this.methodCalls.add(callData);
		
	}
	
	@Override
	public void visitFieldInsn(int op, String owner, String name, String desc) {
		super.visitFieldInsn(op, owner, name, desc);
	}
	
	@Override
	public void visitTypeInsn(int op, String type) {
		super.visitTypeInsn(op, type);
		if(op == Opcodes.NEW) {
			this.method.addCreatedClass(StringParser.parseClassName(type));
		}
	}
	
	@Override
	public void visitVarInsn(int op, int var) {
		super.visitVarInsn(op, var);
	}

	public List<IMethodCallData> getMethodCalls(){
		return this.methodCalls;
	}
	
	public void setMethodCalls(List<IMethodCallData> methodCalls) {
		this.methodCalls = methodCalls;
		
	}

	public String getClassName(){
		return this.className;
	}
	
	public void setClassName(String name) {
		this.className = name;
		
	}
}
