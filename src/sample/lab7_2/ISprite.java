package sample.lab7_2;

import java.awt.Dimension;
import java.awt.Shape;

public interface ISprite extends Iterable<ISprite> {
	public void move(Dimension space);
	public Shape getShape();
	
	public void add(ISprite s);
	public void remove(ISprite s);
	public ISprite getChild(int index);
}
