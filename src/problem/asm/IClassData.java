package problem.asm;

import java.util.List;

public interface IClassData extends IData{
	public void setSuperClass(String superClass);
	public String getSuperClass();
	
	public List<String> getInterfaces();
	public void setInterfaces(List<String> interfaces);
	
	public void addField(IFieldData f);
	public List<IFieldData> getFields();
	
	public Object getExtendsArrow(List<String> classNames);
	public Object getInheritsArrows();
	public Object getUsesArrows(List<String> classNames);
	public Object getAssociationArrows(List<String> classNames);

	public void addMethod(IMethodData m);
	public List<IMethodData> getMethods();

	
	
}
