package problem.asm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageModel implements IPackageModel {
	
	private List<IClassData> classes;
	private Map<String, List<String>> classToInterfaces = new HashMap<>();
	private Map<String, String> classToSuperclass = new HashMap<>();
	private Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
	private Map<String, List<IMethodData>> classToMethods = new HashMap<>();
	private List<String> classNames;
	
	public PackageModel() {
		this.classes = new ArrayList<>();
		this.classNames = StringParser.getClassNames(this.classes);
	}
	
	private void scanForPatterns(IClassData d) throws IOException {
		if (d.hasPattern()) {
			IPatternDetector detector = new SingletonDetector();
			boolean pat = detector.findPattern(d);
			if(pat) {
				d.setHasPattern(true);
				d.setFill("fillcolor = yellow\n");
				d.setPattern("\n\\<\\<singleton\\>\\>\n");
			}
			
			detector = new DecoratorDetector();
			pat = detector.findPattern(d);
			if(pat) {
				d.setHasPattern(true);
				d.setFill("fillcolor = green\n");
				d.setPattern("\n\\<\\<decorator\\>\\>\n");
			}
		}
	}

	@Override
	public List<IClassData> getClasses() {
		return this.classes;
	}

	@Override
	public void addClass(IClassData d) {
		this.classes.add(d);
	}

	@Override
	public void setClasses(List<IClassData> classes) {
		this.classes = classes;
		this.classNames = StringParser.getClassNames(classes);
	}

	@Override
	public void setClassRelations() {
		for(IClassData currentData: this.classes) {
			this.classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
			this.classToInterfaces.put(currentData.getName(), currentData.getImplementedClasses());
			this.classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
			this.classToMethods.put(currentData.getName(), currentData.getMethods());
		}
	}
	
	@Override
	public Map<String, List<String>> getClassToInterfaces() {
		return this.classToInterfaces;
	}

	@Override
	public void setClassToInterfaces(Map<String, List<String>> classToInterfaces) {
		this.classToInterfaces = classToInterfaces;
	}

	@Override
	public Map<String, String> getClassToSuperclass() {
		return this.classToSuperclass;
	}

	@Override
	public void setClassToSuperclass(Map<String, String> classToSuperclass) {
		this.classToSuperclass = classToSuperclass;
	}

	@Override
	public Map<String, List<String>> getClassToAssociatedClasses() {
		return this.classToAssociatedClasses;
	}

	@Override
	public void setClassToAssociatedClasses(
			Map<String, List<String>> classToAssociatedClasses) {
		this.classToAssociatedClasses = classToAssociatedClasses;
	}

	@Override
	public Map<String, List<IMethodData>> getClassToMethods() {
		return this.classToMethods;
	}

	@Override
	public void setClassToMethods(Map<String, List<IMethodData>> classToMethods) {
		this.classToMethods = classToMethods;
	}

	@Override
	public List<String> getClassNames() {
		return this.classNames;
	}

	@Override
	public void setClassNames(List<String> classNames) {
		this.classNames = classNames;
	}
	
	@Override
	public String createArrows() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getInheritanceArrows());
		sb.append(this.getAssociationArrows());
		sb.append(this.getImplementsArrows());
		sb.append(this.getUsedClassesArrows());
		return sb.toString();
	}
	
	@Override
	public String getInheritanceArrows(){
		StringBuilder sb = new StringBuilder();
		sb.append("edge [\n");
		sb.append("arrowhead = \"empty\"\n");
		sb.append("style = \"solid\"\n]\n");
		for(String className: this.classToSuperclass.keySet()){
			if(this.classNames.contains(this.classToSuperclass.get(className))){
				sb.append(className+" -> "+ this.classToSuperclass.get(className)+ "\n");
			}
		}
		return sb.toString();
	}
	
	@Override
	public String getImplementsArrows(){
		StringBuilder sb = new StringBuilder();
		sb.append("edge [\n");
		sb.append("arrowhead = \"empty\"\n");
		sb.append("style = \"dashed\"\n]\n");
		for(String className: this.classToInterfaces.keySet()){
			for(String curInterface : this.classToInterfaces.get(className)){
				if(this.classNames.contains(curInterface)
						&&!this.classToSuperclass.get(className).equals(curInterface))
				{
					sb.append(className+" -> "+ curInterface+ "\n");
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public String getAssociationArrows(){
		StringBuilder sb = new StringBuilder();
		sb.append("edge [\n");
		sb.append("arrowhead = \"vee\"\n");
		sb.append("style = \"solid\"\n]\n");
		for(String className: this.classToAssociatedClasses.keySet()){
			for(String assocClass: this.classToAssociatedClasses.get(className)){
				if(this.classNames.contains(assocClass)
						&&!this.classToInterfaces.get(className).contains(assocClass))
				{
					sb.append(className+" -> "+assocClass+"\n");
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public String getUsedClassesArrows(){
		StringBuilder sb = new StringBuilder();
		sb.append("edge [\n");
		sb.append("arrowhead = \"vee\"\n");
		sb.append("style = \"dashed\"\n]\n");
		List<String> usedClasses = new ArrayList<>();
		for(String className: this.classToMethods.keySet()){
			for(IMethodData method : this.classToMethods.get(className)){
				
				String paramType;
				for(String param: method.getArgs()){
					if(param.contains("\\<")){
						paramType = param.substring(param.indexOf("<")+1, param.lastIndexOf("\\"));
					}
					else{
						paramType = param;
					}
					if(!usedClasses.contains(paramType)&&this.classNames.contains(paramType)
							&&!paramType.equals(className)
							&&!this.classToAssociatedClasses.get(className).contains(paramType))
					{
						usedClasses.add(paramType);
						sb.append(className+" -> "+paramType+"\n");
					}
				}
				
				boolean returnIsConcrete = true;
				for(String usedClass: method.getUsedClasses()){
					if(usedClass.contains("\\<")){
						usedClass = usedClass.substring(usedClass.indexOf("<")+1, usedClass.lastIndexOf("\\"));
					}
					if((this.classToInterfaces.get(usedClass)!=null
						&&this.classToInterfaces.get(usedClass).contains(method.getType()))
						||(this.classToSuperclass.get(usedClass)!=null
						&&this.classToSuperclass.get(usedClass).equals(method.getType())))
					{
						returnIsConcrete = false;
					}
					if(!usedClasses.contains(usedClass)&&this.classNames.contains(usedClass)
						&&!usedClass.equals(className)
						&&!this.classToAssociatedClasses.get(className).contains(usedClass))
					{
						usedClasses.add(usedClass);
						sb.append(className+" -> "+usedClass+"\n");
					}
				}
				if(returnIsConcrete){
					String returnType;
					if(method.getType().contains("\\<")){
						returnType = method.getType().substring(method.getType().indexOf("<")+1, method.getType().lastIndexOf("\\"));
					}
					else{
						returnType = method.getType();
					}
					if(!usedClasses.contains(returnType)&&this.classNames.contains(returnType)
							&&!returnType.equals(className)
							&&!this.classToAssociatedClasses.get(className).contains(returnType)){
						usedClasses.add(returnType);
						sb.append(className+" -> "+returnType+"\n");
					}
				}
				
			}
		}
		return sb.toString();
	}

	@Override
	public void accept(IVisitor v) throws IOException {
		v.preVisit(this);
		for(IClassData data: this.classes){
			scanForPatterns(data);
			data.accept(v);
		}
		v.visit(this);
		v.postVisit(this);
		
	}

	@Override
	public IClassData getClassDataFromName(String name) {
		for(IClassData data: this.classes){
			if(data.getName().equals(name)){
				return data;
			}
		}
		return null;
	}
}
