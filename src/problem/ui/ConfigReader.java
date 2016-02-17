package problem.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
	private List<String> CLASSES = new ArrayList<String>();
	private String UML_OUTPUT = "";
	private String DOT_PATH = "";
	private List<String> PHASES = new ArrayList<String>();
	
	public void configProject(File filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		while((line = br.readLine()) != null) {
			if (line.contains("INPUT-FOLDER")) {
				if((line = br.readLine())!=null && !line.equals("")){
					File folder = new File(line);
					parseFolder(folder);
				}
			} else if (line.contains("INPUT-CLASSES")) {
				while((line = br.readLine())!=null && !line.equals("")){
					CLASSES.add(line);
				}
			} else if (line.contains("OUTPUT-FILE")) {
				if((line = br.readLine()) != null && !line.equals("")) {
					UML_OUTPUT = line;
				}
			} else if (line.contains("DOT-PATH")) {
				if((line = br.readLine()) != null && !line.equals("")) {
					DOT_PATH = line;
				}
			} else if (line.contains("PHASES")) {
				while((line = br.readLine())!=null && !line.equals("")){
					PHASES.add(line);
				}
			}
		}
		br.close();
	}
	
	public void parseFolder(File folder) {
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		CLASSES.add(listOfFiles[i].getAbsolutePath().
	    				substring(listOfFiles[i].getAbsolutePath().lastIndexOf("src\\") + 4, 
	    						listOfFiles[i].getAbsolutePath().lastIndexOf("."))
	    						.replace('\\', '.'));
	    	} else if (listOfFiles[i].isDirectory()) {
	    		parseFolder(listOfFiles[i]);
	    	}
	    }
	}
	
	public List<String> getCLASSES() {
		return CLASSES;
	}

	public String getUML_OUTPUT() {
		return UML_OUTPUT;
	}

	public String getDOT_PATH() {
		return DOT_PATH;
	}

	public List<String> getPHASES() {
		return PHASES;
	}

	public void fetchInputClasses(BufferedReader r) throws IOException {
		String line;
		while((line = r.readLine())!=null && !line.equals("")){
			CLASSES.add(line);
		}
	}
}
