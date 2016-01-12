package problem.asm;

import java.util.List;

public interface IParser {
	
	public String parseClassName(String classString);
	
	public List<String> parametersFromSignature(String signature);
	
	public String returnTypeFromSignature(String signature);
	
	public String fieldTypeFromSignature(String signature);
	
	public List<String> getClassNames(List<ClassData> classes);
	

}
