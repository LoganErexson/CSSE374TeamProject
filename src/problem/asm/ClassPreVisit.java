package problem.asm;


public class ClassPreVisit extends AbstractVisitMethod{

	public ClassPreVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
		IClassData clazz = (ClassData) t;
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getName());
		sb.append(" [\n");
		if(clazz.hasPattern())
			sb.append(clazz.getFill());
		sb.append("label = \"{"+clazz.getName() + "\n");
		if(clazz.hasPattern())
			sb.append(clazz.getPattern());
		sb.append("|");
		this.buffer.append(sb.toString());
		
	}


}
