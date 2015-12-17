package problem.asm;

import org.objectweb.asm.Type;

public class MethodData {
	private String name;
	private String type;
	private String access;
	private Type[] args;
	
	public MethodData(String name, String type, String level, Type[] args){
		this.name=name;
		this.type=type;
		this.access = level;
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

	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public Type[] getArgs() {
		return args;
	}

	public void setArgs(Type[] args) {
		this.args = args;
	}
	
	@Override
	public String toString(){
		if(this.name.contains("<"))
			this.name = this.name.substring(1, this.name.length() - 1);
		String result = this.access+" "+this.name+"(";
		for(Type arg: this.args){
			result += arg.getClassName()+ ", ";
		}
		if(args.length!=0){
			result = result.substring(0, result.length()-2);
		}
		result+=") : " + this.type+"\\l";
		return result;
	}
}
