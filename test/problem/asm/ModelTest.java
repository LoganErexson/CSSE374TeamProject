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

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.Type;

import problem.detector.AdapterDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.main.StringParser;
import problem.model.data.ClassData;
import problem.model.data.FieldData;
import problem.model.data.IClassData;
import problem.model.data.IFieldData;
import problem.model.data.IMethodCallData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;
import problem.model.data.MethodCallData;
import problem.model.data.MethodData;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.SDEditVisitor;


public class ModelTest {
	
	private List<IPatternDetector> detectors;
	
	@Before
	public void setUp(){
		List<IPatternDetector> detectors = new ArrayList<>();
		detectors.add(new SingletonDetector());
		detectors.add(new DecoratorDetector());
		detectors.add(new AdapterDetector());
		detectors.add(new InterfaceDetector());
		this.detectors = detectors;
	}
	@Test
	public final void testSingleClass() throws IOException {
		assertEquals("A [\n" +
				"label = \"{A|+ b : B\\l|+ \\<init\\>() : void\\l+ doB() : void\\l}\"" +
						"\n]\n", VisitorManager.visitClass("problem.asm.A").getClassData().getUMLString());
	}

	@Test
	public final void testInterfaces() throws IOException {
		List<IClassData> datas = new ArrayList<>();
		datas.add(VisitorManager.visitClass("sample.lab1_3.Behavior").getClassData());
		datas.add(VisitorManager.visitClass("sample.lab1_3.HTMLBehavior").getClassData());
		IPackageModel model = new PackageModel(this.detectors);
		model.setClasses(datas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: datas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		model.setClassToSuperclass(classToSuperclass);
		model.setClassToInterfaces(classToInterfaces);
		model.setClassToMethods(classToMethods);
		model.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(model.createArrows().contains("HTMLBehavior -> Behavior"));
	}

	@Test
	public final void testSuperClasses() throws IOException {
		String[] CLASSES = {"problem.asm.AbstractASMVisitor" };
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}
		classDatas.add(VisitorManager.visitClass("problem.asm.ClassMethodVisitor").getClassData());
		IPackageModel model = new PackageModel(this.detectors);
		model.setClasses(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		model.setClassToSuperclass(classToSuperclass);
		model.setClassToInterfaces(classToInterfaces);
		model.setClassToMethods(classToMethods);
		model.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(model.createArrows().contains("ClassMethodVisitor -> AbstractASMVisitor"));
	}

	@Test
	public final void testUMLStringBase() {
		IClassData c = new ClassData();
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
				"problem.asm.ClassFieldVisitor", "problem.main.DesignParser",
				"problem.model.data.FieldData", "problem.model.data.MethodData",
				"problem.asm.AbstractASMVisitor" };
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}	
		//List<String> classNames = StringParser.getClassNames(classDatas);
		IPackageModel model = new PackageModel(this.detectors);
		model.setClasses(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		model.setClassToSuperclass(classToSuperclass);
		model.setClassToInterfaces(classToInterfaces);
		model.setClassToMethods(classToMethods);
		model.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(model.createArrows().contains("ClassMethodVisitor -> MethodData"));
	}

