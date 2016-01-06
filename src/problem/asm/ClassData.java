package problem.asm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;

public class ClassData {
	private String name;
	private String superClass;
	private String[] interfaces;
	private ArrayList<FieldData> fields = new ArrayList<>();
	private ArrayList<MethodData> methods = new ArrayList<>();
	private Set<String> usedClasses = new HashSet<>();
	private Set<String> associatedClasses = new HashSet<>();
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuperClass() {
		return this.superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	public String[] getInterfaces() {
		return this.interfaces;
	}
	public void setInterfaces(String[] interfaces) {
		this.interfaces = interfaces;
	}
	public void addField(FieldData f) {
		this.fields.add(f);
	}
	public ArrayList<FieldData> getFields() {
		return this.fields;
	}
    public void addMethod(MethodData m) {
		this.methods.add(m);
		String returnType;
		if(m.getType().contains("<")&&m.getType().contains(">"))
		{
			returnType = m.getType().substring(m.getType().indexOf('<')+1, m.getType().lastIndexOf('>'));
		}
		else{
			returnType = m.getType();
		}
		if(!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('.')+1))){
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('.')+1));
		} else if (!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('/')+1))) {
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('/')+1));
		}
		
		for(Type parameter : m.getArgs()){
			
			String paramType; 
			
			if(parameter.getClassName().contains("<")&&parameter.getClassName().contains(">"))
			{
				paramType = parameter.getClassName().substring(parameter.getClassName().indexOf('<'),
					parameter.getClassName().lastIndexOf('>'));
			}
			else
			{
				paramType = parameter.getClassName();
			}
			if(!this.usedClasses.contains(paramType))
			{
				this.usedClasses.add(paramType);
			}
		}
	}
    
	public ArrayList<MethodData> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " [\n");
		sb.append("label = \"{"+this.name);
		sb.append("|");
		for(FieldData fd : this.fields) {
			sb.append(fd.toString());
		}
		sb.append("|");
		for(MethodData md : this.methods) {
			sb.append(md.toString());
		}
		sb.append("}\"\n]\n");
		return sb.toString();
	}
	
	public String getInheritsArrows(){
		StringBuilder sb = new StringBuilder();
		if(this.getInterfaces().length!=0){
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
				sb.append("arrowhead = \"empty\"\n]\n");
				sb.append(this.getName()+" -> "+ this.getSuperClass()+ "\n");
			}
		}
		return sb.toString();
	}
	
	public String getUsesArrows(List<String> classNames){
		StringBuilder sb = new StringBuilder();
		if(this.usedClasses.size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n");
			sb.append("style = \"dashed\"\n]\n");
			for(String curClass : this.usedClasses){
				if(classNames.contains(curClass))
				{
					sb.append(this.getName()+" -> "+ curClass.substring(curClass.lastIndexOf("/")+1)+"\n");
				}
			}
		}
		return sb.toString();
	}
}
