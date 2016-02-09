package problem.detector;

import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;
import problem.model.data.SpecialArrowKey;

public class AdapterDetector implements IPatternDetector {
	public static final double MIN_PERCENT_METHODS = 0.75;
	@Override
	public void findPattern(IClassData d, IPackageModel m){
		List<String> interfaces = m.getClassToInterfaces().get(d.getName());
		List<String> associatedClasses = m.getClassToAssociatedClasses().get(d.getName());
		if(interfaces==null||associatedClasses==null){
			return;
		}
		for(String assoc: associatedClasses){
			IClassData assocData = m.getClassDataFromName(assoc);
			if(assocData==null)
				continue;
			for(String inter: interfaces){
				IClassData interData = m.getClassDataFromName(inter);
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
				if((methodsUsed/interData.getMethods().size())>=MIN_PERCENT_METHODS){
					m.addSpecialArrow(new SpecialArrowKey(d.getName(), assocData.getName(), "association"), 
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

	@Override
	public void findPattern(IClassData d) {
		
	}

}
