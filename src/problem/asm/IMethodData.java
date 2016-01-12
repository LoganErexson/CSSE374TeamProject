package problem.asm;

import org.objectweb.asm.Type;

public interface IMethodData extends IData{
	public Type getType();
	public void setType(Type type);
	public String getAccess();
	public void setAccess(String access);
	public Type[] getArgs();
	public void setArgs(Type[] args);
}
