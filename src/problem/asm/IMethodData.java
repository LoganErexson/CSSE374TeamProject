package problem.asm;

import java.util.List;

import org.objectweb.asm.Type;

public interface IMethodData extends IData{
	public String getType();
	public void setType(String type);
	public String getAccess();
	public void setAccess(String access);
	public List<String> getArgs();
	public void setArgs(List<String> args);
}
