package problem.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class DesignParser {
	/**
	 * Determine which folder to get classes from. Prefix here is the package name.
	 * 
	 * For project files: FOLDER_PATH = "./src/problem/asm"; FILE_PREFIX = "problem.asm";
	 * For pizzaaf files: FOLDER_PATH = "./src/headfirst/factory/pizzaaf"; FILE_PREFIX = "headfirst.factory.pizzaaf";
	 * For lab 1-3 files: FOLDER_PATH = "./src/lab1_3"; FILE_PREFIX = "lab1_3"
	 * 
	 */
	public static final String DEFAULT_PATH = "./src/headfirst/factory/pizzaaf";
	public static final String DEFAULT_PREFIX = "headfirst.factory.pizzaaf";
	public static final String UML_OUTPUT = "./input_output/Diagram.gv";
	public static final String SD_OUTPUT = "./input_output/sDiagram.sd";
	
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.out.println("NO ARGUMENTS");
			System.exit(0);
		}
		if(args[0].toLowerCase().equals("uml")){
			if(args.length<3){
				System.out.println("NOT ENOUGH ARGUMENTS");
				System.exit(0);
			}
			String folderPath = args[1];
			String filePrefix = args[2];
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
		    
			List<IClassData> classDatas = new ArrayList<>();
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
			IClassStructurePrinter gPrinter = new GraphVisPrinter(classDatas);
			gPrinter.printToFile(UML_OUTPUT);
		}
		else if(args[0].toLowerCase().equals("sd")){
			if(args.length<3){
				System.out.println("NO ARGUMENTS");
				System.exit(0);
			}
			int depth = 5;
			String className;
			String methodName;
			int firstParam = -1;
			if(args[1].contains("depth=")){
				depth = Integer.parseInt(args[1].replace("depth=", ""));
				className = args[2];
				methodName = args[3];
				firstParam = 4;
			}
			else{
				className = args[1];
				methodName = args[2];
				firstParam = 3;
			}
			List<String> parameters = new ArrayList<>();
			int i = firstParam;
			while(i<args.length){ 
				parameters.add(args[i]);
			}
		}
		else{
			System.out.println("INVALID COMMAND");
			System.exit(0);
		}
	}
}