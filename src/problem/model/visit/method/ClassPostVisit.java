package problem.model.visit.method;

import problem.model.visit.ITraverser;



public class ClassPostVisit extends AbstractVisitMethod{

	
	public ClassPostVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append("}\"\n]\n");
	}

}
