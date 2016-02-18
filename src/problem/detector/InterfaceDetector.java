package problem.detector;

import java.util.List;
import java.util.Map;

import problem.model.data.IClassData;
import problem.model.data.IPackageModel;

public class InterfaceDetector extends AbstractDetector {
	
	public InterfaceDetector(){
		this.patternName = "Interface";
	}
	
	@Override
	public void findPattern(IPackageModel model){
		this.m = model;
		for(IClassData d : this.m.getClasses()){
			findPatternInClass(d);
		}
	}
	
	@Override
	public void findPatternInClass(IClassData d){
		Map<String, List<String>> interfaceMap = this.m.getClassToInterfaces();
		
		for(String className: this.m.getClassNames()){
			if(interfaceMap.get(className).contains(d.getName())){
				d.setIsInterface(true);
				return;
			}
		}
	}

}
