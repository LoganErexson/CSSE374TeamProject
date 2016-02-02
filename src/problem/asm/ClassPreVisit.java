package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class ClassPreVisit  implements IVisitMethod{
	private OutputStream out;
	
	public ClassPreVisit(OutputStream out){
		this.out = out;
	}
	@Override
	public void execute(ITraverser t) throws IOException {
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
		this.out.write(sb.toString().getBytes());
		
	}

}
