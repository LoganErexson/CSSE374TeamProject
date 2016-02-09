package sample.lab7_2;

import java.util.Iterator;
import java.util.Stack;

public class CompositeSpriteIterator implements Iterator<ISprite> {
	private Stack<Iterator<ISprite>> stack;

	public CompositeSpriteIterator(Iterator<ISprite> iterator) {
		stack = new Stack<Iterator<ISprite>>();
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		} 
		else {
			Iterator<ISprite> iterator = stack.peek();
			if (!iterator.hasNext()) {
				stack.pop();
				return hasNext();
			} 
			else {
				return true;
			}
		}
	}

	@Override
	public ISprite next() {
		if(hasNext()) {
			Iterator<ISprite> iterator = stack.peek();
			ISprite sprite = iterator.next();
			stack.push(sprite.iterator());
			return sprite;
		}
		else {
			return null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();	
	}
}
