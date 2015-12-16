package problem.asm;

public class FieldData {
	private String fieldName;
	private String access;
	private String type;
	
	public FieldData(String name, String access, String type) {
		this.fieldName = name;
		this.access = access;
		this.type = type;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getAccessLevel() {
		return access;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString(){
		return this.access+" "+this.fieldName+" : " +this.type+ "\\l";
	}
}
