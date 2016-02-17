package problem.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import problem.asm.AbstractASMVisitor;
import problem.asm.VisitorManager;
import problem.detector.AdapterDetector;
import problem.detector.CompositeDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.model.data.IClassData;
import problem.model.data.IPackageModel;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.UMLVisitor;

public class MainWindow {
	
	private JFrame frame;
	private JPanel contentPane;
	
	private JPanel landingPanel;
	private JPanel resultPanel;
	private JButton loadButton;
	private JButton analyzeButton;
	private File configFile;
	private List<String> classes;
	private String imagePath;
	private JScrollPane picPane;
	private JScrollPane treePane;
	private ConfigReader reader;
	private IPackageModel model; 
	
	public MainWindow(){
		this.frame = new JFrame("Design Parser");
		this.reader = new ConfigReader();
		frame.setSize(500, 500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		this.contentPane = (JPanel)this.frame.getContentPane();
		
		this.buildLandingPanel();
		this.assignActions();
//		this.contentPane.add(this.landingPanel);
		this.frame.add(this.landingPanel);
		
		this.frame.pack();
	}
	public void analyze(){
		this.contentPane.remove(this.landingPanel);
		this.buildResultPanel();
		this.buildCheckboxPanel();
//		this.contentPane.add(this.resultPanel);

//		this.contentPane.add(this.picPane);
		this.resultPanel.add(this.treePane, BorderLayout.WEST);
		this.resultPanel.add(this.picPane,  BorderLayout.EAST);
		this.frame.add(this.resultPanel, BorderLayout.CENTER);
		
//		this.frame.add(this.treePane, BorderLayout.EAST);
		
//		this.contentPane.revalidate();
//		this.contentPane.repaint();
		
		this.frame.repaint();
		
		this.frame.pack();
	}
	private void buildLandingPanel(){
		this.landingPanel = new JPanel();
		this.loadButton = new JButton("Load Config");
		this.analyzeButton = new JButton("Analyze");
		this.landingPanel.add(this.loadButton);
		this.landingPanel.add(this.analyzeButton);
	}
	void buildResultPanel(){
		this.resultPanel = new JPanel();
		Icon image = new ImageProxy(this.imagePath);
		this.picPane = new JScrollPane(new JLabel(image));
//		this.resultPanel.add(this.picPane, BorderLayout.CENTER);
		
//		this.resultPanel.revalidate();
//		this.resultPanel.repaint();
	}
	
	void buildCheckboxPanel() {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for (String phase: reader.getPHASES()) {
			final DefaultMutableTreeNode node =
					add(root, phase, true, this.model);
			
				//add(node, , true, this.model);
				
				root.add(node);
		}
		
		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		final JTree tree = new JTree(treeModel);
		
		final ClassCheckboxRenderer renderer = new ClassCheckboxRenderer();
		tree.setCellRenderer(renderer);
		
		final ClassCheckboxManager manager = new ClassCheckboxManager(tree, this.model);
		tree.setCellEditor(manager);
		tree.setEditable(true);		

		this.treePane = new JScrollPane(tree);
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
				try {
					reader.configProject(MainWindow.this.getConfigFile());
					classList = reader.getCLASSES();
					
					MainWindow.this.setClasses(classList);
					List<IClassData> classDatas = new ArrayList<>();
					for (String className : classList) {
						System.out.println(className);
						AbstractASMVisitor visitor = VisitorManager.visitClass(className);
						classDatas.add(visitor.getClassData());
					}
					
					List<IPatternDetector> detectors = phaseSelector(reader.getPHASES());
					
					model = new PackageModel(detectors);
					model.setClasses(classDatas);
					OutputStream out = new FilterOutputStream(new FileOutputStream(reader.getUML_OUTPUT()+"\\Output.dot"));
					IVisitor visitor = new UMLVisitor();
					model.accept(visitor);
					visitor.printToOutput(out);
					out.close();
					Runtime rt = Runtime.getRuntime();
					String outputString= reader.getUML_OUTPUT()+"\\\\Output.";
					String command = "\""+reader.getDOT_PATH()+"\" -Tpng " +outputString +"dot -o "+outputString+"png";
					rt.exec(command);
					MainWindow.this.setImage(outputString+"png");
					MainWindow.this.analyze();
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
	
	public List<IPatternDetector> phaseSelector(List<String> phases) {
		List<IPatternDetector> detectors = new ArrayList<>();
		detectors.add(new InterfaceDetector());
		if(phases.contains("singleton"))
			detectors.add(new SingletonDetector());
		if(phases.contains("decorator"))
			detectors.add(new DecoratorDetector());
		if(phases.contains("adaptor"))
			detectors.add(new AdapterDetector());
		if(phases.contains("composite"))
			detectors.add(new CompositeDetector());
		return detectors;
	}
	public String getImage() {
		return this.imagePath;
	}
	public void setImage(String image) {
		this.imagePath = image;
	}


	private static DefaultMutableTreeNode add(
		final DefaultMutableTreeNode parent, final String text,
		final boolean checked, final IPackageModel model)
	{
		final ClassCheckboxData data = new ClassCheckboxData(text, checked, model);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
		parent.add(node);
		return node;
	}
}
