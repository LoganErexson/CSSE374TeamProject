package problem.model.visit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import problem.model.data.ClassData;
import problem.model.data.FieldData;
import problem.model.data.MethodData;
import problem.model.data.PackageModel;
import problem.model.visit.method.ClassPostVisit;
import problem.model.visit.method.ClassPreVisit;
import problem.model.visit.method.ClassVisit;
import problem.model.visit.method.FieldVisit;
import problem.model.visit.method.MethodVisit;
import problem.model.visit.method.ModelPostVisit;
import problem.model.visit.method.ModelPreVisit;
import problem.model.visit.method.ModelVisit;

public class UMLVisitor extends AbstractVisitor {
	
	public UMLVisitor() {
		super();
		this.keyToVisitMethodMap = new HashMap<>();
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.Visit, FieldData.class), new FieldVisit(this.buffer));
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.Visit, MethodData.class), new MethodVisit(this.buffer));
		
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.PreVisit, ClassData.class), new ClassPreVisit(this.buffer));
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.Visit, ClassData.class), new ClassVisit(this.buffer));
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.PostVisit, ClassData.class), new ClassPostVisit(this.buffer));
		
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.PreVisit, PackageModel.class), new ModelPreVisit(this.buffer));
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.Visit, PackageModel.class), new ModelVisit(this.buffer));
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.PostVisit, PackageModel.class), new ModelPostVisit(this.buffer));
	}

	@Override
	public void printToOutput(OutputStream out) throws IOException {
		out.write(this.buffer.toString().getBytes());
		
	}

}