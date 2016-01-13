package problem.asm;

import java.util.ArrayList;
import java.util.List;

public class ClassData implements IClassData{
	private String name;
	private String superClass;
	private List<String> interfaces;
	private List<IFieldData> fields = new ArrayList<>();
	private List<IMethodData> methods = new ArrayList<>();
	private List<String> associatedClasses = new ArrayList<>();
	
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getSuperClass() {
		return this.superClass;
	}
	@Override
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}
	@Override
	public void setInterfaces(List<String> interfaces) {
		List<String> list = new ArrayList<>();
		for(String inter: interfaces){
			list.add(StringParser.parseClassName(inter));
		}
		this.interfaces = list;
	}
	@Override
	public void addField(IFieldData f) {
		this.fields.add(f);
		String fieldType = f.getType();
		if(fieldType.contains("\\<")){
			fieldType = fieldType.substring(fieldType.indexOf("<"), fieldType.lastIndexOf("\\"));
		}		
		
		if(!this.associatedClasses.contains(fieldType)&&this.name!=fieldType){
			this.associatedClasses.add(fieldType);
		}
	}
	@Override
	public List<IFieldData> getFields() {
		return this.fields;
	}
    @Override
	public void addMethod(IMethodData m) {
		this.methods.add(m);
	}
    
    @Override
	public List<IMethodData> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " [\n");
		sb.append("label = \"{"+this.name);
		sb.append("|");
		for(IData fd : this.fields) {
			sb.append(fd.toString());
		}
		sb.append("|");
		for(IData md : this.methods) {
			sb.append(md.toString());
		}
		sb.append("}\"\n]\n");
		return sb.toString();
	}

	@Override
	public List<String> getAssociatedClasses() {
		return this.associatedClasses;
	}
}
