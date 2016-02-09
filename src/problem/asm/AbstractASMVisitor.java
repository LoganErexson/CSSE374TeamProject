package problem.asm;

import org.objectweb.asm.ClassVisitor;

import problem.model.data.ClassData;

public abstract class AbstractASMVisitor extends ClassVisitor{
	protected ClassData classData;

	public AbstractASMVisitor(int api, AbstractASMVisitor decorated) {
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

	public void setClassData(ClassData classData) {
		this.classData = classData;
	}

}
