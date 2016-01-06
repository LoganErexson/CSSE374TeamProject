package problem.asm;

import org.objectweb.asm.Type;

public class MethodData {
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
			String returnArgs = this.signature.substring(
					this.signature.indexOf("("), this.signature.indexOf(")"));
			for (Type arg : this.args) {
				result += returnArgs.substring(this.signature.indexOf("/") + 1,
								this.signature.indexOf(";")) + ", ";
				returnArgs = returnArgs.substring(returnArgs.indexOf(";") + 1);
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
					this.signature.lastIndexOf('/') + 1,
					this.signature.lastIndexOf(';'));
			result += ") : " + this.type.getClassName() + returnSig
					+ "\\l";
		} else {
			result += ") : " + this.type.getClassName() + "\\l";
		}

		return result;
	}
}
