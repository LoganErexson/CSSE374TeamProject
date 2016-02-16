package problem.detector;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;
import problem.model.data.SpecialArrowKey;


public class DecoratorDetector implements IPatternDetector {

	private IPackageModel m;
	@Override
	public void findPattern(IPackageModel model){
		this.m = model;
		for(IClassData d : this.m.getClasses()){
			findPatternInClass(d);
		}
	}
	
	private void findPatternInClass(IClassData d){
		IClassData sup = this.m.getClassDataFromName(d.getSuperClass());
		if (sup != null && !sup.hasPattern() && !d.hasPattern()) {
			findPatternInClass(sup);
		}
		for (IMethodData meth : d.getMethods()) {
			if (meth.getName().equals("<init>")) {
				for(String asc : d.getAssociatedClasses()) {
					if (meth.getArgs().contains(asc) && ((d.getImplementedClasses().contains(asc) && this.m.getClassDataFromName(asc) != null)
							|| (d.getSuperClass().equalsIgnoreCase(asc) && sup != null))  ) {
						IClassData in = this.m.getClassDataFromName(asc);
						if (in != null && !in.hasPattern()) {
							in.setHasPattern(true);
							in.setFill("fillcolor = green\n");
							in.setPattern("\\n\\<\\<component\\>\\>\\n");
							this.m.addSpecialArrow(new SpecialArrowKey(d.getName(), in.getName(), 
									"association"), "\\<\\<decorates\\>\\>");
						}
						d.setHasPattern(true);
						d.setFill("fillcolor = green\n");
						d.setPattern("\\n\\<\\<decorator\\>\\>\\n");
						return;
					} 
				}
			}
		}
		if (sup != null && sup.hasPattern() && sup.getPattern().contains("decorator")) {
			d.setHasPattern(true);
			d.setFill("fillcolor = green\n");
			d.setPattern("\\n\\<\\<decorator\\>\\>\\n");
			return;
		}
	}

}
