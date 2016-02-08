package problem.detector;

import java.util.List;
import java.util.Map;

import problem.model.data.IClassData;
import problem.model.data.IPackageModel;

public class InterfaceDetector implements IPatternDetector {

	@Override
	public void findPattern(IClassData d) {
	}

	@Override
	public void findPattern(IClassData d, IPackageModel m) {
		Map<String, List<String>> interfaceMap = m.getClassToInterfaces();
		
		for(String className: m.getClassNames()){
			if(interfaceMap.get(className).contains(d.getName())){
				d.setIsInterface(true);
				return;
			}
		}
		

	}

}
