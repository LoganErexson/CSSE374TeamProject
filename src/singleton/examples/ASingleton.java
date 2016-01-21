package singleton.examples;

public class ASingleton {
	private ASingleton single;
	
	private ASingleton() {
		single = null;
	}
	
	public ASingleton singletonReturner() {
		single = new ASingleton();
		return single;
	}
}
