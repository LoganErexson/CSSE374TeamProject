package problem.asm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFileChooser;

public class DesignParser {
	/**
	 * Determine which folder to get classes from. Prefix here is the package name.
	 * 
	 * For project files: FOLDER_PATH = "./src/problem/asm"; FILE_PREFIX = "problem.asm";
	 * For pizzaaf files: FOLDER_PATH = "./src/headfirst/factory/pizzaaf"; FILE_PREFIX = "headfirst.factory.pizzaaf";
	 * For lab 1-3 files: FOLDER_PATH = "./src/lab1_3"; FILE_PREFIX = "lab1_3"
	 * 
	 */
	public static final String[] DEFAULT_CLASSES = {"problem.asm.AbstractClassDataVisitor", "problem.asm.ClassData", 
		"problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor", "problem.asm.ClassMethodVisitor",
		"problem.asm.DesignParser", "problem.asm.FieldData", "problem.asm.GraphVisPrinter", "problem.asm.IClassData",
		"problem.asm.IFieldData", "problem.asm.IClassStructurePrinter", "problem.asm.IData", "problem.asm.IMethodCallData",
		"problem.asm.IMethodData", "problem.asm.MethodBodyVisitor", "problem.asm.MethodCallData", "problem.asm.MethodData",
		"problem.asm.SDEditPrinter", "problem.asm.StringParser", "problem.asm.VisitorManager"};
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
			
			List<String> classes; 
			
			JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"\\src");
			fc.setMultiSelectionEnabled(true);
			
			int returnVal = fc.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File[] files = fc.getSelectedFiles();
				classes = new ArrayList<>();
				for(File file: files){
					String filePath = file.getPath();
					classes.add(filePath.substring(filePath.lastIndexOf("src\\")+4, filePath.lastIndexOf(".java")));
				}
			}
			else{
				classes = Arrays.asList(DEFAULT_CLASSES);
			}
		    
			List<IClassData> classDatas = new ArrayList<>();
			for (String className : classes) {
				AbstractClassDataVisitor methodVisitor = VisitorManager.visitClass(className);
				classDatas.add(methodVisitor.getClassData());
			}
			IClassStructurePrinter gPrinter = new GraphVisPrinter(classDatas);
			gPrinter.printToFile(UML_OUTPUT);
		}
		else if(args[0].toLowerCase().equals("sd")){
			if(args.length<2){
				System.out.println("NO ARGUMENTS");
				System.exit(0);
			}
			String methodSignature = args[1];
			int depth = 5;
			if(args.length>=3){
				depth = Integer.parseInt(args[2]);
			}
			
			List<IMethodCallData> methodCalls = new ArrayList<>();
			List<String> classNames = new ArrayList<>();
			classNames.add(methodSignature.substring(0, methodSignature.lastIndexOf(".")));
			
			Queue<IMethodCallData> methodQueue = new LinkedList<>();
			
			IMethodCallData startingMethod = new MethodCallData();
			startingMethod.setMethodClass(classNames.get(0));
			startingMethod.setCallingClass("");
			startingMethod.setDepth(depth);
			startingMethod.setName(methodSignature.substring(methodSignature.lastIndexOf(".")+1, 
					methodSignature.lastIndexOf("(")));
			
			methodQueue.add(startingMethod);
		
			methodCalls = VisitorManager.getMethodCalls(startingMethod);
			
			IClassStructurePrinter sdPrinter = new SDEditPrinter(methodCalls, classNames);
			sdPrinter.printToFile(SD_OUTPUT);

		}
		else{
			System.out.println("INVALID COMMAND");
			System.exit(0);
		}
	}
}