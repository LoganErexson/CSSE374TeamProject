package problem.asm;

import org.objectweb.asm.ClassVisitor;

public abstract class AbstractClassDataVisitor extends ClassVisitor{
	protected IData classData;
	
	public AbstractClassDataVisitor(int api, AbstractClassDataVisitor decorated) {
		super(api, decorated);
		if(decorated ==null){
			this.classData = new ClassData();
		}
		else{
			this.classData = decorated.getClassData();
		}
		
	}

	public ClassData getClassData() {
		return (ClassData) this.classData;
	}

}
