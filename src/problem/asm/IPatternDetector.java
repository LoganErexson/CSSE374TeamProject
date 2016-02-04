package problem.asm;


public interface IPatternDetector {
	public void findPattern(IClassData d);
	public void findPattern(IClassData d, IPackageModel m);
}
