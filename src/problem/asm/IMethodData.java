package problem.asm;

import org.objectweb.asm.Type;

public interface IMethodData extends IData{
	public String getType();
	public void setType(String type);
	public String getAccess();
	public void setAccess(String access);
	public Type[] getArgs();
	public void setArgs(Type[] args);
}
