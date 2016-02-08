package problem.asm;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import problem.model.data.FieldData;

public class ClassFieldVisitor extends AbstractClassDataVisitor {
	
	private String level = "";
	
	public ClassFieldVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc,
				signature, value);
		Type type = Type.getType(desc);
		
		addAccessLevel(access);
		
		this.classData.addField(new FieldData(name, this.level, type, signature));
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
}