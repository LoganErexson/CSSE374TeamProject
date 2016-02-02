package problem.asm;


public class ModelVisit extends AbstractVisitMethod{

	public ModelVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		IPackageModel model = (PackageModel) t;
		model.setClassRelations();
		this.buffer.append(model.createArrows());
	}

}
