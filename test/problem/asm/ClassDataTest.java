package problem.asm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
				+ "style = \"dashed\"\n]\nHTMLBehavior -> Behavior\n", methodVisitor.getClassData().getInheritsArrows());
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
		List<String> classNames = GraphVisPrinter.getClassNames(classDatas);
		assertEquals("edge [ \n" + "arrowhead = \"empty\"\n]\n"
				+ "ClassMethodVisitor -> AbstractClassDataVisitor\n", 
				methodVisitor.getClassData().getExtendsArrow(classNames));
	}
	
	@Test
	public final void testToStringBase() {
		ClassData c = new ClassData();
		c.setName("Test");
		List<String> inters = new ArrayList<>();
		inters.add("Interface");
		c.setInterfaces(inters);
		c.setSuperClass("Super");
		Type f = Type.CHAR_TYPE;
		c.addField(new FieldData("AField", "+", f, null));
		Type[] t = {};
		c.addMethod(new MethodData("AMEthod", Type.VOID_TYPE, "+", t, ""));
		
		assertEquals("Test [\n" + "label = " + "\"{Test|+ AField : C\\l|+ AMEthod() : void\\l"
				+ "}\"\n" + "]\n", c.toString());
	}
}
