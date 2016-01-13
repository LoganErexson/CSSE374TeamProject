package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

public class MethodData implements IMethodData{
	private String name;
	private String type;
	private String access;
	private List<String> args;
	private List<String> usedClasses;

	public MethodData(String name, Type type, String level, Type[] args,
			String sig) {
		this.name = name;
		if(sig!=null){
			this.type = StringParser.returnTypeFromSignature(sig);
			setArgs(StringParser.parametersFromSignature(sig));
		}
		else{
			this.type = StringParser.parseClassName(type.getClassName());
			List<String> temp = new ArrayList<>();
			for (Type param : args) {
				temp.add(StringParser.parseClassName(param.getClassName()));
			}
			setArgs(temp);
		}
		this.access = level;
		this.usedClasses = new ArrayList<>();
	}
	
	public void addUsedClass(String clazz) {
		this.usedClasses.add(clazz);
	}

	public List<String> getUsedClasses() {
		return usedClasses;
	}

	public void setUsedClasses(List<String> usedClasses) {
		this.usedClasses = usedClasses;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

	@Override
	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public List<String> getArgs() {
		return this.args;
	}

	@Override
	public void setArgs(List<String> args) {
		this.args = args;
	}
	
	@Override
	public String toString() {
		if (this.name.contains("<")) //Constructor handling
			this.name = this.name.replace("<", "\\<").replace(">", "\\>");
		String result = this.access + " " + this.name + "(";
		for(String arg : this.getArgs()){
			result+= arg +", ";
		}
		if (this.args.size() != 0) {
			result = result.substring(0, result.length() - 2);
		}
		result += ") : " + this.getType() + "\\l";
		
		return result;
	}

}
