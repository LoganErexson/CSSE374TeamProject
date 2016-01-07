package problem.asm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;

public class ClassData {
	private String name;
	private String superClass;
	private List<String> interfaces;
	private List<FieldData> fields = new ArrayList<>();
	private List<MethodData> methods = new ArrayList<>();
	private Set<String> usedClasses = new HashSet<>();
	private Set<String> associatedClasses = new HashSet<>();
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuperClass() {
		return this.superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	public List<String> getInterfaces() {
		return this.interfaces;
	}
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
	public void addField(FieldData f) {
		this.fields.add(f);
		String signature;
		if(f.getSignature()!=null){
			String returnSig = f.getSignature().substring(f.getSignature().lastIndexOf(')') + 1);
			if(returnSig.contains("<")){
				signature = returnSig.substring(returnSig.lastIndexOf('<')+1, 
						returnSig.indexOf(';'));
			}
			else{
				signature = f.getType().getClassName();
			}
		}
		else{
			signature = f.getType().getClassName();
		}		
		
		if(signature.contains(".")&&!this.associatedClasses.contains(
				signature.substring(signature.lastIndexOf('.')+1))&&
				!this.name.equals(signature.substring(signature.lastIndexOf('.')+1))&&
				!this.superClass.equals(signature.substring(signature.lastIndexOf('.')+1))&&
				!this.interfaces.contains(signature.substring(signature.lastIndexOf('.')+1)))
		{
			this.associatedClasses.add(signature.substring(signature.lastIndexOf('.')+1));
		} 
		else if (signature.contains("/")&&!this.associatedClasses.contains(signature.substring(signature.lastIndexOf('/')+1))&&
				!this.name.equals(signature.substring(signature.lastIndexOf('/')+1))&&
				!this.superClass.equals(signature.substring(signature.lastIndexOf('/')+1))&&
				!this.interfaces.contains(signature.substring(signature.lastIndexOf('/')+1))) 
		{
			this.associatedClasses.add(signature.substring(signature.lastIndexOf('/')+1));
		}
		else if(!this.associatedClasses.contains(signature)){
			this.associatedClasses.add(signature);
		}
	}
	public List<FieldData> getFields() {
		return this.fields;
	}
    public void addMethod(MethodData m) {
		this.methods.add(m);
		
		String returnType;
		if(m.getSignature()!=null){
			String returnSig = m.getSignature().substring(m.getSignature().lastIndexOf(')') + 1);
			if(returnSig.contains("<")){
				returnType = returnSig.substring(returnSig.lastIndexOf('<')+1, 
						returnSig.indexOf(';'));
			}
			else{
				returnType = m.getType().getClassName();
			}
		}
		else{
			returnType = m.getType().getClassName();
		}		
		
		if(returnType.contains(".")&&!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('.')+1))&&
				!this.name.equals(returnType.substring(returnType.lastIndexOf('.')+1))&&
				!this.superClass.equals(returnType.substring(returnType.lastIndexOf('.')+1))&&
				!this.interfaces.contains(returnType.substring(returnType.lastIndexOf('.')+1)))
		{
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('.')+1));
		} 
		else if (returnType.contains("/")&&!this.usedClasses.contains(returnType.substring(returnType.lastIndexOf('/')+1))&&
				!this.name.equals(returnType.substring(returnType.lastIndexOf('/')+1))&&
				!this.superClass.equals(returnType.substring(returnType.lastIndexOf('/')+1))&&
				!this.interfaces.contains(returnType.substring(returnType.lastIndexOf('/')+1))) 
		{
			this.usedClasses.add(returnType.substring(returnType.lastIndexOf('/')+1));
		}
		else if(this.usedClasses.contains(returnType))
		{
			this.usedClasses.add(returnType);
		}
		String paramType;
		if(m.getSignature()!=null){
			String[] args = m.getSignature().substring(m.getSignature().indexOf("("), 
					m.getSignature().indexOf(")")).split(";");
			if(!args[0].equals("(")){
				for(String arg: args)
				{
					if(!arg.contains(">")){		
						paramType = arg.substring(arg.lastIndexOf('/')+1);
						if(paramType.contains(".")&&!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('.')+1))&&
								!this.name.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
								!this.superClass.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
								!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('.')+1)))
						{
							this.usedClasses.add(paramType.substring(paramType.lastIndexOf('.')+1));
						} 
						else if (paramType.contains("/")&&!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('/')+1))&&
								!this.name.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
								!this.superClass.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
								!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('/')+1))) 
						{
							this.usedClasses.add(paramType.substring(paramType.lastIndexOf('/')+1));
						}
						else if(!this.usedClasses.contains(paramType))
						{
							this.usedClasses.add(paramType);
						}
					}
				}
			}
			
		}
		else{
			for(Type parameter : m.getArgs()){ 
				
				paramType = parameter.getClassName();
				if(paramType.contains("/")&&!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('.')+1))&&
						!this.name.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
						!this.superClass.equals(paramType.substring(paramType.lastIndexOf('.')+1))&&
						!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('.')+1)))
				{
					this.usedClasses.add(paramType.substring(paramType.lastIndexOf('.')+1));
				} 
				else if (paramType.contains("/")&&!this.usedClasses.contains(paramType.substring(paramType.lastIndexOf('/')+1))&&
						!this.name.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
						!this.superClass.equals(paramType.substring(paramType.lastIndexOf('/')+1))&&
						!this.interfaces.contains(paramType.substring(paramType.lastIndexOf('/')+1))) 
				{
					this.usedClasses.add(paramType.substring(paramType.lastIndexOf('/')+1));
				}
				else if(this.usedClasses.contains(paramType))
				{
					this.usedClasses.add(paramType);
				}
			}
		}
		
	}
    
	public List<MethodData> getMethods() {
		return this.methods;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " [\n");
		sb.append("label = \"{"+this.name);
		sb.append("|");
		for(FieldData fd : this.fields) {
			sb.append(fd.toString());
		}
		sb.append("|");
		for(MethodData md : this.methods) {
			sb.append(md.toString());
		}
		sb.append("}\"\n]\n");
		return sb.toString();
	}
	
	public String getInheritsArrows(){
		StringBuilder sb = new StringBuilder();
		if(this.getInterfaces().size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"empty\"\n");
			sb.append("style = \"dashed\"\n]\n");
			for(String curInterface : this.getInterfaces()){
				sb.append(this.getName()+" -> "+ curInterface.substring(curInterface.lastIndexOf("/")+1)+"\n");
			}
		}
		return sb.toString();
	}
	public String getExtendsArrow(List<String> classNames) {
		StringBuilder sb = new StringBuilder();
		if(!this.getSuperClass().equals("Object")){
			if(classNames.contains(this.getSuperClass())){
				sb.append("edge [ \n");
				sb.append("arrowhead = \"empty\"\n");
				sb.append("style = \"solid\"\n]\n");
				sb.append(this.getName()+" -> "+ this.getSuperClass()+ "\n");
			}
		}
		return sb.toString();
	}
	
	public String getUsesArrows(List<String> classNames){
		StringBuilder sb = new StringBuilder();
		if(this.usedClasses.size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n");
			sb.append("style = \"dashed\"\n]\n");
			for(String curClass : this.usedClasses){
				if(classNames.contains(curClass))
				{
					sb.append(this.getName()+" -> "+ curClass.substring(curClass.lastIndexOf("/")+1)+"\n");
				}
			}
		}
		return sb.toString();
	}
	
	public String getAssociationArrows(List<String> classNames){
		StringBuilder sb = new StringBuilder();
		if(this.associatedClasses.size()!=0){
			sb.append("edge [ \n");
			sb.append("arrowhead = \"vee\"\n");
			sb.append("style = \"solid\"\n]\n");
			for(String curClass : this.associatedClasses){
				if(classNames.contains(curClass))
				{
					sb.append(this.getName()+" -> "+ curClass.substring(curClass.lastIndexOf("/")+1)+"\n");
				}
			}
		}
		return sb.toString();
	}
}
