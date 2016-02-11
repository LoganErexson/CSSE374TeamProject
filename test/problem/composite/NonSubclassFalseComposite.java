package problem.composite;

import java.util.List;

public class NonSubclassFalseComposite {
	private List<IComponent> comps;
	
	public void add(IComponent comp){
		this.comps.add(comp);
	}
	public void remove(IComponent comp){
		this.comps.remove(comp);
	}
}
