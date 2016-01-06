package problem.asm;

import org.objectweb.asm.Type;

public class FieldData {
	private String fieldName;
	private String access;
	private Type type;
	
	public FieldData(String name, String access, Type type) {
		this.fieldName = name;
		this.access = access;
		this.type = type;
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
	public String toString(){
		return this.access+" "+this.fieldName+" : " +this.type+ "\\l";
	}
}
