package problem.asm;

public class MethodData {
	private String name;
	private String type;
	private int access;
	
	public MethodData(String name, String type, int access){
		this.name=name;
		this.type=type;
		this.access = access;
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
}
