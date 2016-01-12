package problem.asm;

import java.util.Arrays;



public class ClassDeclarationVisitor extends AbstractClassDataVisitor {
	
	public ClassDeclarationVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.classData.setName(name.substring(name.lastIndexOf("/")+1));
		this.getClassData().setSuperClass(superName.substring(superName.lastIndexOf("/")+1));
		this.getClassData().setInterfaces(Arrays.asList(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}