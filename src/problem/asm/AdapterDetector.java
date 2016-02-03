package problem.asm;

import java.util.List;

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

	@Override
	public void findPattern(IClassData d, IPackageModel m){
		List<String> interfaces = m.getClassToInterfaces().get(d.getName());
		List<String> associatedClasses = m.getClassToAssociatedClasses().get(d.getName());
		if(interfaces==null||associatedClasses==null){
			return;
		}
		for(String assoc: associatedClasses){
			IClassData assocData = m.getClassDataFromName(assoc);
			for(String inter: interfaces){
				IClassData interData = m.getClassDataFromName(inter);
				boolean hasPattern = true;
				for(IMethodData method: d.getMethods()){
					if(interData.getMethods().contains(method)){
						if(!method.getUsedClasses().contains(assocData.getName())){
							hasPattern = false;
						}
					}
				}
				if(hasPattern){
					d.setPattern("\\l\\<\\<adapter\\>\\>\\l");
					d.setHasPattern(true);
					d.setFill("fillcolor = red\n");
					assocData.setPattern("\\l\\<\\<adaptee\\>\\>\\l");
					assocData.setHasPattern(true);
					assocData.setFill("fillcolor = red\n");
					interData.setPattern("\\l\\<\\<target\\>\\>\\l");
					interData.setHasPattern(true);
					interData.setFill("fillcolor = red\n");
					return;
				}
			}
		}
	}

	@Override
	public void findPattern(IClassData d) {
		// TODO Auto-generated method stub.
		
	}

}
