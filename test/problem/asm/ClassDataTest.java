package problem.asm;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import problem.asm.AbstractClassDataVisitor;
import problem.asm.ClassData;
import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;

public class ClassDataTest {
	@Test
	public final void testSingleClass() throws IOException {
		ClassReader reader = new ClassReader("problem.asm.FirstASM");
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertEquals("FirstASM [\n" + "label = " + "\"{FirstASM|+ myField : java.lang.String\\l|+ clinit() : void\\l+ init() :"
		+ " void\\l+ main(java.lang.String[]) : void\\l}\"\n" + "]\n", methodVisitor.getClassData().toString());
	}
	
	@Test
	public final void testInterfaces() throws IOException {
		ClassReader reader = new ClassReader("lab1_3/HTMLBehavior");
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertEquals("edge [ \n" + "arrowhead = \"empty\"\n"
				+ "style = \"dashed\"\n]\nHTMLBehavior -> Behavior\n", methodVisitor.getClassData().getInterfacesString());
	}
	
	@Test
	public final void testSuperClasses() throws IOException {
		String[] CLASSES = {"problem.asm.ClassData", "problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor", 
				"problem.asm.DesignParser", "problem.asm.FieldData", "problem.asm.MethodData", "problem.asm.AbstractClassDataVisitor"};
		List<ClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			ClassReader reader = new ClassReader(className);
			AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
			AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor);
			AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			classDatas.add(methodVisitor.getClassData());
		}
		ClassReader reader = new ClassReader("problem.asm.ClassMethodVisitor");
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		classDatas.add(methodVisitor.getClassData());
		assertEquals("edge [ \n" + "arrowhead = \"empty\"\n]\n"
				+ "ClassMethodVisitor -> AbstractClassDataVisitor\n", 
				methodVisitor.getClassData().getSuperClassString(classDatas));
	}
}
