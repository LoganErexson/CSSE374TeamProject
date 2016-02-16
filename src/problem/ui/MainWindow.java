package problem.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import problem.asm.AbstractASMVisitor;
import problem.asm.VisitorManager;
import problem.detector.AdapterDetector;
import problem.detector.CompositeDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.main.DesignParser;
import problem.model.data.IClassData;
import problem.model.data.IPackageModel;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.UMLVisitor;

public class MainWindow {
	
	private JFrame frame;
	private JPanel landingPanel;
	private JPanel resultPanel;
	private JButton loadButton;
	private JButton analyzeButton;
	private File configFile;
	private List<String> classes;
	
	public MainWindow(){
		this.frame = new JFrame("Design Parser");
		this.buildLandingPanel();
		this.buildResultPanel();
		this.assignActions();
		this.frame.add(this.landingPanel);
	}
	public void analyze(){
		this.frame.remove(this.landingPanel);
		this.frame.add(this.resultPanel);
	}
	private void buildLandingPanel(){
		this.landingPanel = new JPanel();
		this.loadButton = new JButton("Load Config");
		this.analyzeButton = new JButton("Analyze");
		this.landingPanel.add(this.loadButton);
		this.landingPanel.add(this.analyzeButton);
	}
	private void buildResultPanel(){
		this.resultPanel = new JPanel();
	}
	
	private void assignActions() {
		this.loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"\\input_output");
				int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					MainWindow.this.setConfigFile(fc.getSelectedFile());
				}
			}
			
		});
		this.analyzeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> classList = new ArrayList<>(); 
				BufferedReader br;
				try {
//					br = new BufferedReader(new FileReader(MainWindow.this.getConfigFile()));
					ConfigReader reader = new ConfigReader();
					reader.configProject(MainWindow.this.getConfigFile());
					classList = reader.getCLASSES();
					
//					String line;
//					while((line = br.readLine())!=null){
//						classList.add(line);
//					}
//					br.close();
					MainWindow.this.setClasses(classList);
					List<IClassData> classDatas = new ArrayList<>();
					for (String className : classList) {
						System.out.println(className);
						AbstractASMVisitor visitor = VisitorManager.visitClass(className);
						classDatas.add(visitor.getClassData());
					}
					
					List<IPatternDetector> detectors = new ArrayList<>();
					detectors.add(new SingletonDetector());
					detectors.add(new DecoratorDetector());
					detectors.add(new AdapterDetector());
					detectors.add(new InterfaceDetector());
					detectors.add(new CompositeDetector());
					
					IPackageModel model = new PackageModel(detectors);
					model.setClasses(classDatas);
					OutputStream out = new FilterOutputStream(new FileOutputStream(reader.getUML_OUTPUT()));
					IVisitor visitor = new UMLVisitor();
					model.accept(visitor);
					visitor.printToOutput(out);
					out.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				
			}
			
		});
	}
	
	public void show() {
		this.frame.setVisible(true);
	}
	public File getConfigFile() {
		return this.configFile;
	}
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	public List<String> getClasses() {
		return this.classes;
	}
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
	

}
