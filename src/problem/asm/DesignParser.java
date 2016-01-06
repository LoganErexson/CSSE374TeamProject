package problem.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class DesignParser {
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		//Modify input here to change which folder is converted
		//For project files: ("./src/problem/asm", "problem.asm")
		//For Lab 1-3 files: ("./src/lab1_3", "lab1_3")
	    List<String> classes = getClasses("./src/lab1_3", "lab1_3");
	    
		List<ClassData> classDatas = new ArrayList<>();
		for (String className : classes) {
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
		GraphVisPrinter.makeUML("./input_output/Diagram.gv", classDatas);
	}
	
	public static List<String> getClasses(String folder, String prefix)
	{
		List<String> classes = new ArrayList<>();
		
		File classesFolder = new File(folder);
		File[] listOfFiles = classesFolder.listFiles();
		
		String classPath;
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  classPath = listOfFiles[i].toString();
	        classes.add(prefix+"."+classPath.substring(classPath.lastIndexOf("\\")+1, classPath.length()-5));
	      } 
	    }
	    
		return classes;
	}
}