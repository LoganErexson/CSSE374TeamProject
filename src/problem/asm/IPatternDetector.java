package problem.asm;

import java.util.List;

public interface IPatternDetector {
	public boolean findPattern(IClassData d);
	public boolean getPattern();
	public void findPattern(List<IClassData> datas);
}
