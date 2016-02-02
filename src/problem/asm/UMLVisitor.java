package problem.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

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