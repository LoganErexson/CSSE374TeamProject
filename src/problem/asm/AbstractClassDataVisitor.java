package problem.asm;

import org.objectweb.asm.ClassVisitor;

public abstract class AbstractClassDataVisitor extends ClassVisitor{
	protected ClassData classData;
	
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
		return this.classData;
	}

}
