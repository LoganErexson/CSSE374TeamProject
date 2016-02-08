package sample.lab1_3;

public class PDFBehavior implements Behavior {

	@Override
	public String launch(String file) {
		System.out.println(file);
		System.out.println(file.toUpperCase());
		return "C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader\\AcroRd32.exe";
	}

}
