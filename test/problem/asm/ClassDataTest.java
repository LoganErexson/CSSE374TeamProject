package problem.asm;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassDataTest {
	@Test
	public final void testSingleClass() throws IOException {
		assertEquals(
				"GraphVisPrinter [\n"
						+ "label = \"{GraphVisPrinter|- classToInterfaces : Map\\<String\\>\\l- classToSuperclass : " +
						"Map\\<String\\>\\l- classToAssociatedClasses : Map\\<String\\>\\l- classToMethods : Map\\<IMethodData\\>\\l- "+
						"classNames : List\\<String\\>\\l- classes : List\\<IClassData\\>\\l|+ \\<init\\>(List\\<IClassData\\>) : " + 
						"void\\l+ printToFile(String) : void\\l+ getClassToInterfaces() : Map\\<String\\>\\l+ setClassToInterfaces(Map\\<String\\>, "+
						"List\\<String\\>) : void\\l+ getClassToSuperclass() : Map\\<String\\>\\l+ setClassToSuperclass(Map\\<String\\>, "+
						"String) : void\\l+ getClassToAssociatedClasses() : Map\\<String\\>\\l+ setClassToAssociatedClasses(Map\\"+
						"<String\\>, List\\<String\\>) : void\\l+ getClassToMethods() : Map\\<IMethodData\\>\\l+ setClassToMethods(Map"+
						"\\<String\\>, List\\<IMethodData\\>) : void\\l+ getClassNames() : List\\<String\\>\\l+ setClassNames(List\\"+
						"<String\\>) : void\\l+ getClasses() : List\\<IClassData\\>\\l+ setClasses(List\\<IClassData\\>) : void\\l+ "+
						"createArrows() : String\\l- getUsedClassesArrows() : String\\l}\"\n" 
						+ "]\n", VisitorManager.visitClass("problem.asm.GraphVisPrinter").getClassData().toString());
	}

	@Test
	public final void testInterfaces() throws IOException {
		List<IClassData> datas = new ArrayList<>();
		datas.add(VisitorManager.visitClass("lab1_3/Behavior").getClassData());
		datas.add(VisitorManager.visitClass("lab1_3/HTMLBehavior").getClassData());
		GraphVisPrinter printer = new GraphVisPrinter(datas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: datas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getInterfaces());
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
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}
		classDatas.add(VisitorManager.visitClass("problem.asm.ClassMethodVisitor").getClassData());
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getInterfaces());
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
	public final void testToStringBase() {
		IClassData c = new ClassData();
		c.setName("Test");
		List<String> inters = new ArrayList<>();
		inters.add("Interface");
		c.setInterfaces(inters);
		c.setSuperClass("Super");
		Type f = Type.CHAR_TYPE;
		c.addField(new FieldData("AField", "+", f, null));
		Type[] t = {};
		c.addMethod(new MethodData("AMEthod", Type.VOID_TYPE, "+", t, null));

		assertEquals("Test [\n" + "label = "
				+ "\"{Test|+ AField : char\\l|+ AMEthod() : void\\l" + "}\"\n"
				+ "]\n", c.toString());
	}

	@Test
	public final void testUsesArrow() throws IOException {
		String[] CLASSES = { "problem.asm.ClassMethodVisitor",
				"problem.asm.ClassDeclarationVisitor",
				"problem.asm.ClassFieldVisitor", "problem.asm.DesignParser",
				"problem.asm.FieldData", "problem.asm.MethodData",
				"problem.asm.AbstractClassDataVisitor" };
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}	
		classDatas.add(VisitorManager.visitClass("problem.asm.ClassData").getClassData());
		//List<String> classNames = StringParser.getClassNames(classDatas);
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getInterfaces());
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
		String[] CLASSES = {"problem.asm.IClassData"};
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}	
		classDatas.add(VisitorManager.visitClass("problem.asm.AbstractClassDataVisitor").getClassData());
		GraphVisPrinter printer = new GraphVisPrinter(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getInterfaces());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		printer.setClassToSuperclass(classToSuperclass);
		printer.setClassToInterfaces(classToInterfaces);
		printer.setClassToMethods(classToMethods);
		printer.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(printer.createArrows().contains("AbstractClassDataVisitor -> IClassData"));
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
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
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
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
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
}
