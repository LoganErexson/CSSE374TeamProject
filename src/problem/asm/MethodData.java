package problem.asm;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

public class MethodData implements IMethodData{
	private String name;
	private String type;
	private String access;
	private List<String> args;
	private String signature;

	public MethodData(String name, Type type, String level, Type[] args,
			String sig) {
		this.signature = sig;
		this.name = name;
		if(sig!=null){
			this.type = StringParser.returnTypeFromSignature(sig);
			setArgs(StringParser.parametersFromSignature(sig));
		}
		else{
			this.type = StringParser.parseClassName(type.getClassName());
			List<String> temp = new ArrayList<String>();
			for (Type param : args) {
				temp.add(StringParser.parseClassName(param.getClassName()));
			}
			setArgs(temp);
		}
		//this.type = type.getClassName();
		this.access = level;
		
		//this.setArgs(args);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

	@Override
	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public List<String> getArgs() {
		return this.args;
	}

	@Override
	public void setArgs(List<String> args) {
		this.args = args;
	}
	
	@Override
	public String toString() {
		String result = this.access + " " + this.name + "(";
		for(String arg : this.getArgs()){
			result+= arg +", ";
		}
		return result;
	}

//	@Override
//	public String toString() {
//		if (this.name.contains("<"))
//			this.name = this.name.substring(1, this.name.length() - 1);
//		String result = this.access + " " + this.name + "(";
//		if (this.signature != null) {
//			String[] returnArgs = this.signature.substring(
//					this.signature.indexOf("(")+1, this.signature.indexOf(")")).split(";");
//			if(returnArgs[0]!=""){
//				String element;
//				for(String arg : returnArgs){
//					if(arg.contains(">")){
//						continue;
//					}
//					else if(arg.contains("<")){
//						element = arg.substring(arg.lastIndexOf('/')+1);
//						arg = arg.substring(0, arg.indexOf('<'));
//						result+= arg.substring(arg.lastIndexOf('/')+1)+" "+element+" , ";
//					}
//					else{
//						result+= arg.substring(arg.lastIndexOf('/')+1) +", ";
//					}
//				}
//			}
//		} else {
//			for (Type arg : this.args) {
//				if (arg.getClassName().contains("."))
//					result += arg.getClassName().substring(
//							arg.getClassName().lastIndexOf(".") + 1)
//							+ ", ";
//				else if (arg.getClassName().contains("/"))
//					result += arg.getClassName().substring(
//							arg.getClassName().lastIndexOf("/") + 1)
//							+ ", ";
//				else
//					result += arg.getClassName() + ", ";
//			}
//
//		}
//		if (this.args.length != 0) {
//			result = result.substring(0, result.length() - 2);
//		}
//
//		if (this.getSignature() != null) {
//			String returnSig = this.signature.substring(
//					this.signature.lastIndexOf(')') + 1);
//			if(returnSig.equals("V"))
//			{
//				result+=") : void\\l";
//			}
//			else{
//				if(returnSig.contains("<")){
//					String element = returnSig.substring(returnSig.lastIndexOf('<')+1, 
//							returnSig.lastIndexOf('>'));
//					returnSig = returnSig.substring(0, returnSig.indexOf('<'));
//					result+= ") : "+ returnSig.substring(returnSig.lastIndexOf('/')+1)+" "+
//							element.substring(element.lastIndexOf("/")+1, element.lastIndexOf(';'))
//							+"\\l";
//				}
//				else{
//					result+= ") : "+ returnSig.substring(returnSig.lastIndexOf('/')+1, returnSig.lastIndexOf(';')) +"\\l";
//				}
//			}
//		} 
//		else {
//			if (this.type.getClassName().contains("."))
//				result += ") : " +this.type.getClassName().substring(
//						this.type.getClassName().lastIndexOf(".") + 1)
//						+ "\\l";
//			else if (this.type.getClassName().contains("/"))
//				result += ") : " +this.type.getClassName().substring(
//						this.type.getClassName().lastIndexOf("/") + 1)
//						+ "\\l";
//			else
//				result += ") : " +this.type.getClassName() + "\\l";
//		}
//
//		return result;
//	}
}
