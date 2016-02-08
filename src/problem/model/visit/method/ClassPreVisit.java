package problem.model.visit.method;

import problem.model.data.ClassData;
import problem.model.data.IClassData;
import problem.model.visit.ITraverser;


public class ClassPreVisit extends AbstractVisitMethod{

	public ClassPreVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		IClassData clazz = (ClassData) t;
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getName());
		sb.append(" [\n");
		if(clazz.hasPattern())
			sb.append(clazz.getFill());
		sb.append("label = \"{");
		if(clazz.isInterface()){
			sb.append("\\<\\<interface\\>\\>\\n");
		}
		sb.append(clazz.getName() + "\n");
		if(clazz.hasPattern())
			sb.append(clazz.getPattern());
		sb.append("|");
		this.buffer.append(sb.toString());
		
	}


}
