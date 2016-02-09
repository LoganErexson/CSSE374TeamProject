package sample.lab7_2;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public abstract class AbstractSprite implements ISprite {
	protected double dx;
	protected double dy;
	protected Shape shape;
	

	// Subclasses need to chain this constructor
	public AbstractSprite(double x, double y, double width, double height) {
		this.dx = SpriteFactory.DX;
		this.dy = SpriteFactory.DY;
	}

	
	// Designed to be used by subclasses
	protected final Rectangle2D computeNewBoundsAfterMoving(Dimension space) {
		Rectangle2D bounds = shape.getBounds2D();
		
		if(bounds.getX() < 0 || bounds.getX() > space.getWidth())
			dx = -dx;

		if(bounds.getY() < 0 || bounds.getY() > space.getHeight())
			dy = -dy;
		
		Rectangle2D newBounds = new Rectangle2D.Double(bounds.getX() + dx,
														bounds.getY() + dy,
														bounds.getWidth(),
														bounds.getHeight());
		return newBounds;
	}
	
	@Override
	public final Shape getShape() {
		return this.shape;
	}
	
	@Override
	public void add(ISprite s) {
		throw new UnsupportedOperationException();
	}


	@Override
	public void remove(ISprite s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ISprite getChild(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<ISprite> iterator() {
		return new NullSpriteIterator();
	}
	
	@Override
	public abstract void move(Dimension space);
}
