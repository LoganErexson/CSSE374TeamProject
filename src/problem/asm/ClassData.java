package problem.asm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassData implements IClassData{
	private String name;
	private String superClass;
	private List<String> interfaces;
	private List<IData> fields = new ArrayList<>();
	private List<IData> methods = new ArrayList<>();
	private Set<String> usedClasses = new HashSet<>();
	private Set<String> associatedClasses = new HashSet<>();
	
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getSuperClass() {
		return this.superClass;
	}
	@Override
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}
	@Override
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
	@Override
	public void addField(FieldData f) {
		this.fields.add(f);
		String fieldType = f.getType();
		if(fieldType.contains("<")){
			fieldType = fieldType.substring(fieldType.indexOf('<'), fieldType.indexOf('>'));
		}		
		
		if(!this.associatedClasses.contains(fieldType)&&this.name!=fieldType){
			this.associatedClasses.add(fieldType);
		}
	}
	@Override
	public List<IData> getFields() {
		return this.fields;
	}
    @Override
	public void addMethod(MethodData m) {
		this.methods.add(m);
		
		String returnType = m.getType();		
		
		if(!this.usedClasses.contains(returnType)&&returnType!=this.name)
		{
			this.usedClasses.add(returnType);
		}
		for(String parameter : m.getArgs()){ 
				
			if(!this.usedClasses.contains(parameter)&&parameter!=this.name&&!this.associatedClasses.contains(parameter))
			{
				this.usedClasses.add(parameter);
			}
		}
		
	}
    
    @Override
	public List<IData> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " [\n");
		sb.append("label = \"{"+this.name);
		sb.append("|");
		for(IData fd : this.fields) {
			sb.append(fd.toString());
		}
		sb.append("|");
		for(IData md : this.methods) {
			sb.append(md.toString());
		}
		sb.append("}\"\n]\n");
		return sb.toString();
	}
	
	public String getInheritsArrows(){
		StringBuilder sb = new StringBuilder();
		if(this.getInterfaces().size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"empty\"\n");
			sb.append("style = \"dashed\"\n]\n");
			for(String curInterface : this.getInterfaces()){
				sb.append(this.getName()+" -> "+ curInterface.substring(curInterface.lastIndexOf("/")+1)+"\n");
			}
		}
		return sb.toString();
	}
	public String getExtendsArrow(List<String> classNames) {
		StringBuilder sb = new StringBuilder();
		if(!this.getSuperClass().equals("Object")){
			if(classNames.contains(this.getSuperClass())){
				sb.append("edge [ \n");
				sb.append("arrowhead = \"empty\"\n");
				sb.append("style = \"solid\"\n]\n");
				sb.append(this.getName()+" -> "+ this.getSuperClass()+ "\n");
			}
		}
		return sb.toString();
	}
	
	public String getUsesArrows(List<String> classNames) {
		StringBuilder sb = new StringBuilder();
		if (this.usedClasses.size()!=0) {
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n");
			sb.append("style = \"dashed\"\n]\n");
			for (String curClass : this.usedClasses) {
				if(classNames.contains(curClass))
				{
					sb.append(this.getName()+" -> "+ curClass.substring(curClass.lastIndexOf("/")+1)+"\n");
				}
			}
		}
		return sb.toString();
	}
	
	public String getAssociationArrows(List<String> classNames){
		StringBuilder sb = new StringBuilder();
		if(this.associatedClasses.size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n");
			sb.append("style = \"solid\"\n]\n");
			for(String curClass : this.associatedClasses){
				if(classNames.contains(curClass))
				{
					sb.append(this.getName()+" -> "+ curClass.substring(curClass.lastIndexOf("/")+1)+"\n");
				}
			}
		}
		return sb.toString();
	}
}
