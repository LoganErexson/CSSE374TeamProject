package problem.detector;

import java.util.ArrayList;
import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IFieldData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;

public class CompositeDetector implements IPatternDetector{

	@Override
	public void findPattern(IClassData d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findPattern(IClassData d, IPackageModel m) {
		List<String> potComponents = new ArrayList<>();
		potComponents.addAll(d.getImplementedClasses());
		potComponents.add(d.getSuperClass());
//		if (sup != null && !sup.hasPattern() && !d.hasPattern()) {
//			findPattern(sup, m);
//		}
		for(String comp : potComponents){
			boolean isField = false;
			for(IFieldData field : d.getFields()){
				if(field.getName().contains(comp)){
					isField = true;
					break;
				}
			}
			boolean hasMethod = false;
			for(IMethodData method : d.getMethods()){
				if(method.getArgs().contains(comp)){
					hasMethod = true;
					break;
				}
			}
			if(isField&&hasMethod){
				d.setHasPattern(true);
				d.setFill("fillcolor = pink\n");
				d.setPattern("\\n\\<\\<Composite\\>\\>\\n");
				IClassData component = m.getClassDataFromName(comp);
				if(!component.hasPattern()){
					component.setHasPattern(true);
					component.setFill("fillcolor = pink\n");
					component.setPattern("\\n\\<\\<Component\\>\\>\\n");
				}
				if(component.isInterface()){
					for(String key : m.getClassToInterfaces().keySet()){
						if(m.getClassToInterfaces().get(key).contains(component)){
							IClassData inheritsFrom = m.getClassDataFromName(key);
							if(!inheritsFrom.hasPattern())
							{
								inheritsFrom.setHasPattern(true);
								inheritsFrom.setFill("fillcolor = pink\n");
								inheritsFrom.setPattern("\\n\\<\\<Leaf\\>\\>\\n");	
							}
						}
					}
				}
				else{
					for(String key : m.getClassToSuperclass().keySet()){
						if(m.getClassToSuperclass().get(key).equals(component)){
							IClassData inheritsFrom = m.getClassDataFromName(key);
							if(!inheritsFrom.hasPattern())
							{
								inheritsFrom.setHasPattern(true);
								inheritsFrom.setFill("fillcolor = pink\n");
								inheritsFrom.setPattern("\\n\\<\\<Leaf\\>\\>\\n");	
							}
						}
					}
				}
			}
		}
//		for (IMethodData meth : d.getMethods()) {
//			if (!meth.getName().equals("<init>")) {
//				if (meth.getArgs().contains(sup) && sup != null && (sup.isInterface())) {
//					if (sup != null && !sup.hasPattern()) {
//						sup.setHasPattern(true);
//						sup.setFill("fillcolor = pink\n");
//						sup.setPattern("\\n\\<\\<component\\>\\>\\n");
//					}
//					d.setHasPattern(true);
//					d.setFill("fillcolor = pink\n");
//					d.setPattern("\\n\\<\\<composite\\>\\>\\n");
//					return;
//				} 
//			}
//		}
//		if (sup != null && sup.hasPattern() && sup.getPattern().contains("component")) {
//			d.setHasPattern(true);
//			d.setFill("fillcolor = pink\n");
//			d.setPattern("\\n\\<\\<leaf\\>\\>\\n");
//			return;
//		}
	}

}
