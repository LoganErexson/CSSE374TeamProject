package problem.model.visit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import problem.main.StringParser;
import problem.model.data.MethodCallData;
import problem.model.visit.method.MethodCallVisit;

public class SDEditVisitor extends AbstractVisitor {


	private Set<String> classSet;
	private String firstClass;
	
	public SDEditVisitor(String firstClass) {
		super();
		this.classSet = new HashSet<>();
		this.firstClass=StringParser.parseClassName(firstClass);
		this.classSet.add(this.firstClass);
		this.keyToVisitMethodMap = new HashMap<>();
		this.keyToVisitMethodMap.put(new LookupKey(VisitType.Visit, MethodCallData.class), new MethodCallVisit(this.buffer, this.classSet));
	}

	@Override
	public void printToOutput(OutputStream out) throws IOException {
		out.write((this.firstClass+":"+this.firstClass+"[a]\n").getBytes());
		for(String className: this.classSet){
			if(!className.equals(this.firstClass)){
				out.write((className+":"+className+"\n").getBytes());
			}
		}
		out.write("\n".getBytes());
		out.write(this.buffer.toString().getBytes());
	}

}
