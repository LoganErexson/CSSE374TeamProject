package problem.asm;

import java.util.List;

public interface IClassData extends IData{
	public void setSuperClass(String superClass);
	public String getSuperClass();
	
	public List<String> getInterfaces();
	public void setInterfaces(List<String> interfaces);
	
	public List<String> getAssociatedClasses();
	
	public void addField(IFieldData f);
	public List<IFieldData> getFields();
	
	public void addMethod(IMethodData m);
	public List<IMethodData> getMethods();

	
	
}
