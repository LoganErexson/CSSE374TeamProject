package problem.asm;

import java.io.OutputStream;

public class ClassPostVisit implements IVisitMethod{
	private OutputStream out;
	
	public ClassPostVisit(OutputStream out){
		this.out = out;
	}
	
	@Override
	public void execute(ITraverser t) {
		// TODO Auto-generated method stub.
		
	}

}
