package problem.asm;

import java.io.IOException;
import java.util.List;

public class DecoratorDetector implements IPatternDetector {
	private boolean pattern = false;

	@Override
	public void findPattern(IClassData d, IPackageModel m) throws IOException {
		IClassData sup = m.getClassDataFromName(d.getSuperClass());
		for (IFieldData field : d.getFields()) {
			if (field.getType().equals(d.getSuperClass())) {
				sup.setFill("fillcolor = green\n");
				sup.setPattern("\n\\<\\<component\\>\\>\n");
				d.setHasPattern(true);
				d.setFill("fillcolor = green\n");
				d.setPattern("\n\\<\\<decorator\\>\\>\n");
			}
		}
		//if (sup.hasPattern() && sup.getPattern().contains("decorator"))
			//return true;
	}

	@Override
	public boolean getPattern() {
		return pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
	}

	@Override
	public void findPattern(IClassData d) {
		// TODO Auto-generated method stub
		
	}

}
