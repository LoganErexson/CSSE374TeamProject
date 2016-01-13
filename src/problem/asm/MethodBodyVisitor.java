package problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodBodyVisitor extends MethodVisitor{
	private IMethodData method;

	public MethodBodyVisitor(int api, MethodVisitor decorated) {
		super(api, decorated);
	}
	
	public void setMethod(IMethodData method){
		this.method = method;
	}

	@Override
	public void visitMethodInsn(int op, String owner, String name, String desc, boolean imp) {
		super.visitMethodInsn(op, owner, name, desc, imp);
	}
	
	@Override
	public void visitFieldInsn(int op, String owner, String name, String desc) {
		super.visitFieldInsn(op, owner, name, desc);
	}
	
	@Override
	public void visitTypeInsn(int op, String type) {
		super.visitTypeInsn(op, type);
		this.method.setType(StringParser.parseClassName(type));
		if(op == Opcodes.NEW) {
			this.method.addUsedClass(StringParser.parseClassName(type));
		}
	}
	
	@Override
	public void visitVarInsn(int op, int var) {
		super.visitVarInsn(op, var);;
	}
}
