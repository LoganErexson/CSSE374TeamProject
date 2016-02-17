package problem.detector;

import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IPackageModel;


public interface IPatternDetector {
	public void findPattern(IPackageModel m);
	public void findPatternInClass(IClassData d);
	public String getPatternName();
	public List<IClassData> getClasses();
}
