package problem.asm;

public interface IMethodCallData extends IData{
	
	public void setCallingClass(String callingClass);
	public String getCallingClass();
	public void setMethod(IMethodData method);
	public IMethodData getMethod();
	public void setMethodClass(String methodClass);
	public String getMethodClass();
	
}
