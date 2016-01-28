package problem.asm;

import java.util.List;

public interface IPatternDetector {
	public String findPattern(IClassData d);
	public String getPattern();
	public void findPattern(List<IClassData> datas);
}
