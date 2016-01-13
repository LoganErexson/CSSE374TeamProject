package problem.asm;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphVisPrinter implements IClassStructurePrinter{

	private Map<String, List<String>> classToInterfaces = new HashMap<>();
	private Map<String, String> classToSuperclass = new HashMap<>();
	private Map<String, List<String>> classToUsedClasses = new HashMap<>();
	private Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
	private Map<String, List<IMethodData>> classToMethods = new HashMap<>();
	private List<String> classNames;
	private List<IClassData> classes;
	
	public GraphVisPrinter(List<IClassData> classes){
		this.classNames = StringParser.getClassNames(classes);
		this.classes = classes;
	}
	
	@Override
	public void printToFile(String file){
		try {
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
			sb.append("digraph G {\n");
			sb.append("fontname = \" Bitstream Vera San\"\n");
			sb.append("fontsize =8\n");
			
			sb.append("node [\n");
			sb.append("fontname = \"Bitstream Vera Sans\"\n");
			sb.append("fontsize =8\n");
			sb.append("shape = \"record\"");
			sb.append("]\n");
			
			sb.append("edge [\n");
			sb.append("fontname = \"Bitstream Vera Sans\"\n");
			sb.append("fontsize =8\n");
			sb.append("]\n");
			
			for(IClassData currentData: this.classes){
				sb.append(currentData.toString());
				this.classToSuperclass.put(currentData.getName(), currentData.getSuperClass());
				this.classToInterfaces.put(currentData.getName(), currentData.getInterfaces());
				this.classToUsedClasses.put(currentData.getName(), currentData.getUsedClasses());
				this.classToAssociatedClasses.put(currentData.getName(), currentData.getAssociatedClasses());
				this.classToMethods.put(currentData.getName(), currentData.getMethods());
			}
			sb.append(this.createArrows());
			sb.append("}\n");
			out.write(sb.toString().getBytes());
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	@Override
	public String createArrows(){
		StringBuilder sb = new StringBuilder();
		sb.append("edge [ \n");
		sb.append("arrowhead = \"empty\"\n");
		sb.append("style = \"solid\"\n]\n");
		for(String className: this.classToSuperclass.keySet()){
			if(this.classNames.contains(this.classToSuperclass.get(className))){
				sb.append(className+" -> "+ this.classToSuperclass.get(className)+ "\n");
			}
		}
		sb.append("edge [ \n");
		sb.append("arrowhead = \"empty\"\n");
		sb.append("style = \"dashed\"\n]\n");
		for(String className: this.classToInterfaces.keySet()){
			for(String curInterface : this.classToInterfaces.get(className)){
				if(this.classNames.contains(curInterface)
						&&!this.classToSuperclass.get(className).equals(curInterface))
				{
					sb.append(className+" -> "+ curInterface+ "\n");
				}
			}
		}
		sb.append("edge [ \n");
		sb.append("arrowhead = \"vee\"\n");
		sb.append("style = \"solid\"\n]\n");
		for(String className: this.classToAssociatedClasses.keySet()){
			for(String assocClass: this.classToAssociatedClasses.get(className)){
				if(this.classNames.contains(assocClass)
						&&!this.classToInterfaces.get(className).contains(assocClass))
				{
					sb.append(className+" -> "+assocClass+"\n");
				}
			}
		}

		sb.append("edge [ \n");
		sb.append("arrowhead = \"vee\"\n");
		sb.append("style = \"dashed\"\n]\n");
		for(String className: this.classToUsedClasses.keySet()){
			for(String usedClass: this.classToUsedClasses.get(className)){
				if(this.classNames.contains(usedClass)
						&&!this.classToAssociatedClasses.get(className).contains(usedClass)
						&&!this.classToInterfaces.get(className).contains(usedClass)
						&&!this.classToSuperclass.get(className).equals(usedClass))
				{
					sb.append(className+" -> "+usedClass+"\n");
				}
			}
		}
		return sb.toString();
	}


}
