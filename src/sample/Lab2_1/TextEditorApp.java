package sample.Lab2_1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import edu.roshulman.csse374.editor.TextEditor;

public class TextEditorApp {
	public static void main(String[] args) throws Exception {
		SubstitutionCipher cipher = new SubstitutionCipher();
		InputStream in = new DecryptionInputStream(new FileInputStream("./input_output/out.txt"), cipher);
		OutputStream out = new EncryptionOutputStream(new FileOutputStream("./input_output/out1.txt"), cipher);
		
		TextEditor editor = new TextEditor(in, out);
		editor.execute();
	}	
}
