package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class ClassVisit  implements IVisitMethod{
	private OutputStream out;
	
	public ClassVisit(OutputStream out){
		this.out = out;
	}
	
	@Override
	public void execute(ITraverser t) throws IOException {
		this.out.write("|".getBytes());
		
	}

}