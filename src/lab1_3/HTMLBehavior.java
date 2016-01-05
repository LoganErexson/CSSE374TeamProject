package lab1_3;

public class HTMLBehavior implements Behavior {

	@Override
	public String launch(String file) {
		System.out.println(file);
		System.out.println(file.toUpperCase());
		return "explorer";
	}

}
