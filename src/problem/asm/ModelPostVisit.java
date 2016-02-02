package problem.asm;


public class ModelPostVisit extends AbstractVisitMethod{
	
	public ModelPostVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append("}\n");
	}

}
