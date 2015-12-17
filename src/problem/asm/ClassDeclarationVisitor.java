package problem.asm;


public class ClassDeclarationVisitor extends AbstractClassDataVisitor {
	
	public ClassDeclarationVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	ClassData classData = new ClassData();

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.classData.setName(name.substring(name.lastIndexOf("/")+1));
		this.classData.setSuperClass(superName);
		this.classData.setInterfaces(interfaces);
		super.visit(version, access, name, signature, superName, interfaces);
	}
}