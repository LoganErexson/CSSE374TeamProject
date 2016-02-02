package problem.asm;

import java.util.Set;

public class MethodCallVisit extends AbstractVisitMethod{

	private Set<String> classSet;
	public MethodCallVisit(StringBuffer buffer) {
		super(buffer);
	}
	public MethodCallVisit(StringBuffer buffer, Set<String> classSet){
		this(buffer);
		this.classSet = classSet;
	}

	@Override
	public void execute(ITraverser t){
		this.buffer.append(t.toString());
	}

}
