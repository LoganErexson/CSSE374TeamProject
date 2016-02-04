package problem.asm;

import java.util.List;

public class AdapterDetector implements IPatternDetector {

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
				boolean hasPattern = true;
				for(IMethodData method: d.getMethods()){
					if(interData.getMethods().contains(method)){
						if(!method.getUsedClasses().contains(assocData.getName())){
							hasPattern = false;
							break;
						}
					}
				}
				if(hasPattern){
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
