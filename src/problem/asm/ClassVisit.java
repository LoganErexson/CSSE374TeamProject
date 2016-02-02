package problem.asm;


public class ClassVisit extends AbstractVisitMethod{

	
	public ClassVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append("|");
		
	}

}
