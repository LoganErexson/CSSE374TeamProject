package problem.asm;

import java.util.List;

public interface IClassData extends IData{
	public void setSuperClass(String superClass);
	public String getSuperClass();
	
	public List<String> getInterfaces();
	public void setInterfaces(List<String> interfaces);
	
	public void addField(FieldData f);
	public List<IData> getFields();
	
	public void addMethod(MethodData m);
	public List<IData> getMethods();
	public Object getExtendsArrow(List<String> classNames);
	public Object getInheritsArrows();
	public Object getUsesArrows(List<String> classNames);
	public Object getAssociationArrows(List<String> classNames);
	
	
}
