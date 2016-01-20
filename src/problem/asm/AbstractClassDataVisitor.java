package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;

public abstract class AbstractClassDataVisitor extends ClassVisitor{
	protected String className;
	protected String superClass;
	protected List<String> implementedClasses;
	protected List<IFieldData> fields = new ArrayList<>();
	protected List<IMethodData> methods = new ArrayList<>();
	protected List<String> associatedClasses = new ArrayList<>();
	protected AbstractClassDataVisitor decorated;
	
	public AbstractClassDataVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
		this.decorated = decorated;
		this.updateFromDecorated();
	}

	public String getName() {
		return this.className;
	}

	public void setName(String name) {
		this.className = name;
	}

	public String getSuperClass() {
		return this.superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<String> getImplementedClasses() {
		return this.implementedClasses;
	}

	public void setImplementedClasses(List<String> interfaces) {
		List<String> list = new ArrayList<>();
		for(String inter: interfaces){
			list.add(StringParser.parseClassName(inter));
		}
		this.implementedClasses = list;
	}

	public void addField(IFieldData f) {
		this.fields.add(f);
		String fieldType = f.getType();
		if(fieldType.contains("\\<")){
			fieldType = fieldType.substring(fieldType.indexOf("<"), fieldType.lastIndexOf("\\"));
		}		
		
		if(!this.associatedClasses.contains(fieldType)&&this.className!=fieldType){
			this.associatedClasses.add(fieldType);
		}
	}
	
	public List<IFieldData> getFields() {
		return this.fields;
	}
	
	public void addMethod(IMethodData m) {
		this.methods.add(m);
	}
    
	public List<IMethodData> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.className + " [\n");
		sb.append("label = \"{"+this.className);
		sb.append("|");
		for(IFieldData fd : this.fields) {
			sb.append(fd.toString());
		}
		sb.append("|");
		for(IMethodData md : this.methods) {
			sb.append(md.toString());
		}
		sb.append("}\"\n]\n");
		return sb.toString();
	}

	public List<String> getAssociatedClasses() {
		return this.associatedClasses;
	}
	
	public void updateFromDecorated(){
		if(this.decorated !=null){
			this.decorated.updateFromDecorated();
			this.className = this.decorated.getName();
			this.superClass = this.decorated.getSuperClass();
			this.implementedClasses = this.decorated.getImplementedClasses();
			this.fields = this.decorated.getFields();
			this.methods = this.decorated.getMethods();
			this.associatedClasses = this.decorated.getAssociatedClasses();
		}
	}

}
