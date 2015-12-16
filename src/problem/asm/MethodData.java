package problem.asm;

import org.objectweb.asm.Type;

public class MethodData {
	private String name;
	private String type;
	private int access;
	private Type[] args;
	
	public MethodData(String name, String type, int access, Type[] args){
		this.name=name;
		this.type=type;
		this.access = access;
		this.setArgs(args);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAccess() {
		return this.access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public Type[] getArgs() {
		return args;
	}

	public void setArgs(Type[] args) {
		this.args = args;
	}
}
