package problem.asm;

import java.io.IOException;


@FunctionalInterface
public interface IVisitMethod {
	public void execute(ITraverser t) throws IOException;
}
