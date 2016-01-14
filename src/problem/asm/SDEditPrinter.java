package problem.asm;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SDEditPrinter implements IClassStructurePrinter {
	
	private List<String> classNames;
	private List<IClassData> classes;
	private List<String> usedClasses;

	public SDEditPrinter(List<IClassData> classes){
		this.classNames = StringParser.getClassNames(classes);
		this.classes = classes;
		this.usedClasses = new ArrayList<String>();
	}

	@Override
	public void printToFile(String file) {
		try {
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
			List<IFieldData> fields = new ArrayList<>();
			if (classes.size() == 1) {
				String nm = classes.get(0).getName();
				sb.append(nm.toLowerCase() + ":" + nm + "[a]\n");
				fields = classes.get(0).getFields();
			}
			
			for (IFieldData field : fields) {
				if (!this.usedClasses.contains(field.getType())) {
					sb.append(field.getName() + ":" + field.getType() + "\n");
				}
				sb.append(field.getName() + ":" + field.getType() + "\n");
			}
			
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
