package problem.asm;


public class MethodVisit extends AbstractVisitMethod {
	
	public MethodVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append(t.toString());
	}

}
