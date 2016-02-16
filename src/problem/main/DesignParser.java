package problem.main;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import problem.asm.VisitorManager;
import problem.model.data.IMethodCallData;
import problem.model.data.MethodCallData;
import problem.model.visit.IVisitor;
import problem.model.visit.SDEditVisitor;
import problem.ui.MainWindow;

public class DesignParser {
	/**
	 * Determine which folder to get classes from. Prefix here is the package name.
	 * 
	 */
	public static final String[] DEFAULT_CLASSES = {"problem.asm.AbstractClassDataVisitor",  
		"problem.asm.ClassDeclarationVisitor", "problem.asm.ClassFieldVisitor", "problem.asm.ClassMethodVisitor",
		"problem.asm.DesignParser", "problem.asm.FieldData", "problem.asm.GraphVisPrinter", 
		"problem.asm.IFieldData", "problem.asm.IClassStructurePrinter", "problem.asm.IMethodCallData",
		"problem.asm.IMethodData", "problem.asm.MethodBodyVisitor", "problem.asm.MethodCallData", "problem.asm.MethodData",
		"problem.asm.SDEditPrinter", "problem.asm.StringParser", "problem.asm.VisitorManager"};
	public static final String UML_OUTPUT = "./input_output/Diagram.dot";
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
		    MainWindow window = new MainWindow();
		    window.show();
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