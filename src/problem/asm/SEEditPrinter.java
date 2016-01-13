package problem.asm;

import java.util.List;

public class SEEditPrinter implements IClassStructurePrinter {
	
	private List<String> classNames;
	private List<IClassData> classes;

	public SEEditPrinter(List<IClassData> classes){
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
