package problem.asm;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends AbstractClassDataVisitor {
	
	public ClassFieldVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	private String level = "";

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		Type type = Type.getType(desc);
		
		addAccessLevel(access);
		
		this.addField(new FieldData(name, this.level, type, signature));
		//appendToField(name + " : " + type + "'\'l");
		// TODO: add this field to your internal representation of the current class.
		// What is a good way to know what the current class is?
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
}