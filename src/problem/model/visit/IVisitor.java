package problem.model.visit;

import java.io.IOException;
import java.io.OutputStream;

import problem.model.visit.method.IVisitMethod;


public interface IVisitor {
	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);

	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m);
	public void removeVisit(VisitType visitType, Class<?> clazz);
	
	public void printToOutput(OutputStream out) throws IOException;
}
