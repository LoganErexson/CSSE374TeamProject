package problem.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LandingPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton loadButton;
	private JButton analyzeButton;
	private DesignParserWindow window;
	
	public LandingPanel(DesignParserWindow window){
		this.loadButton = new JButton("Load Config");
		this.analyzeButton = new JButton("analyzeButton");
		this.add(this.loadButton);
		this.add(this.analyzeButton);
		this.window = window;
		this.assignActions();
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

}
