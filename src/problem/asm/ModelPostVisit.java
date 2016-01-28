package problem.asm;

import java.io.OutputStream;

public class ModelPostVisit implements IVisitMethod{

	private OutputStream out;
	public ModelPostVisit(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void execute(ITraverser t) {
		// TODO Auto-generated method stub.
		
	}

}
