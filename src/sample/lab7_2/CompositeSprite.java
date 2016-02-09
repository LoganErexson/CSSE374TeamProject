package sample.lab7_2;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeSprite extends AbstractSprite {
	List<ISprite> children;

	public CompositeSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		children = new ArrayList<ISprite>();
	}

	@Override
	public Iterator<ISprite> iterator() {
		return new CompositeSpriteIterator(children.iterator());
	}

	@Override
	public void add(ISprite s) {
		children.add(s);
	}

	@Override
	public void remove(ISprite s) {
		children.remove(s);
	}

	@Override
	public ISprite getChild(int index) {
		return children.get(index);
	}

	@Override
	public void move(Dimension space) {
		for(ISprite s : children) {
			s.move(space);
		}
	}
}
