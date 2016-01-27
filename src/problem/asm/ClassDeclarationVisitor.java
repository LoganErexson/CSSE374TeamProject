package problem.asm;

import java.util.Arrays;

public class ClassDeclarationVisitor extends AbstractClassDataVisitor {
	
	public ClassDeclarationVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.classData.setName(StringParser.parseClassName(name));
		this.classData.setSuperClass(StringParser.parseClassName(superName));
		this.classData.setImplementedClasses(Arrays.asList(interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}