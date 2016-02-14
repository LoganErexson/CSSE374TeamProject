package problem.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DesignParserWindow {
	
	private JFrame frame;
	private JPanel landingPanel;
	private JPanel resultPanel;
	public DesignParserWindow(){
		this.frame = new JFrame("Design Parser");
		this.landingPanel = new LandingPanel(this);
		this.resultPanel = new ResultPanel();
		this.frame.add(this.landingPanel);
	}
	public void analyze(){
		this.frame.remove(this.landingPanel);
		this.frame.add(this.resultPanel);
	}
	
	public void show() {
		this.frame.setVisible(true);
	}

}
