package problem.model.data;

import problem.model.visit.ITraverser;


public interface IFieldData extends ITraverser{
	public String getAccessLevel();
	public String getType();
	public void setName(String name);
	public String getName();
}
