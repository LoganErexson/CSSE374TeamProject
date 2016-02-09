package sample.lab7_2;

import java.util.Iterator;

public class NullSpriteIterator implements Iterator<ISprite> {

	public NullSpriteIterator() {
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public ISprite next() {
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();	
	}
}
