package problem.detector;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;
import problem.model.data.SpecialArrowKey;

public class CompositeDetector implements IPatternDetector{

	@Override
	public void findPattern(IClassData d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findPattern(IClassData d, IPackageModel m) {
		IClassData sup = m.getClassDataFromName(d.getSuperClass());
		if (sup != null && !sup.hasPattern() && !d.hasPattern()) {
			findPattern(sup, m);
		}
		for (IMethodData meth : d.getMethods()) {
			if (meth.getName().equals("<init>")) {
				if (meth.getArgs().contains(sup) && sup != null && (sup.isInterface())) {
					if (sup != null && !sup.hasPattern()) {
						sup.setHasPattern(true);
						sup.setFill("fillcolor = pink\n");
						sup.setPattern("\\n\\<\\<component\\>\\>\\n");
					}
					d.setHasPattern(true);
					d.setFill("fillcolor = pink\n");
					d.setPattern("\\n\\<\\<composite\\>\\>\\n");
					return;
				} 
			}
		}
		if (sup != null && sup.hasPattern() && sup.getPattern().contains("component")) {
			d.setHasPattern(true);
			d.setFill("fillcolor = pink\n");
			d.setPattern("\\n\\<\\<leaf\\>\\>\\n");
			return;
		}
	}

}
