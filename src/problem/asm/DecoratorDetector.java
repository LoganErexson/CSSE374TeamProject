package problem.asm;

import java.util.List;

public class DecoratorDetector implements IPatternDetector {
	private boolean pattern = false;

	@Override
	public boolean findPattern(IClassData d) {
		boolean assocField = false;
		for (IFieldData field : d.getFields()) {
			if (field.getType().equals(d.getSuperClass())) {
				assocField = true;
			}
		}
		return false;
	}

	@Override
	public boolean getPattern() {
		return pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
	}

}
