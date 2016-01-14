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
	private List<IMethodCallData> classes;
	private List<String> usedClasses;
	private IClassData startClass;
	private int depth;

	public SDEditPrinter(List<IMethodCallData> classes, List<String> classNames){
		this.classNames = classNames;
		this.classes = classes;
		this.usedClasses = new ArrayList<String>();
		
	}

	@Override
	public void printToFile(String file) {
		try {
			VisitorManager mgr = new VisitorManager();
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
//			if (classes.size() == 1) {
			String nm = this.classNames.get(0);
			sb.append(nm.toLowerCase() + ":" + nm + "[a]\n");
//			}
			
			for (String name : classNames) {
				if (!name.equals(nm)) {
					sb.append("/" + name + ":" + name + "\n");
				}
			}
			
			for (IMethodCallData meth : classes) {
				sb.append(meth.toString());
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
}
