package problem.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DesignParserWindow {
	
	private JFrame frame;
	private JPanel landingPanel;
	private JPanel resultPanel;
	private JButton loadButton;
	private JButton analyzeButton;
	private String configFile = "";
	
	public DesignParserWindow(){
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
				// TODO Auto-generated method stub.
				
			}
			
		});
		this.analyzeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub.
				
			}
			
		});
	}
	public void show() {
		this.frame.setVisible(true);
	}
	

}
