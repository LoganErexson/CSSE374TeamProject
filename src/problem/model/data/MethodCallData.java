package problem.model.data;

import problem.main.StringParser;
import problem.model.visit.IVisitor;


public class MethodCallData implements IMethodCallData{
	
	private String callingClass;
	private String methodClass;
	private IMethodData method;
	private int depth = -1;
	private String methodName;
	
	@Override
	public void setName(String nm) {
		this.methodName = nm;
	}

	@Override
	public String getName() {
		return this.methodName;
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
		result.append(StringParser.parseClassName(this.callingClass)+":");
		if(this.method!=null&&this.method.getType()!="void"){
			result.append(StringParser.cleanAngleBrackets(this.method.getType())+"=");
		}
		result.append(StringParser.parseClassName(this.methodClass));
		result.append("."+this.methodName+"(");
		if(this.method!=null){
			for(String param: this.method.getArgs()){
				result.append(StringParser.cleanAngleBrackets(param)+", ");
			}
			if(this.method.getArgs().size()>0){
				result.delete(result.length()-2, result.length());
			}
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

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
}
