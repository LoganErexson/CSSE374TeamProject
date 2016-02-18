package problem.detector;

import java.util.ArrayList;
import java.util.List;

import problem.model.data.IClassData;
import problem.model.data.IPackageModel;

public abstract class AbstractDetector implements IPatternDetector {

	protected IPackageModel m;
	protected String patternName;
	protected List<IClassData> classes = new ArrayList<>();
	
	@Override
	public void findPattern(IPackageModel model){
		this.m = model;
		for(IClassData d : this.m.getClasses()){
			findPatternInClass(d);
		}
	}
	@Override
	public abstract void findPatternInClass(IClassData d);
	
	@Override
	public String getPatternName() {
		return this.patternName;
	}
	@Override
	public List<IClassData> getClasses() {
		return this.classes;
	}

}
