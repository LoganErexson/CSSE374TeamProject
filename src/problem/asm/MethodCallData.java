package problem.asm;

public class MethodCallData implements IMethodCallData{
	
	private String callingClass;
	private String methodClass;
	private IMethodData method;
	private int depth;
	
	@Override
	public void setName(String nm) {
		this.setCallingClass(nm);	
	}

	@Override
	public String getName() {
		return this.getCallingClass();
	}

	@Override
	public void setCallingClass(String callingClass) {
		this.callingClass=callingClass;
		
	}

	@Override
	public String getCallingClass() {
		return this.callingClass;
	}

	@Override
	public void setMethod(IMethodData method) {
		this.method = method;
		
	}

	@Override
	public IMethodData getMethod() {
		return this.method;
	}

	@Override
	public void setMethodClass(String methodClass) {
		this.methodClass=methodClass;
	}

	@Override
	public String getMethodClass() {
		return this.methodClass;
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append(this.callingClass+":");
		if(this.method.getType()!="void"){
			result.append(this.method.getType()+"=");
		}
		result.append(this.methodClass+"(");
		for(String param: this.method.getArgs()){
			result.append(param+", ");
		}
		if(this.method.getArgs().size()>0){
			result.deleteCharAt(result.length()-2);
		}
		result.append(")");
		return result.toString();
		
	}

	@Override
	public void setDepth(int depth) {
		this.depth=depth;
		
	}

	@Override
	public int getDepth() {
		return this.depth;
	}
}
