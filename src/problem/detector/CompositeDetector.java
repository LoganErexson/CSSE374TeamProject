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
		IClassData sup = m.getClassDataFromName(d.getSuperClass());
		if(sup!=null){
			potComponents.addAll(sup.getImplementedClasses());
		}

		for(String comp : potComponents){
			boolean isField = false;
			for(IFieldData field : d.getFields()){
				if(field.getType().contains("\\<"+comp+"\\>")){
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
				d.setPattern("\\n\\<\\<composite\\>\\>\\n");
				IClassData component;
				if(sup!=null&&sup.getImplementedClasses().contains(comp)){
					component = m.getClassDataFromName(sup.getName());
				}
				else{
					component = m.getClassDataFromName(comp);
				}
				if(component!=null){
					if(!component.hasPattern()){
						component.setHasPattern(true);
						component.setFill("fillcolor = pink\n");
						component.setPattern("\\n\\<\\<component\\>\\>\\n");
					}
					if(component.isInterface()){
						for(String key : m.getClassToInterfaces().keySet()){
							if(m.getClassToInterfaces().get(key).contains(component)){
								IClassData inheritsFrom = m.getClassDataFromName(key);
								if(!inheritsFrom.hasPattern())
								{
									inheritsFrom.setHasPattern(true);
									inheritsFrom.setFill("fillcolor = pink\n");
									inheritsFrom.setPattern("\\n\\<\\<leaf\\>\\>\\n");	
								}
							}
						}
					}
					else{
						for(String key : m.getClassToSuperclass().keySet()){
							if(m.getClassToSuperclass().get(key).equals(component.getName())){
								IClassData extendsFrom = m.getClassDataFromName(key);
								if(!extendsFrom.hasPattern())
								{
									extendsFrom.setHasPattern(true);
									extendsFrom.setFill("fillcolor = pink\n");
									extendsFrom.setPattern("\\n\\<\\<leaf\\>\\>\\n");	
								}
							}
						}
					}
					for(int i = 0; i<1; i++){
						for(IClassData clazz: m.getClasses()){
							IClassData superClass = m.getClassDataFromName(clazz.getSuperClass());
							if(superClass!=null&&superClass.getPattern().contains("composite")&&!clazz.hasPattern())
							{
								clazz.setHasPattern(true);
								clazz.setFill("fillcolor = pink\n");
								clazz.setPattern("\\n\\<\\<composite\\>\\>\\n");
								if(i>=0)
									i--;
							}
						}
					}
				}
				
			}
		}
	}

}
