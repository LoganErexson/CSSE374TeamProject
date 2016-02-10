package problem.alphabet;


public class A {
	B b = new B();
	public void doB() {
		System.out.println("This is the first level");
		b.doC();
	}
}
