package problem.asm;


public class DecoratorDetector implements IPatternDetector {

	@Override
	public void findPattern(IClassData d, IPackageModel m) {
		IClassData sup = m.getClassDataFromName(d.getSuperClass());
		if (sup != null && !sup.hasPattern() && !d.hasPattern()) {
			findPattern(sup, m);
		}
		for (IMethodData meth : d.getMethods()) {
			if (meth.getName().equals("<init>")) {
				for(String asc : d.getAssociatedClasses()) {
					if (meth.getArgs().contains(asc) && ((d.getImplementedClasses().contains(asc) && m.getClassDataFromName(asc) != null)
							|| (d.getSuperClass().equalsIgnoreCase(asc) && sup != null))  ) {
						IClassData in = m.getClassDataFromName(asc);
						if (in != null && !in.hasPattern()) {
							in.setHasPattern(true);
							in.setFill("fillcolor = green\n");
							in.setPattern("\\n\\<\\<component\\>\\>\\n");
							m.addSpecialArrow(new SpecialArrowKey(d.getName(), in.getName(), 
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


	@Override
	public void findPattern(IClassData d) {
		
	}

}
