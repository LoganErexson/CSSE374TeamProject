package problem.model.visit.method;

import java.util.Set;

import problem.main.StringParser;
import problem.model.data.IMethodCallData;
import problem.model.data.MethodCallData;
import problem.model.visit.ITraverser;

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
		IMethodCallData meth = (MethodCallData) t;
		String name = StringParser.parseClassName(meth.getMethodClass());
		if(!this.classSet.contains(name)){
			this.classSet.add(name);
		}
		this.buffer.append(meth.toString() + "\n");
	}

}
