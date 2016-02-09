package sample.lab7_2;

public class CrystalBall extends CompositeSprite {

	public CrystalBall(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.add(new RectangleTower(x, y, width, height));
		this.add(new CircleSprite(x + 15, y - 3 * SpriteFactory.HEIGHT + SpriteFactory.HEIGHT - (width-30), width-30, width-30));
	}
}
