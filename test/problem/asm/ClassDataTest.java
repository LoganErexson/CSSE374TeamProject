package problem.asm;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import problem.asm.AbstractClassDataVisitor;
import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;

public class ClassDataTest {
	@Test
	public final void testSingleClass() throws IOException, InterruptedException {
		ClassReader reader = new ClassReader("problem.asm.FirstASM");
		// make class declaration visitor to get superclass and interfaces
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		// DECORATE declaration visitor with field visitor
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		// DECORATE field visitor with method visitor
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		// TODO: add more DECORATORS here in later milestones to accomplish specific tasks
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertEquals("FirstASM [\n" + "label = " +
		"{FirstASM|+ myField : java.lang.String\l|+ clinit() : void\l+ init() :"
		+ " void\l+ main(java.lang.String[]) : void\l}\n" + "]", methodVisitor.getClassData().toString());
	}
}
