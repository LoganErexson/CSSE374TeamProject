package problem.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
	private List<String> classes = new ArrayList<>();
	private String outputFolder = "";
	private String dotPath = "";
	private List<String> phases = new ArrayList<>();
	
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
					this.classes.add(line);
				}
			} else if (line.contains("OUTPUT-FILE")) {
				if((line = br.readLine()) != null && !line.equals("")) {
					this.outputFolder = line;
				}
			} else if (line.contains("DOT-PATH")) {
				if((line = br.readLine()) != null && !line.equals("")) {
					this.dotPath = line;
				}
			} else if (line.contains("PHASES")) {
				while((line = br.readLine())!=null && !line.equals("")){
					this.phases.add(line);
				}
			}
		}
		br.close();
	}
	
	public void parseFolder(File folder) {
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		this.classes.add(listOfFiles[i].getAbsolutePath().
	    				substring(listOfFiles[i].getAbsolutePath().lastIndexOf("src\\") + 4, 
	    						listOfFiles[i].getAbsolutePath().lastIndexOf("."))
	    						.replace('\\', '.'));
	    	} else if (listOfFiles[i].isDirectory()) {
	    		parseFolder(listOfFiles[i]);
	    	}
	    }
	}
	
	public List<String> getClasses() {
		return this.classes;
	}

	public String getOutputFile() {
		return this.outputFolder;
	}

	public String getDotPath() {
		return this.dotPath;
	}

	public List<String> getPhases() {
		return this.phases;
	}

	public void fetchInputClasses(BufferedReader r) throws IOException {
		String line;
		while((line = r.readLine())!=null && !line.equals("")){
			this.classes.add(line);
		}
	}
}
