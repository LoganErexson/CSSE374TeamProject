package problem.model.visit.method;

import problem.model.visit.ITraverser;



public abstract class AbstractVisitMethod implements IVisitMethod {
	protected StringBuffer buffer;
	
	public AbstractVisitMethod(StringBuffer buffer){
		this.buffer=buffer;
	}
	@Override
	public abstract void execute(ITraverser t);

}
