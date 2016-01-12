package problem.asm;

import org.objectweb.asm.Type;

public interface IFieldData extends IData{
	public String getAccessLevel();
	public Type getType();
}
