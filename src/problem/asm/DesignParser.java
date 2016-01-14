package problem.asm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
			List<String> classes = VisitorManager.getClassNames(folderPath, filePrefix);
		    
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
			
			List<IClassData> classDatas = new ArrayList<>();
			List<IMethodCallData> methodCalls = new ArrayList<>();
			List<String> classNames = new ArrayList<>();
			classNames.add(methodSignature.substring(0, methodSignature.lastIndexOf(".")));
			
			Queue<IMethodCallData> methodQueue = new LinkedList<>();
			
			IMethodCallData startingMethod = new MethodCallData();
			startingMethod.setMethodClass(classNames.get(0));
			startingMethod.setCallingClass("");
			startingMethod.setDepth(depth);
			
			methodQueue.add(startingMethod);
			
			ClassMethodVisitor methodVisitor;
			while(true){
				IMethodCallData currentMethod = methodQueue.poll();
				methodCalls.add(currentMethod);
				if(!classNames.contains(currentMethod.getCallingClass()))
				{
					classNames.add(currentMethod.getCallingClass());
				}
				if(currentMethod.getDepth()==0){
					break;
				}
				methodVisitor = (ClassMethodVisitor) VisitorManager.visitClass(classNames.get(0));
				classDatas.add(methodVisitor.getClassData());
				for(IMethodCallData callData: methodVisitor.getMethodCalls()){
					callData.setDepth(currentMethod.getDepth()-1);
					methodQueue.add(callData);
				}
			}
			
			IClassStructurePrinter sdPrinter = new SDEditPrinter(methodCalls, classNames);
			sdPrinter.printToFile(SD_OUTPUT);

		}
		else{
			System.out.println("INVALID COMMAND");
			System.exit(0);
		}
	}
}