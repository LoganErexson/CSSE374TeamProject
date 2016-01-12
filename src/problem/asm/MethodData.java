package problem.asm;

import org.objectweb.asm.Type;

public class MethodData implements IData{
	private String name;
	private Type type;
	private String access;
	private Type[] args;
	private String signature;

	public MethodData(String name, Type type, String level, Type[] args,
			String sig) {
		this.signature = sig;
		this.name = name;
		this.type = type;
		this.access = level;
		this.setArgs(args);
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public Type[] getArgs() {
		return this.args;
	}

	public void setArgs(Type[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		if (this.name.contains("<"))
			this.name = this.name.substring(1, this.name.length() - 1);
		String result = this.access + " " + this.name + "(";
		if (this.signature != null) {
			String[] returnArgs = this.signature.substring(
					this.signature.indexOf("(")+1, this.signature.indexOf(")")).split(";");
			if(returnArgs[0]!=""){
				String element;
				for(String arg : returnArgs){
					if(arg.contains(">")){
						continue;
					}
					else if(arg.contains("<")){
						element = arg.substring(arg.lastIndexOf('/')+1);
						arg = arg.substring(0, arg.indexOf('<'));
						result+= arg.substring(arg.lastIndexOf('/')+1)+" "+element+" , ";
					}
					else{
						result+= arg.substring(arg.lastIndexOf('/')+1) +", ";
					}
				}
			}
		} else {
			for (Type arg : this.args) {
				if (arg.getClassName().contains("."))
					result += arg.getClassName().substring(
							arg.getClassName().lastIndexOf(".") + 1)
							+ ", ";
				else if (arg.getClassName().contains("/"))
					result += arg.getClassName().substring(
							arg.getClassName().lastIndexOf("/") + 1)
							+ ", ";
				else
					result += arg.getClassName() + ", ";
			}

		}
		if (this.args.length != 0) {
			result = result.substring(0, result.length() - 2);
		}

		if (this.getSignature() != null) {
			String returnSig = this.signature.substring(
					this.signature.lastIndexOf(')') + 1);
			if(returnSig.equals("V"))
			{
				result+=") : void\\l";
			}
			else{
				if(returnSig.contains("<")){
					String element = returnSig.substring(returnSig.lastIndexOf('<')+1, 
							returnSig.lastIndexOf('>'));
					returnSig = returnSig.substring(0, returnSig.indexOf('<'));
					result+= ") : "+ returnSig.substring(returnSig.lastIndexOf('/')+1)+" "+
							element.substring(element.lastIndexOf("/")+1, element.lastIndexOf(';'))
							+"\\l";
				}
				else{
					result+= ") : "+ returnSig.substring(returnSig.lastIndexOf('/')+1, returnSig.lastIndexOf(';')) +"\\l";
				}
			}
		} 
		else {
			if (this.type.getClassName().contains("."))
				result += ") : " +this.type.getClassName().substring(
						this.type.getClassName().lastIndexOf(".") + 1)
						+ "\\l";
			else if (this.type.getClassName().contains("/"))
				result += ") : " +this.type.getClassName().substring(
						this.type.getClassName().lastIndexOf("/") + 1)
						+ "\\l";
			else
				result += ") : " +this.type.getClassName() + "\\l";
		}

		return result;
	}
}
