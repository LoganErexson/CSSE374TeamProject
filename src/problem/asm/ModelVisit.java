package problem.asm;

import java.io.OutputStream;

public class ModelVisit  implements IVisitMethod{

	private OutputStream out;
	public ModelVisit(OutputStream out) {
		this.out = out;
	}

	@Override
	public void execute(ITraverser t) {
		IPackageModel model = (PackageModel) t;
		
	}

}
