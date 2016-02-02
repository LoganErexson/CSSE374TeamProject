package problem.asm;

import java.io.IOException;

public class ModelVisit extends AbstractVisitMethod{

	public ModelVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		IPackageModel model = (PackageModel) t;
		model.setClassRelations();
		this.buffer.append(model.createArrows());
	}

}
