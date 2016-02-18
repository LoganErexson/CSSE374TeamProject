package problem.detector;

import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IMethodData;
import problem.model.data.SpecialArrowKey;

public class AdapterDetector extends AbstractDetector {
	private int minimumMethods;
	
	public AdapterDetector(int minimumMethods){
		this.minimumMethods=minimumMethods;
		this.patternName = "Adapter";
	}
	
	@Override
	public void findPatternInClass(IClassData d){
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
				if(interData==null||assocData.equals(interData))
					continue;
				int methodsUsed = 0;
				boolean isInConstructor = false;
				for(IMethodData method: d.getMethods()){
					if(interData.getMethods().contains(method)){
						if(method.getUsedClasses().contains(assocData.getName())){
							methodsUsed++;
						}
					}
					if(method.getName().contains("<init")){
						for(String arg : method.getArgs()){
							if(arg.contains("\\<")){
								arg = arg.substring(0, arg.indexOf("\\<"));
							}
							if(arg.equals(assoc)){
								isInConstructor = true;
								break;
							}
						}
					}
				}
				if(methodsUsed>=this.minimumMethods&&isInConstructor){
					this.m.addSpecialArrow(new SpecialArrowKey(d.getName(), assocData.getName(), "association"), 
							"\\<\\<adapts\\>\\>");
					d.setPattern("\\n\\<\\<adapter\\>\\>\\n");
					d.setHasPattern(true);
					d.setFill("fillcolor = red\n");
					this.classes.add(d);
					
					assocData.setPattern("\\n\\<\\<adaptee\\>\\>\\n");
					assocData.setHasPattern(true);
					assocData.setFill("fillcolor = red\n");
					this.classes.add(assocData);
					
					interData.setPattern("\\n\\<\\<target\\>\\>\\n");
					interData.setHasPattern(true);
					interData.setFill("fillcolor = red\n");
					this.classes.add(interData);
					
					return;
				}
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
