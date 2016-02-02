package problem.asm;

import java.util.Map;

public abstract class AbstractVisitor implements IVisitor{

	protected Map<LookupKey, IVisitMethod> keyToVisitMethodMap;
	protected StringBuffer buffer;
	
	public AbstractVisitor(){
		this.buffer = new StringBuffer();
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
			m.execute(t);
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
	
}
