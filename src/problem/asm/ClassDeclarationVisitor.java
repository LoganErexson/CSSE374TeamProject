package problem.asm;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	ClassData classData = new ClassData();
	public ClassDeclarationVisitor(int api) {
		super(api);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.classData.setName(name.substring(name.lastIndexOf("/")+1));
		this.classData.setSuperClass(superName);
		this.classData.setInterfaces(interfaces);
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public ClassData getClassData()
	{
		return this.classData;
	}
}