	@Test
	public final void testAssociationArrows() throws IOException {
		String[] CLASSES = {"problem.asm.A"};
		String expected = "edge [\narrowhead = \"vee\"\nstyle = \"solid\"\n]\nA -> B";
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className).getClassData());
		}	
		classDatas.add(VisitorManager.visitClass("problem.asm.B").getClassData());
		IPackageModel model = new PackageModel(this.detectors);
		model.setClasses(classDatas);
		Map<String, List<String>> classToInterfaces = new HashMap<>();
		Map<String, String> classToSuperclass = new HashMap<>();
		Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
		Map<String, List<IMethodData>> classToMethods = new HashMap<>();
		for(IClassData currentData: classDatas){
			classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			classToMethods.put(currentData.getName(), currentData.getMethods());
		}
		model.setClassToSuperclass(classToSuperclass);
		model.setClassToInterfaces(classToInterfaces);
		model.setClassToMethods(classToMethods);
		model.setClassToAssociatedClasses(classToAssociatedClasses);
		assertTrue(model.createArrows().contains(expected));
	}
	
	@Test
	public final void testSignatureParsing(){
		String signature = "(Ljava/util/List<Ljava/lang/String;>;Ljava/lang/String;)Ljava/util/List<Ljava/lang/String;>;";
		List<String> parameters = new ArrayList<>();
		parameters.add("String");
		assertEquals(parameters, StringParser.parametersFromSignature(signature));
		assertEquals("List\\<String\\>", StringParser.returnTypeFromSignature(signature));
	}
	
	@Test 
	public final void testMethodCallClassName() throws IOException {
		String[] CLASSES = { "problem.asm.ClassMethodVisitor",
				"problem.asm.ClassDeclarationVisitor",
				"problem.asm.ClassFieldVisitor", "problem.main.DesignParser",
				"problem.model.data.FieldData", "problem.model.data.MethodData",
				"problem.asm.AbstractASMVisitor" };
		List<AbstractASMVisitor> classDatas = new ArrayList<>();
		for (String className : CLASSES) {
			classDatas.add(VisitorManager.visitClass(className));
		}
		String methodSignature = "problem.main.DesignParser.main(String[])";
		List<String> classNames = new ArrayList<>();
		classNames.add(methodSignature.substring(0, methodSignature.lastIndexOf(".")));
		
		IMethodCallData startingMethod = new MethodCallData();
		startingMethod.setMethodClass(classNames.get(0));
		startingMethod.setCallingClass("");
		startingMethod.setDepth(5);
		startingMethod.setName(methodSignature.substring(methodSignature.lastIndexOf(".")+1, 
				methodSignature.lastIndexOf("(")));
		
		assertEquals("problem.main.DesignParser", startingMethod.getMethodClass());
	}
	
	@Test
	public final void testDepthOf2withSimpleClasses() throws IOException {
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
		
		IVisitor sdVisitor = new SDEditVisitor(className);
		for(IMethodCallData data: methodCalls){
			data.accept(sdVisitor);
		}
		OutputStream out = new ByteArrayOutputStream();
		sdVisitor.printToOutput(out);
		assertEquals(expected, out.toString());
		out.close();
	}
	@Test
	public final void testMethodCallDataToString(){
		IMethodCallData callData = new MethodCallData();
		callData.setCallingClass("problem.asm.X");
		callData.setMethodClass("problem/asm/Y");
		callData.setName("fooBar");
		Type[] args = {};
		IMethodData method = new MethodData("fooBar", null,"+", args, "(String;int;)List<String;>");
		callData.setMethod(method);
		
		assertEquals("X:List<String>=Y.fooBar(String, int)", callData.toString());
	}
	
	@Test
	public final void testMethodDataToString(){

		Type[] args = {Type.getType(int.class), Type.getType(Boolean.class)};
		IMethodData methodA = new MethodData("fooBar", Type.getType(String.class), "-", args, null);
		assertEquals("- fooBar(int, Boolean) : String\\l", methodA.toString());

		IMethodData methodB = new MethodData("barFoo", null, "-", null, "(String;int;)List<ClassData;>");
		assertEquals("- barFoo(String, int) : List\\<ClassData\\>\\l", methodB.toString());
		
		IMethodData methodC = new MethodData("boFaRo", null, "-", null, "(List<ClassData;>;int;)Double");
		assertEquals("- boFaRo(List\\<ClassData\\>, int) : Double\\l", methodC.toString());
	}
	
	@Test
	public final void testFieldDataToString(){
		IFieldData fieldA = new FieldData("name", "#", Type.getType(Float.class), null);
		assertEquals("# name : Float\\l", fieldA.toString());
		
		IFieldData fieldB = new FieldData("set", "-", null, "Set<Integer>");
		assertEquals("- set : Set\\<Integer\\>\\l", fieldB.toString());
	}
}
