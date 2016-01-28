package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class ModelVisit  implements IVisitMethod{

	private OutputStream out;
	public ModelVisit(OutputStream out) {
		this.out = out;
	}

	@Override
	public void execute(ITraverser t) throws IOException {
		IPackageModel model = (PackageModel) t;
		model.setClassRelations();
		this.out.write(model.createArrows().getBytes());
	}

}
