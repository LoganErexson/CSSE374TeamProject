package problem.asm;

import java.util.List;

public class SingletonDetector implements IPatternDetector {
	private String pattern = "";

	@Override
	public String findPattern(IClassData d) {
		boolean privCon = false;
		boolean privField = false;
		boolean returnMethod = false;
		for (IFieldData field : d.getFields()) {
			if (field.getAccessLevel().equals("-") && field.getType().equals(d.getName())) {
				privField = true;
			}
		}
		for (IMethodData dat : d.getMethods()) {
			if(dat.getName().equals("<init>") && dat.getAccess().equals("-") ) {
				privCon = true;
			} else if (dat.getType().equals(d.getName())) {
				returnMethod = true;
			}
		}
		if (privCon && privField && returnMethod) {
			pattern = "\\<\\<singleton\\>\\>\n";
		}
		return pattern;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
	}
}
