package problem.ui;

import problem.model.data.IPackageModel;

public class ClassCheckboxData {
	private String text;
	private boolean checked;
	private IPackageModel model;

	public ClassCheckboxData(final String text, final boolean checked, IPackageModel model) {
		this.text = text;
		this.checked = checked;
		this.model = model;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
		
		if(!this.checked) {
			if (this.model.getClassNames().contains(this.text))
				this.model.addInactiveClass(this.model.getClassDataFromName(this.text));
		} else {
			if (this.model.getInactiveClasses().contains(this.model.getClassDataFromName(this.text)))
				this.model.removeInactiveClass(this.model.getClassDataFromName(this.text));
		}
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
