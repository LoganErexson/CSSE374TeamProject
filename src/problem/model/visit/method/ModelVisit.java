package problem.model.visit.method;

import problem.model.data.IPackageModel;
import problem.model.data.PackageModel;
import problem.model.visit.ITraverser;


public class ModelVisit extends AbstractVisitMethod{

	public ModelVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		IPackageModel model = (PackageModel) t;
		this.buffer.append(model.createArrows());
	}

}
