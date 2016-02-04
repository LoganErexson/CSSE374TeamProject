package problem.asm;

import java.util.List;
import java.util.Map;

public class InterfaceDetector implements IPatternDetector {

	@Override
	public void findPattern(IClassData d) {
		// TODO Auto-generated method stub.

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

	@Override
	public boolean getPattern() {
		// TODO Auto-generated method stub.
		return false;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
		// TODO Auto-generated method stub.

	}

}
