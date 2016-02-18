package problem.ui;

import java.io.IOException;

import problem.model.data.IPackageModel;
import problem.util.DesignParser;

public class ClassCheckboxData {
	private String text;
	private boolean checked;
	private DesignParser parser;

	public ClassCheckboxData(final String text, final boolean checked, DesignParser parser) {
		this.text = text;
		this.checked = checked;
		this.parser = parser;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) throws IOException {
		this.checked = checked;
		
		if(!this.checked) {
			if (this.parser.getModel().getClassNames().contains(this.text))
				this.parser.getModel().addInactiveClass(this.parser.getModel().getClassDataFromName(this.text));
		} else {
			if (this.parser.getModel().getInactiveClasses().contains(this.parser.getModel().getClassDataFromName(this.text)))
				this.parser.getModel().removeInactiveClass(this.parser.getModel().getClassDataFromName(this.text));
		}
		this.parser.createOutputFiles();
	}
	
	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
