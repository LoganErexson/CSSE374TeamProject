package problem.model.visit;

import java.io.IOException;

public interface ITraverser {
	public void accept(IVisitor v) throws IOException;
}
