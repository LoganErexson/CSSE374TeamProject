package problem.asm;

public class ClassData {
	private String name;
	private String superClass;
	private String[] interfaces;
	
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

}
