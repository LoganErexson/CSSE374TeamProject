package lab1_3;

public class TXTBehavior implements Behavior {

	@Override
	public String launch(String file) {
		System.out.println(file);
		System.out.println(file.toUpperCase());
		return "Notepad";
	}

}
