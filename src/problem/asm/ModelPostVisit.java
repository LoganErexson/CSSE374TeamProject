package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class ModelPostVisit implements IVisitMethod{

	private OutputStream out;
	public ModelPostVisit(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void execute(ITraverser t) throws IOException {
		this.out.write("]\n".getBytes());
	}

}
