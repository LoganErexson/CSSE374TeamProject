package problem.asm;

import java.util.List;

public class SDEditPrinter implements IClassStructurePrinter {
	
	private List<String> classNames;
	private List<IClassData> classes;

	public SDEditPrinter(List<IClassData> classes){
		this.classNames = StringParser.getClassNames(classes);
		this.classes = classes;
	}

	@Override
	public void printToFile(String file) {
		// TODO Auto-generated method stub

	}

	@Override
	public String createArrows() {
		// TODO Auto-generated method stub
		return null;
	}

}
