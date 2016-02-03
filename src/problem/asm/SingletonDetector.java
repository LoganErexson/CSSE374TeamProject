package problem.asm;

import java.io.IOException;
import java.util.List;

public class SingletonDetector implements IPatternDetector {
	private boolean pattern = false;

	@Override
	public void findPattern(IClassData d) {
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
			d.setHasPattern(true);
			d.setFill("fillcolor = yellow\n");
			d.setPattern("\n\\<\\<singleton\\>\\>\n");
		}
	}

	@Override
	public boolean getPattern() {
		return pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
	}

	@Override
	public void findPattern(IClassData d, IPackageModel m) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
