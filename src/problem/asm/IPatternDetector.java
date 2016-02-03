package problem.asm;

import java.io.IOException;
import java.util.List;

public interface IPatternDetector {
	public boolean findPattern(IClassData d, IPackageModel m) throws IOException;
	public boolean getPattern();
	public void findPattern(List<IClassData> datas);
}
