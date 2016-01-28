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
		IPatternDetector single = new SingletonDetector();
		StringBuilder sb = new StringBuilder();
		String pattern = single.findPattern(clazz);
		sb.append(clazz.getName());
		sb.append(" [\n");
		if(!pattern.equals(""))
			sb.append("fillcolor = yellow\n");
		sb.append("label = \"{"+clazz.getName() + "\n");
		if(!pattern.equals(""))
			sb.append("\n\\<\\<singleton\\>\\>\n");
		sb.append("|");
		this.out.write(sb.toString().getBytes());
		
	}

}
