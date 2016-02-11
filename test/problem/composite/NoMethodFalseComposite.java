package problem.composite;

import java.util.List;

public class NoMethodFalseComposite extends AbstractComponent{

	private List<IComponent> comps;

	@Override
	public void method() {
		if(this.comps!=null)
			System.out.println("This shouldn't happen");
		
	}

}
