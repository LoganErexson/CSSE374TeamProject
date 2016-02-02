package problem.asm;

import java.io.IOException;

public class FieldVisit extends AbstractVisitMethod {

	public FieldVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		this.buffer.append(t.toString());
	}

}
