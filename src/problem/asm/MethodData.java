package problem.asm;

import org.objectweb.asm.Type;

public class MethodData {
	private String name;
	private Type type;
	private String access;
	private Type[] args;

	public MethodData(String name, Type type, String level, Type[] args) {
		this.name = name;
		this.type = type;
		this.access = level;
		this.setArgs(args);
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
		for (Type arg : this.args) {
			if (arg.getClassName() != "void"
					&& (arg.getSort() == Type.OBJECT
							&& arg.getDimensions() > 0 && arg.getElementType()
							.getClassName() != null)) {
				result += arg.getClassName() + arg.getInternalName() + ", ";
			} else {
				result += arg.getClassName() + ", ";
			}
		}
		if (this.args.length != 0) {
			result = result.substring(0, result.length() - 2);
		}

		if (this.type.getClassName() != "void"
				&& (this.type.getSort() == Type.OBJECT
						&& this.type.getDimensions() > 0 && this.type
						.getElementType().getClassName() != null)) {
			result += ") : " + this.type.getClassName() + this.type.getInternalName()  + "\\l";
		} else {
			result += ") : " + this.type.getClassName() + "\\l";
		}

		return result;
	}
}
