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
		return this.signature;
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
		String result = this.access+" "+this.fieldName+" : ";
		String typeString = this.type.getClassName().substring(this.type.getClassName().lastIndexOf(".")+1);
		if (this.getSignature() != null) {
			String f = this.getSignature();
			if(f.contains("<")){
				f = f.substring(f.lastIndexOf('<')+1, 
						f.lastIndexOf('>'));
				result+= typeString + " " + f.substring(f.lastIndexOf("/")+1, f.lastIndexOf(';')) + "\\l";
			}
//			else{
//				result+= ") : "+ f.substring(f.lastIndexOf('/')+1) +"\\l";
//			}
//				}
			 
			return result;
		} else {
			return result + typeString + "\\l";
		}
	}
}
