package problem.asm;

public class AbstractTraverser implements ITraverser {

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

}
