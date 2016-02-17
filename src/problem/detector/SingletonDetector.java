package problem.detector;

import problem.model.data.IClassData;
import problem.model.data.IFieldData;
import problem.model.data.IMethodData;


public class SingletonDetector extends AbstractDetector{

	private boolean requiresGetInstance =false;
	
	public SingletonDetector(boolean requiresGetInstance){
		this.requiresGetInstance=requiresGetInstance;
		this.patternName = "Singleton";
	}
	
	@Override
	public void findPatternInClass(IClassData d){
		boolean privCon = false;
		boolean privField = false;
		boolean returnMethod = false;
		for (IFieldData field : d.getFields()) {
			if (field.getAccessLevel().equals("-") && field.getType().equals(d.getName())) {
				privField = true;
			}
		}
		for (IMethodData dat : d.getMethods()) {
			if(dat.getName().equals("<init>") && dat.getAccess().equals("-") ) {
				privCon = true;
			} else if (dat.getType().equals(d.getName())) {
				if(!this.requiresGetInstance||dat.getName().equals("getInstance"))
				{
					returnMethod = true;	
				}
			}
		}
		if (privCon && privField && returnMethod) {
			d.setHasPattern(true);
			d.setFill("fillcolor = yellow\n");
			d.setPattern("\\n\\<\\<singleton\\>\\>\\n");
			this.classes.add(d);
		}
	}

}
