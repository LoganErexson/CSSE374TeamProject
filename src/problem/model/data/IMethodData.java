package problem.model.data;

import java.util.List;

import problem.model.visit.ITraverser;

public interface IMethodData extends ITraverser{
	public String getType();

	public void setType(String type);

	public String getAccess();

	public void setAccess(String access);

	public List<String> getArgs();

	public void setArgs(List<String> args);

	public void addCreatedClass(String clazz);

	public List<String> getCreatedClasses();

	public void setCreatedClasses(List<String> usedClasses);

	public void addUsedClass(String clazz);

	public List<String> getUsedClasses();

	public void setUsedClasses(List<String> usedClasses);

	public void setName(String name);
	
	public String getName();
}
