package problem.asm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdapterDetector implements IPatternDetector {

	private boolean pattern = false;

	@Override
	public boolean getPattern() {
		return this.pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
		// TODO Auto-generated method stub.

	}
	
	public void findPattern(IPackageModel model) {
		Map<String, List<String>> interfaceMap = model.getClassToInterfaces();
		Map<String, List<String>> associatedMap = model.getClassToAssociatedClasses();
		for(IClassData clazz: model.getClasses()){
			
			if(interfaceMap.containsKey(clazz.getName())&&
					associatedMap.containsKey(clazz.getName()))
			{
				
			}
		}

	}

	@Override
	public boolean findPattern(IClassData d, IPackageModel m)
			throws IOException {
		// TODO Auto-generated method stub.
		return false;
	}

}
