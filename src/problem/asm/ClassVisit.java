package problem.asm;

import java.io.IOException;

public class ClassVisit extends AbstractVisitMethod{

	
	public ClassVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		this.buffer.append("|");
		
	}

}
