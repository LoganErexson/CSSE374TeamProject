package problem.asm;

import org.objectweb.asm.MethodVisitor;

public class MethodBodyVisitor extends MethodVisitor{
	private IMethodData method;

	public MethodBodyVisitor(int api, MethodVisitor decorated) {
		super(api, decorated);
	}
	
	public void setMethod(IMethodData method){
		this.method = method;
	}

}
