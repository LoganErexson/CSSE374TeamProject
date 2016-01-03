package problem.asm;

import java.util.ArrayList;

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
}
