package problem.asm;

import java.util.ArrayList;
import java.util.List;

public class StringParser{
	
	public static String parseClassName(String classString){
		if(classString==null)
		{
			return "";
		}
		String cleanClassName = classString;
		if(cleanClassName.contains("/")){
			cleanClassName = cleanClassName.substring(cleanClassName.lastIndexOf('/')+1);
		}
		if(cleanClassName.contains(".")){
			cleanClassName = cleanClassName.substring(cleanClassName.lastIndexOf('.')+1);
		}
		if(cleanClassName.contains(";")){
			cleanClassName = cleanClassName.substring(0, cleanClassName.indexOf(';'));
		}
		return cleanClassName;
	}
	
	public static List<String> parametersFromSignature(String signature){
		List<String> params = new ArrayList<>();
		if(signature==null)
			return params;
		
		String[] unparsedParams = signature.substring(signature.indexOf("(")+1, signature.indexOf(")")).split(";");
		
		String elementType;
		for(String param: unparsedParams){
			if(param.contains(">")){
				continue;
			}
			else if(param.contains("<")){
				elementType = parseClassName(param);
				param = param.substring(0, param.indexOf('<'));
				params.add(parseClassName(param)+"<"+elementType+">");
			}
			else{
				params.add(parseClassName(param));
			}
		}
		return params;
	}
	
	public static String returnTypeFromSignature(String signature){
		if(signature==null)
			return "";
		String returnType = signature.substring(signature.indexOf(')')+1);
		if(returnType.contains("<")){
			String elementType = parseClassName(returnType);
			returnType = returnType.substring(0, returnType.indexOf('<'));
			return parseClassName(returnType)+"<"+elementType+">";
		}
		else if(returnType.equals("V")){
			return "void";
		}
		return returnType;
	}
	
	public static String fieldTypeFromSignature(String signature){
		if(signature.contains("<")){
			String elementType = parseClassName(signature);
			String fieldType = signature.substring(0, signature.indexOf('<'));
			return parseClassName(fieldType)+"<"+elementType+">";
		}
		return signature;
	}
	
	public static List<String> getClassNames(List<ClassData> classes){
		List<String> classNames = new ArrayList<>();
		for(ClassData clazz: classes){
			classNames.add(clazz.getName());
		}
		return classNames;
	}

}
