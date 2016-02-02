package problem.asm;

import java.util.List;

public class SingletonDetector implements IPatternDetector {
	private boolean pattern = false;

	@Override
	public boolean findPattern(IClassData d) {
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
			pattern = true;
		}
		return pattern;
	}

	@Override
	public boolean getPattern() {
		return pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
	}
}
