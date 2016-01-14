package problem.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class VisitorManager {
	
	public static AbstractClassDataVisitor visitClass(String className) throws IOException{
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
		return methodVisitor;
	}
	public static List<String> getClassNames(String folderPath, String filePrefix){
		List<String> classes = new ArrayList<>();
		
		File classesFolder = new File(folderPath);
		File[] listOfFiles = classesFolder.listFiles();
		
		String classPath;
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		classPath = listOfFiles[i].toString();
	    		classes.add(filePrefix+"."+classPath.substring(classPath.lastIndexOf("\\")+1, classPath.length()-5));
	      } 
	    }
	    return classes;  
	}
	
	public static ClassMethodVisitor visitMethods(String className, IMethodCallData callData) throws IOException{
		ClassReader reader = new ClassReader(className);
		// make class declaration visitor to get superclass and interfaces
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		// DECORATE declaration visitor with field visitor
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		// DECORATE field visitor with method visitor
		ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		methodVisitor.setCallData(callData);
		// TODO: add more DECORATORS here in later milestones to accomplish specific tasks
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		return methodVisitor;
	}

}
