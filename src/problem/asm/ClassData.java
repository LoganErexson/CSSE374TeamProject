package problem.asm;

import java.util.ArrayList;

public class ClassData {
	private String name;
	private String superClass;
	private String[] interfaces;
	private ArrayList<FieldData> fields;
	private ArrayList<MethodData> methods;
	
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
		
	}
	public ArrayList<FieldData> getFields() {
		return fields;
	}
    public void addMethod(MethodData m) {
		
	}
	public ArrayList<FieldData> getMethods() {
		return fields;
	}
	
	
}
