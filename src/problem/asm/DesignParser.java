package problem.asm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class DesignParser {
	public static String[] CLASSES = {"problem.asm.FirstASM", "problem.asm.ClassData", "problem.asm.ClassDeclarationVisitor", 
		"problem.asm.ClassFieldVisitor", "problem.asm.ClassMethodVisitor", "problem.asm.DesignParser", "problem.asm.FieldData", "problem.asm.MethodData"};
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 * 
	 * @param args
	 *            : the names of the classes, separated by spaces. For example: java DesignParser
	 *            java.lang.String edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length > 0)
			CLASSES = args;
		List<ClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			// ASM's ClassReader does the heavy lifting of parsing the compiled Java class
			ClassReader reader = new ClassReader(className);
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
			classDatas.add(methodVisitor.getClassData());
		}
		GraphVisPrinter.makeUML("./input_output/out.txt", classDatas);
	}
}