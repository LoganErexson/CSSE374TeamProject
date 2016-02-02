package problem.asm;

import java.io.IOException;

public class MethodVisit extends AbstractVisitMethod {
	
	public MethodVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		this.buffer.append(t.toString());
	}

}
