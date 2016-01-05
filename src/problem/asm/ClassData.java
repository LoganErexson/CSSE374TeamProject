package problem.asm;

import java.util.ArrayList;
import java.util.List;

public class ClassData {
	private String name;
	private String superClass;
	private String[] interfaces;
	private ArrayList<FieldData> fields = new ArrayList<>();
	private ArrayList<MethodData> methods = new ArrayList<>();
	
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
	}
	public ArrayList<FieldData> getMethods() {
		return this.fields;
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
	
	public String getInterfacesString(){
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
	public String getSuperClassString(List<ClassData> classes) {
		StringBuilder sb = new StringBuilder();
		if(!this.getSuperClass().equals("Object")){
			boolean inClassFolder = false;
			for(ClassData curData: classes){
				if(curData.getName().equals(this.getSuperClass()))
				{
					inClassFolder = true;
					break;
				}
			}
			if(inClassFolder){
				sb.append("edge [ \n");
				sb.append("arrowhead = \"empty\"\n]\n");
				sb.append(this.getName()+" -> "+ this.getSuperClass()+ "\n");
			}
		}
		return sb.toString();
	}
}
