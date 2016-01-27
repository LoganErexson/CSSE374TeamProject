package problem.asm;

import java.util.List;

public interface IPatternDetector {
	public void findPattern(IClassData d);
	public String getPattern();
	public void findPattern(List<IClassData> datas);
}
