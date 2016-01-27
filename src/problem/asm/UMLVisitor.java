package problem.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UMLVisitor implements IVisitor {
	private Map<LookupKey, IVisitMethod> keyToVisitMethodMap;
	private OutputStream out;
	
	public UMLVisitor(OutputStream out) {
		this.keyToVisitMethodMap = new HashMap<>();
		this.out = out;
	}
	@Override
	public void preVisit(ITraverser t) {
		this.doVisit(VisitType.PreVisit, t);
	}

	@Override
	public void visit(ITraverser t) {
		this.doVisit(VisitType.Visit, t);
	}

	@Override
	public void postVisit(ITraverser t) {
		this.doVisit(VisitType.PostVisit, t);
	}
	
	private void doVisit(VisitType vType, ITraverser t) {
		LookupKey key = new LookupKey(vType, t.getClass());
		IVisitMethod m = this.keyToVisitMethodMap.get(key);
		if(m != null)
			try {
				m.execute(t);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
	}
	
	@Override
	public void addVisit(VisitType visitType, Class<?> clazz, IVisitMethod m) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.put(key, m);
	}

	@Override
	public void removeVisit(VisitType visitType, Class<?> clazz) {
		LookupKey key = new LookupKey(visitType, clazz);
		this.keyToVisitMethodMap.remove(key);
	}
	@Override
	public OutputStream getOutputStream() {
		return this.out;
	}

}