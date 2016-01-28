package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class MethodVisit implements IVisitMethod {
	private OutputStream out;
	
	public MethodVisit(OutputStream out){
		this.out = out;
	}
	
	@Override
	public void execute(ITraverser t) throws IOException {
		this.out.write(t.toString().getBytes());
	}

}
