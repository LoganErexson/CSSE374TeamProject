package problem.asm;

import java.util.ArrayList;
import java.util.Collection;

public class PackageModel implements IPackageModel {
	
	private Collection<IClassData> classes;
	
	public PackageModel() {
		classes = new ArrayList<IClassData>();
	}

	@Override
	public Collection<IClassData> getClasses() {
		return null;
	}

	@Override
	public void addClass(IClassData d) {
		classes.add(d);
	}

}
