package problem.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import problem.model.data.IPackageModel;

public class ClassCheckboxRenderer  implements TreeCellRenderer {
	private final ClassSelectorPanel panel = new ClassSelectorPanel();

	private final DefaultTreeCellRenderer defaultRenderer =
		new DefaultTreeCellRenderer();

	private final Color selectionForeground, selectionBackground;
	private final Color textForeground, textBackground;

	protected ClassSelectorPanel getPanel() {
		return panel;
	}

	public ClassCheckboxRenderer() {
		final Font fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) panel.label.setFont(fontValue);

		final Boolean focusPainted =
			(Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		panel.check.setFocusPainted(focusPainted != null && focusPainted);

		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	// -- TreeCellRenderer methods --

	@Override
	public Component getTreeCellRendererComponent(final JTree tree,
		final Object value, final boolean selected, final boolean expanded,
		final boolean leaf, final int row, final boolean hasFocus)
	{
		ClassCheckboxData data = null;
		if (value instanceof DefaultMutableTreeNode) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			final Object userObject = node.getUserObject();
			if (userObject instanceof ClassCheckboxData) {
				data = (ClassCheckboxData) userObject;
			}
		}

		final String stringValue =
			tree.convertValueToText(value, selected, expanded, leaf, row, false);
		panel.label.setText(stringValue);
		panel.check.setSelected(false);

		panel.setEnabled(tree.isEnabled());
		
		if (selected) {
			panel.setForeground(selectionForeground);
			panel.setBackground(selectionBackground);
			panel.label.setForeground(selectionForeground);
			panel.label.setBackground(selectionBackground);
		}
		else {
			panel.setForeground(textForeground);
			panel.setBackground(textBackground);
			panel.label.setForeground(textForeground);
			panel.label.setBackground(textBackground);
		}

		if (data == null) {
			// not a check box node; return default cell renderer
			return defaultRenderer.getTreeCellRendererComponent(tree, value,
				selected, expanded, leaf, row, hasFocus);
		}

		panel.label.setText(data.getText());
		try {
			panel.check.setSelected(data.isChecked());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return panel;
	}
}
