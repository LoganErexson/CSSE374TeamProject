package problem.detector;

import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;
import problem.model.data.SpecialArrowKey;

public class AdapterDetector implements IPatternDetector {
	private double minimumMethods = 0.75;
	private IPackageModel m;
	
	@Override
	public void findPattern(IPackageModel model){
		this.m = model;
		for(IClassData d : this.m.getClasses()){
			findPatternInClass(d);
		}
	}
	
	private void findPatternInClass(IClassData d){
		List<String> interfaces = this.m.getClassToInterfaces().get(d.getName());
		List<String> associatedClasses = this.m.getClassToAssociatedClasses().get(d.getName());
		if(interfaces==null||associatedClasses==null){
			return;
		}
		for(String assoc: associatedClasses){
			IClassData assocData = this.m.getClassDataFromName(assoc);
			if(assocData==null)
				continue;
			for(String inter: interfaces){
				IClassData interData = this.m.getClassDataFromName(inter);
				if(interData==null)
					continue;
				int methodsUsed = 0;
				for(IMethodData method: d.getMethods()){
					if(interData.getMethods().contains(method)){
						if(method.getUsedClasses().contains(assocData.getName())){
							methodsUsed++;
						}
					}
				}
				if((methodsUsed/interData.getMethods().size())>=this.minimumMethods){
					this.m.addSpecialArrow(new SpecialArrowKey(d.getName(), assocData.getName(), "association"), 
							"\\<\\<adapts\\>\\>");
					d.setPattern("\\n\\<\\<adapter\\>\\>\\n");
					d.setHasPattern(true);
					d.setFill("fillcolor = red\n");
					assocData.setPattern("\\n\\<\\<adaptee\\>\\>\\n");
					assocData.setHasPattern(true);
					assocData.setFill("fillcolor = red\n");
					interData.setPattern("\\n\\<\\<target\\>\\>\\n");
					interData.setHasPattern(true);
					interData.setFill("fillcolor = red\n");
					return;
				}
			}
		}
	}
}
