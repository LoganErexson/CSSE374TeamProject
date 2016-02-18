package problem.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import problem.asm.AbstractASMVisitor;
import problem.asm.VisitorManager;
import problem.detector.AdapterDetector;
import problem.detector.CompositeDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.model.data.IClassData;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.UMLVisitor;

public class DesignParser extends Observable{
	
	private File configFile;
	private ConfigReader reader;
	private PackageModel model;
	private String imagePath;

	public DesignParser(File configFile){
		this.configFile = configFile;
	}
	
	public void createThread(){
		try {
			this.readClassesFromConfig();
			this.createModel();
			this.createOutputFiles();
		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void readClassesFromConfig() throws IOException{
		this.reader = new ConfigReader();
		this.reader.configProject(this.configFile);
		this.notifyObservers();
	}
	
	private void createModel() throws IOException{
		List<IClassData> classDatas = new ArrayList<>();
		for (String className : this.reader.getClasses()) {
			System.out.println(className);
			AbstractASMVisitor visitor = VisitorManager.visitClass(className);
			classDatas.add(visitor.getClassData());
		}
		
		List<IPatternDetector> detectors = phaseSelector(this.reader.getPhases());
		
		this.model = new PackageModel(detectors);
		this.model.setClasses(classDatas);
		this.notifyObservers();
	}
	
	private void createOutputFiles() throws IOException{
		OutputStream out = new FilterOutputStream(new FileOutputStream(this.reader.getOutputFile()+"\\Output.dot"));
		IVisitor visitor = new UMLVisitor();
		this.model.accept(visitor);
		visitor.printToOutput(out);
		out.close();
		Runtime rt = Runtime.getRuntime();
		String outputString= this.reader.getOutputFile()+"\\\\Output.";
		this.imagePath = outputString+"png";
		String command = "\""+this.reader.getDotPath()+"\" -Tpng " +outputString +"dot -o "+this.imagePath;
		rt.exec(command);

		this.notifyObservers();
	}
	
	private List<IPatternDetector> phaseSelector(List<String> phases) {
		List<IPatternDetector> detectors = new ArrayList<>();
		detectors.add(new InterfaceDetector());
		if(phases.contains("Singleton-Detection"))
			detectors.add(new SingletonDetector(false));
		if(phases.contains("Decorator-Detection"))
			detectors.add(new DecoratorDetector(1));
		if(phases.contains("Adapter-Detection"))
			detectors.add(new AdapterDetector(2));
		if(phases.contains("Composite-Detection"))
			detectors.add(new CompositeDetector());
		return detectors;
	}

	public ConfigReader getReader() {
		return this.reader;
	}

	public void setReader(ConfigReader reader) {
		this.reader = reader;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public PackageModel getModel() {
		return this.model;
	}

	public void setModel(PackageModel model) {
		this.model = model;
	}

}
