package problem.asm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;

public class ClassData {
	private String name;
	private String superClass;
	private List<String> interfaces;
	private List<FieldData> fields = new ArrayList<>();
	private List<MethodData> methods = new ArrayList<>();
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
	public List<String> getInterfaces() {
		return this.interfaces;
	}
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
	public void addField(FieldData f) {
		this.fields.add(f);
		String fieldType;
		if (f.getType().getClassName().equals("void") && (f.getType().getSort() == Type.ARRAY||
				(f.getType().getSort() == Type.OBJECT && f.getType().getDimensions() > 0 &&
				f.getType().getElementType().getClassName()!=null))) {
			fieldType = f.getType().getElementType().getClassName();
		} else {
			fieldType = f.getType().getClassName();
		}		
		
		if(!this.associatedClasses.contains(fieldType.substring(fieldType.lastIndexOf('.')+1))){
			this.associatedClasses.add(fieldType.substring(fieldType.lastIndexOf('.')+1));
		} else if (!this.associatedClasses.contains(fieldType.substring(fieldType.lastIndexOf('/')+1))) {
			this.associatedClasses.add(fieldType.substring(fieldType.lastIndexOf('/')+1));
		}
	}
	public List<FieldData> getFields() {
		return this.fields;
	}
    public void addMethod(MethodData m) {
		this.methods.add(m);
		String returnType;
		if (m.getType().getClassName().equals("void") && (m.getType().getSort() == Type.ARRAY||
				(m.getType().getSort() == Type.OBJECT && m.getType().getDimensions() > 0 &&
				m.getType().getElementType().getClassName()!=null))) {
			returnType = m.getType().getElementType().getClassName();
		} else {
			returnType = m.getType().getClassName();
		}		
		
		if(!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('.')+1))){
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('.')+1));
		} else if (!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('/')+1))) {
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('/')+1));
		}
		
		for(Type parameter : m.getArgs()){
			String paramType; 
			if(parameter.getClassName() != "void" && (parameter.getSort() == Type.ARRAY ||
					(parameter.getSort() == Type.OBJECT && parameter.getDimensions() > 0 &&
					parameter.getElementType().getClassName() != null)))
			{
				paramType = parameter.getElementType().getClassName();
			}
			else
			{
				paramType = parameter.getClassName();
			}
			if(!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('.')+1))&&
					!this.name.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
					!this.superClass.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
					!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('.')+1)))
			{
				this.usedClasses.add(paramType.substring(paramType.lastIndexOf('.')+1));
			} else if (!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('/')+1))&&
					!this.name.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
					!this.superClass.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
					!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('/')+1))) 
			{
				this.usedClasses.add(paramType.substring(paramType.lastIndexOf('/')+1));
			}
		}
	}
    
	public List<MethodData> getMethods() {
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
	
	public String getAssociationArrows(List<String> classNames){
		StringBuilder sb = new StringBuilder();
		if(this.associatedClasses.size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n]\n");
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
