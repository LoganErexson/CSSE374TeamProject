package problem.detector;

import java.util.ArrayList;
import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IFieldData;
import problem.model.data.IMethodData;
import problem.model.data.IPackageModel;

public class CompositeDetector extends AbstractDetector{
	
	public CompositeDetector(){
		this.patternName = "Composite";
	}
	@Override
	public void findPattern(IPackageModel model){
		this.m = model;
		for(IClassData d : this.m.getClasses()){
			findPatternInClass(d);
		}
	}
	
	@Override
	public void findPatternInClass(IClassData d){
		List<String> potComponents = new ArrayList<>();
		potComponents.addAll(d.getImplementedClasses());
		potComponents.add(d.getSuperClass());
		IClassData sup = this.m.getClassDataFromName(d.getSuperClass());
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
				classes.add(d);
				IClassData component;
				if(sup!=null&&sup.getImplementedClasses().contains(comp)){
					component = this.m.getClassDataFromName(sup.getName());
				}
				else{
					component = this.m.getClassDataFromName(comp);
				}
				if(component!=null){
					if(!component.hasPattern()){
						component.setHasPattern(true);
						component.setFill("fillcolor = pink\n");
						component.setPattern("\\n\\<\\<component\\>\\>\\n");
						this.classes.add(component);
					}
					if(component.isInterface()){
						for(String key : this.m.getClassToInterfaces().keySet()){
							if(this.m.getClassToInterfaces().get(key).contains(component)){
								IClassData inheritsFrom = this.m.getClassDataFromName(key);
								if(!inheritsFrom.hasPattern())
								{
									inheritsFrom.setHasPattern(true);
									inheritsFrom.setFill("fillcolor = pink\n");
									inheritsFrom.setPattern("\\n\\<\\<leaf\\>\\>\\n");
									this.classes.add(inheritsFrom);
								}
							}
						}
					}
					else{
						for(String key : this.m.getClassToSuperclass().keySet()){
							if(this.m.getClassToSuperclass().get(key).equals(component.getName())){
								IClassData extendsFrom = this.m.getClassDataFromName(key);
								if(!extendsFrom.hasPattern())
								{
									extendsFrom.setHasPattern(true);
									extendsFrom.setFill("fillcolor = pink\n");
									extendsFrom.setPattern("\\n\\<\\<leaf\\>\\>\\n");	
									this.classes.add(extendsFrom);
								}
							}
						}
					}
					for(int i = 0; i<1; i++){
						for(IClassData clazz: this.m.getClasses()){
							IClassData superClass = this.m.getClassDataFromName(clazz.getSuperClass());
							if(superClass!=null&&superClass.getPattern().contains("composite")&&!clazz.hasPattern())
							{
								clazz.setHasPattern(true);
								clazz.setFill("fillcolor = pink\n");
								clazz.setPattern("\\n\\<\\<composite\\>\\>\\n");
								this.classes.add(clazz);
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
