package problem.asm;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class SDEditPrinter implements IClassStructurePrinter {
	
	private List<String> classNames;
	private List<IClassData> classes;
	private List<String> usedClasses;
	private IClassData startClass;
	private String methodPath;
	private int depth;

	public SDEditPrinter(List<IClassData> classes, String method){
		this.classNames = StringParser.getClassNames(classes);
		this.classes = classes;
		this.usedClasses = new ArrayList<String>();
		this.methodPath = method;
		this.startClass = classes.get(0);
	}

	@Override
	public void printToFile(String file) {
		try {
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
			if (classes.size() == 1) {
				String nm = startClass.getName();
				sb.append(nm.toLowerCase() + ":" + nm + "[a]\n");
			}
			
			
			
//			for (IFieldData field : fields) {
//				if (!this.usedClasses.contains(field.getType())) {
//					sb.append(field.getName() + ":" + field.getType() + "\n");
//				}
//			}
			
			out.write(sb.toString().getBytes());
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public String createArrows() {
		// TODO Auto-generated method stub
		return null;
	}

	public void visitClass(String className) throws IOException {
		// ASM's ClassReader does the heavy lifting of parsing the compiled Java class
		ClassReader reader = new ClassReader(className.substring(0, className.lastIndexOf(".")));
		// make class declaration visitor to get superclass and interfaces
		AbstractClassDataVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, null);
		// DECORATE declaration visitor with field visitor
		AbstractClassDataVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor);
		// DECORATE field visitor with method visitor
		AbstractClassDataVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
				fieldVisitor);
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		this.classes.add(methodVisitor.getClassData());
	}
}
