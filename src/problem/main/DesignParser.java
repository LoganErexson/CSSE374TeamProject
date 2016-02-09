package problem.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import problem.asm.AbstractASMVisitor;
import problem.asm.VisitorManager;
import problem.detector.AdapterDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.model.data.IClassData;
import problem.model.data.IMethodCallData;
import problem.model.data.IPackageModel;
import problem.model.data.MethodCallData;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.SDEditVisitor;
import problem.model.visit.UMLVisitor;

public class DesignParser {
	/**
	 * Determine which folder to get classes from. Prefix here is the package name.
	 * 
	 * For project files: FOLDER_PATH = "./src/problem/asm"; FILE_PREFIX = "problem.asm";
	 * For pizzaaf files: FOLDER_PATH = "./src/headfirst/factory/pizzaaf"; FILE_PREFIX = "headfirst.factory.pizzaaf";
	 * For lab 1-3 files: FOLDER_PATH = "./src/lab1_3"; FILE_PREFIX = "lab1_3"
	 * 
	 */
	public static final String[] DEFAULT_CLASSES = {"problem.asm.AbstractClassDataVisitor",  
		"problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor", "problem.asm.ClassMethodVisitor",
		"problem.asm.DesignParser", "problem.asm.FieldData", "problem.asm.GraphVisPrinter", 
		"problem.asm.IFieldData", "problem.asm.IClassStructurePrinter", "problem.asm.IMethodCallData",
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
			
			List<String> classes = new ArrayList<>(); 
			if(args.length>1){
				BufferedReader br = new BufferedReader(new FileReader(args[1]));
				String line;
				while((line = br.readLine())!=null){
					classes.add(line);
				}
				br.close();
			}
			else{
				JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"\\src");
				fc.setMultiSelectionEnabled(true);
				
				int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File[] files = fc.getSelectedFiles();
					for(File file: files){
						String filePath = file.getPath();
						classes.add(filePath.substring(filePath.lastIndexOf("src\\")+4, filePath.lastIndexOf(".java")));
					}
				}
				else{
					classes = Arrays.asList(DEFAULT_CLASSES);
				}
			}
		    
			List<IClassData> classDatas = new ArrayList<>();
			for (String className : classes) {
				AbstractASMVisitor visitor = VisitorManager.visitClass(className);
				classDatas.add(visitor.getClassData());
			}
			
			List<IPatternDetector> detectors = new ArrayList<>();
			detectors.add(new SingletonDetector());
			detectors.add(new DecoratorDetector());
			detectors.add(new AdapterDetector());
			detectors.add(new InterfaceDetector());
			
			IPackageModel model = new PackageModel(detectors);
			model.setClasses(classDatas);
			OutputStream out = new FilterOutputStream(new FileOutputStream(UML_OUTPUT));
			IVisitor visitor = new UMLVisitor();
			model.accept(visitor);
			visitor.printToOutput(out);
			out.close();
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
			String firstClass = methodSignature.substring(0, methodSignature.lastIndexOf("."));
			
			IMethodCallData startingMethod = new MethodCallData();
			startingMethod.setMethodClass(firstClass);
			startingMethod.setCallingClass("");
			startingMethod.setDepth(depth + 1);
			startingMethod.setName(methodSignature.substring(methodSignature.lastIndexOf(".")+1, 
					methodSignature.lastIndexOf("(")));
		
			methodCalls = VisitorManager.getMethodCalls(startingMethod);
			
			IVisitor visitor = new SDEditVisitor(firstClass);
			for(IMethodCallData data: methodCalls){
				data.accept(visitor);
			}
			OutputStream out = new FilterOutputStream(new FileOutputStream(SD_OUTPUT));
			visitor.printToOutput(out);
			out.close();

		}
		else{
			System.out.println("INVALID COMMAND");
			System.exit(0);
		}
	}
}