package lab1_3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Handler {
	private Behavior behavior;
	private String filename;
	
	public Handler(Path file){
		filename = file.toString();
		if(filename.endsWith(".html") || filename.endsWith(".htm")) {
			behavior = new HTMLBehavior();
		}
		else if(filename.endsWith(".txt")) {
			behavior = new TXTBehavior();
		}
		else if(filename.endsWith(".pdf")) {
			behavior = new PDFBehavior();
		}
		else {
			System.err.format("No support available for: %s...%n", file);
			return;
		}
	}
	
	public void handleDirectoryEvent(List<Process> processes, String eventName, Path file) {

		// We are only interested in the new files that get dropped into the launcher folder
		if(!eventName.equals("ENTRY_CREATE"))
			return;

		ProcessBuilder processBuilder = null;
		String command = null;
		String arg = filename;
		
		System.out.println("Processing " + filename + "...");
	
		command = behavior.launch(filename);

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			processBuilder = new ProcessBuilder(command, arg);
			
			// Start and add the process to the processes list
			Process process = processBuilder.start();
			processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
