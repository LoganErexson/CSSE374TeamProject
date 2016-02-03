package problem.asm;

import java.util.List;

public class AdapterDecorator implements IPatternDetector {

	private boolean pattern = false;
	
	@Override
	public boolean findPattern(IClassData d) {
		// TODO Auto-generated method stub.
		return false;
	}

	@Override
	public boolean getPattern() {
		return this.pattern;
	}

	@Override
	public void findPattern(List<IClassData> datas) {
		// TODO Auto-generated method stub.

	}
	
	public void findPattern(IPackageModel model) {
		for(IClassData clazz: model.getClasses()){
			
			if(model.getClassToInterfaces().containsKey(clazz.getName())&&
					model.getClassToAssociatedClasses().containsKey(clazz.getName()))
			{
				
			}
		}

	}

}
