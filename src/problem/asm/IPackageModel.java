package problem.asm;

import java.util.Collection;

public interface IPackageModel {
	public Collection<IClassData> getClasses();
	public void addClass(IClassData d);
}
