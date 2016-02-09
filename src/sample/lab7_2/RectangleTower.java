package sample.lab7_2;

public class RectangleTower extends CompositeSprite {

	public RectangleTower(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.add(new RectangleSprite(x,y, width, height));
		this.add(new RectangleSprite(x + 5, y - SpriteFactory.HEIGHT, width - 10, height));
		this.add(new RectangleSprite(x + 10, y - 2 * SpriteFactory.HEIGHT, width - 20, height));
	}
}
