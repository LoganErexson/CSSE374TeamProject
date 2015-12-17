package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	private String level = "";
	private ClassData data;
	
	public ClassFieldVisitor(int api) {
		super(api);
		this.data = new ClassData();
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
		this.data = new ClassData();
	}
	
	public ClassFieldVisitor(int api, ClassDeclarationVisitor decorated) {
		super(api, decorated);
		this.data = decorated.getClassData();
	}


	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		String type = Type.getType(desc).getClassName();
		
		addAccessLevel(access);
		
		data.addField(new FieldData(name, level, type));
		//appendToField(name + " : " + type + "'\'l");
		// TODO: add this field to your internal representation of the current class.
		// What is a good way to know what the current class is?
		return toDecorate;
	};
	
	public void addAccessLevel(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "-";
		} else {
			level = "+";
		}
		// TODO: ADD this information to your representation of the current method.
	}
	
	public ClassData getClassData() {
		return data;
	}
}