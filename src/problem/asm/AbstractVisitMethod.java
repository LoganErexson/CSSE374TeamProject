package problem.asm;

import java.io.IOException;

public abstract class AbstractVisitMethod implements IVisitMethod {
	protected StringBuffer buffer;
	
	public AbstractVisitMethod(StringBuffer buffer){
		this.buffer=buffer;
	}
	@Override
	public abstract void execute(ITraverser t) throws IOException;

}
