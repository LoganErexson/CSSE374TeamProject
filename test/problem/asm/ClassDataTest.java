package problem.asm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.org.objectweb.asm.Opcodes;

import org.junit.Test;
import org.objectweb.asm.Type;

public class ClassDataTest {
	@Test
	public final void testSingleClass() throws IOException {
		assertEquals("A [\n" +
				"label = \"{A|+ b : B\\l|+ \\<init\\>() : void\\l+ doB() : void\\l}\"" +
						"\n]\n", VisitorManager.visitClass("problem.asm.A").getUMLString());
	}

	@Test
	public final void testInterfaces() throws IOException {
		List<AbstractClassDataVisitor> datas = new ArrayList<>();
		datas.add(VisitorManager.visitClass("lab1_3/Behavior"));
		datas.add(VisitorManager.visitClass("lab1_3/HTMLBehavior"));
		GraphVisPrinter printer = new GraphVisPrinter(datas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(AbstractClassDataVisitor currentData: datas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		printer.setClassToSuperclass(classToSuperclass);
		printer.setClassToInterfaces(classToInterfaces);
		printer.setClassToMethods(classToMethods);
		printer.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(printer.createArrows().contains("HTMLBehavior -> Behavior"));
	}

	@Test
	public final void testSuperClasses() throws IOException {
		String[] CLASSES = {"problem.asm.AbstractClassDataVisitor" };
		List<AbstractClassDataVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}
		classDatas.add(VisitorManager.visitClass("problem.asm.ClassMethodVisitor"));
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(AbstractClassDataVisitor currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		printer.setClassToSuperclass(classToSuperclass);
		printer.setClassToInterfaces(classToInterfaces);
		printer.setClassToMethods(classToMethods);
		printer.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(printer.createArrows().contains("ClassMethodVisitor -> AbstractClassDataVisitor"));
	}

	@Test
	public final void testUMLStringBase() {
		AbstractClassDataVisitor c = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		c.setName("Test");
		List<String> inters = new ArrayList<>();
		inters.add("Interface");
		c.setImplementedClasses(inters);
		c.setSuperClass("Super");
		Type f = Type.CHAR_TYPE;
		c.addField(new FieldData("AField", "+", f, null));
		Type[] t = {};
		c.addMethod(new MethodData("AMEthod", Type.VOID_TYPE, "+", t, null));

		assertEquals("Test [\n" + "label = "
				+ "\"{Test|+ AField : char\\l|+ AMEthod() : void\\l" + "}\"\n"
				+ "]\n", c.getUMLString());
	}

	@Test
	public final void testUsesArrow() throws IOException {
		String[] CLASSES = { "problem.asm.ClassMethodVisitor",
				"problem.asm.ClassDeclarationVisitor",
				"problem.asm.ClassFieldVisitor", "problem.asm.DesignParser",
				"problem.asm.FieldData", "problem.asm.MethodData",
				"problem.asm.AbstractClassDataVisitor" };
		List<AbstractClassDataVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}	
		//List<String> classNames = StringParser.getClassNames(classDatas);
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(AbstractClassDataVisitor currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		printer.setClassToSuperclass(classToSuperclass);
		printer.setClassToInterfaces(classToInterfaces);
		printer.setClassToMethods(classToMethods);
		printer.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(printer.createArrows().contains("ClassMethodVisitor -> MethodData"));
	}

	@Test
	public final void testAssociationArrows() throws IOException {
		String[] CLASSES = {"problem.asm.A"};
		List<AbstractClassDataVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}	
		classDatas.add(VisitorManager.visitClass("problem.asm.B"));
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(AbstractClassDataVisitor currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		printer.setClassToSuperclass(classToSuperclass);
		printer.setClassToInterfaces(classToInterfaces);
		printer.setClassToMethods(classToMethods);
		printer.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(printer.createArrows().contains("A -> B"));
	}
	
	@Test
	public final void testSignatureParsing(){
		String signature = "(Ljava/util/List<Ljava/lang/String;>;Ljava/lang/String;)Ljava/util/List<Ljava/lang/String;>;";
		List<String> parameters = new ArrayList<>();
		parameters.add("List\\<String\\>");
		parameters.add("String");
		assertEquals(parameters, StringParser.parametersFromSignature(signature));
		assertEquals("List\\<String\\>", StringParser.returnTypeFromSignature(signature));
	}
	
	@Test 
	public final void testMethodCallClassName() throws IOException {
		String[] CLASSES = { "problem.asm.ClassMethodVisitor",
				"problem.asm.ClassDeclarationVisitor",
				"problem.asm.ClassFieldVisitor", "problem.asm.DesignParser",
				"problem.asm.FieldData", "problem.asm.MethodData",
				"problem.asm.AbstractClassDataVisitor" };
		List<AbstractClassDataVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}
		String methodSignature = "problem.asm.DesignParser.main(String[])";
		List<String> classNames = new ArrayList<>();
		classNames.add(methodSignature.substring(0, methodSignature.lastIndexOf(".")));
		
		IMethodCallData startingMethod = new MethodCallData();
		startingMethod.setMethodClass(classNames.get(0));
		startingMethod.setCallingClass("");
		startingMethod.setDepth(5);
		startingMethod.setName(methodSignature.substring(methodSignature.lastIndexOf(".")+1, 
				methodSignature.lastIndexOf("(")));
		
		assertEquals("problem.asm.DesignParser", startingMethod.getMethodClass());
	}
	
	@Test 
	public final void testMethodCallInnerMethodCall() throws IOException {
		String[] CLASSES = { "problem.asm.ClassMethodVisitor",
				"problem.asm.ClassDeclarationVisitor",
				"problem.asm.ClassFieldVisitor", "problem.asm.DesignParser",
				"problem.asm.FieldData", "problem.asm.MethodData",
				"problem.asm.AbstractClassDataVisitor" };
		List<AbstractClassDataVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}
		String methodSignature = "problem.asm.DesignParser.main(String[])";
		//List<IMethodCallData> methodCalls = new ArrayList<>();
		List<String> classNames = new ArrayList<>();
		classNames.add(methodSignature.substring(0, methodSignature.lastIndexOf(".")));
		
		//Queue<IMethodCallData> methodQueue = new LinkedList<>();
		
		IMethodCallData startingMethod = new MethodCallData();
		startingMethod.setMethodClass(classNames.get(0));
		startingMethod.setCallingClass("");
		startingMethod.setDepth(5);
		startingMethod.setName(methodSignature.substring(methodSignature.lastIndexOf(".")+1, 
				methodSignature.lastIndexOf("(")));
		
		assertEquals("", startingMethod.getCallingClass());
	}
	
	@Test
	public final void testDepthOf5withSimpleClasses() throws IOException {
		String expected = "A:A[a]\nPrintStream:PrintStream\nB:B\n\n" +
				"A:PrintStream.println(Object)\nA:B.doC()\n";
		
		String className = "problem.asm.A";
		String methodName = "doB";
		List<IMethodCallData> methodCalls = new ArrayList<>();
		
		IMethodCallData startingMethod = new MethodCallData();
		startingMethod.setMethodClass(className);
		startingMethod.setCallingClass("");
		startingMethod.setDepth(2);
		startingMethod.setName(methodName);
		
		methodCalls = VisitorManager.getMethodCalls(startingMethod);
		
		IClassStructurePrinter sdPrinter = new SDEditPrinter(methodCalls, className);
		OutputStream out = new ByteArrayOutputStream();
		sdPrinter.printToFile(out);
		assertEquals(expected, out.toString());
		out.close();
	}
}
