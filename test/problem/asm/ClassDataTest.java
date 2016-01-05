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
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		assertEquals("FirstASM [\n" + "label = " +
		"{FirstASM|+ myField : java.lang.String\l|+ clinit() : void\l+ init() :"
		+ " void\l+ main(java.lang.String[]) : void\l}\n" + "]", methodVisitor.getClassData().toString());
	}
}
