package problem.asm;


public class FieldVisit extends AbstractVisitMethod {

	public FieldVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append(t.toString());
	}

}
