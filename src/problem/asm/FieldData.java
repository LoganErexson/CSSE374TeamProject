package problem.asm;

import org.objectweb.asm.Type;

public class FieldData {
	private String fieldName;
	private String signature;
	private String access;
	private Type type;
	
	public FieldData(String name, String access, Type type, String sig) {
		this.fieldName = name;
		this.access = access;
		this.type = type;
		this.signature = sig;
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getFieldName() {
		return this.fieldName;
	}
	
	public String getAccessLevel() {
		return this.access;
	}
	
	public Type getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		if (this.getSignature() != null) {
			
			return this.access+" "+this.fieldName+" : " + "" + "\\l";
		} else {
			return this.access+" "+this.fieldName+" : " + this.type + "\\l";
		}
	}
}
