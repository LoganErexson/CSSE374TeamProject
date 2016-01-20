package problem.asm;

public class B {
	C c = new C();
	public void doC() {
		System.out.println("This is the second level");
		c.doD();
	}
}
