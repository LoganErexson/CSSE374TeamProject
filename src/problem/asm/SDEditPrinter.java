package problem.asm;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SDEditPrinter implements IClassStructurePrinter {
	
	private List<String> classNames;
	private List<IMethodCallData> classes;

	public SDEditPrinter(List<IMethodCallData> classes, List<String> classNames){
		this.classNames = classNames;
		this.classes = classes;
		
	}

	@Override
	public void printToFile(String file) {
		try {
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
			
			StringBuilder classesString = new StringBuilder();
			Set<String> classSet = new HashSet<>();
			String nm = StringParser.parseClassName(this.classNames.get(0));
			classesString.append(nm + ":" + nm + "[a]\n");
			
//			for (String name : this.classNames) {
//				name = StringParser.parseClassName(name);
//				if (!name.equals(nm) && !name.isEmpty()) {
//					sb.append("/" + name + ":" + name + "\n");
//				}
//			}
			sb.append("\n");
			
			for (IMethodCallData meth : this.classes) {
				if(!classSet.contains(meth.getMethodClass())){
					classSet.add(meth.getMethodClass());
					String name = StringParser.parseClassName(meth.getMethodClass());
					classesString.append(name + ":" + name + "\n");
				}
				sb.append(meth.toString() + "\n");
			}
			classesString.append(sb.toString());
			out.write(classesString.toString().getBytes());
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
