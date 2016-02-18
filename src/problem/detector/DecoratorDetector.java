package problem.detector;

import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.SpecialArrowKey;


public class DecoratorDetector extends AbstractDetector {

	private int minimumMethods;
	
	public DecoratorDetector(int minimumMethods){
		this.minimumMethods=minimumMethods;
		this.patternName = "Decorator";
	}
	
	@Override
	public void findPatternInClass(IClassData d){
		IClassData sup = this.m.getClassDataFromName(d.getSuperClass());
		if (sup != null && !sup.hasPattern() && !d.hasPattern()) {
			findPatternInClass(sup);
		}
		if (sup != null && sup.hasPattern() && sup.getPattern().contains("decorator")) {
			d.setHasPattern(true);
			d.setFill("fillcolor = green\n");
			d.setPattern("\\n\\<\\<decorator\\>\\>\\n");
			this.classes.add(d);
			return;
		}

		List<String> parentClasses = d.getImplementedClasses();
		parentClasses.add(d.getSuperClass());
		for(String inter : parentClasses){

			IClassData interData = this.m.getClassDataFromName(inter);
			if(interData==null)
				continue;
			int methodsUsed = 0;
			if(d.getAssociatedClasses().contains(inter)){
				for(IMethodData meth: d.getMethods()){
					if (meth.getName().equals("<init>")&&!meth.getArgs().contains(inter)){
						return;
					}
					if(interData.getMethods().contains(meth)){
						if(meth.getUsedClasses().contains(inter)){
							methodsUsed++;
						}
					}
				}
			}
			if(methodsUsed>=this.minimumMethods){
				IClassData in = this.m.getClassDataFromName(inter);
				if (in != null && !in.hasPattern()) {
					in.setHasPattern(true);
					in.setFill("fillcolor = green\n");
					in.setPattern("\\n\\<\\<component\\>\\>\\n");
					this.m.addSpecialArrow(new SpecialArrowKey(d.getName(), in.getName(), 
							"association"), "\\<\\<decorates\\>\\>");
					this.classes.add(in);
				}
				d.setHasPattern(true);
				d.setFill("fillcolor = green\n");
				d.setPattern("\\n\\<\\<decorator\\>\\>\\n");
				this.classes.add(d);
				return;
			}	
		}
	}

	@Override
	public void update(String valueName, String newValue) {
		if(valueName.equals("MethodDelegation")){
			this.minimumMethods = Integer.parseInt(newValue);
		}
	}

}
