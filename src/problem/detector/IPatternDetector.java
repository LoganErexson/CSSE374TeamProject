package problem.detector;

import problem.model.data.IClassData;
import problem.model.data.IPackageModel;


public interface IPatternDetector {
	public void findPattern(IClassData d);
	public void findPattern(IClassData d, IPackageModel m);
}
