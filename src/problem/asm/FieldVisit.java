package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class FieldVisit implements IVisitMethod {
	private OutputStream out;
	
	public FieldVisit(OutputStream out){
		this.out = out;
	}
	@Override
	public void execute(ITraverser t) throws IOException {
		this.out.write(t.toString().getBytes());
	}

}
