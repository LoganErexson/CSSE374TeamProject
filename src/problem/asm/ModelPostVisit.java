package problem.asm;

import java.io.IOException;

public class ModelPostVisit extends AbstractVisitMethod{
	
	public ModelPostVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		this.buffer.append("}\n");
	}

}
