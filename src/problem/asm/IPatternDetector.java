package problem.asm;

import java.util.List;

public interface IPatternDetector {
	public void findPattern(IClassData d);
	public void findPattern(IClassData d, IPackageModel m);
	public boolean getPattern();
	public void findPattern(List<IClassData> datas);
}
