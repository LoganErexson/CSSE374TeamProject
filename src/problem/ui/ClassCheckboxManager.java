package problem.ui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import problem.model.data.IPackageModel;

public class ClassCheckboxManager extends AbstractCellEditor implements TreeCellEditor {

	private final ClassCheckboxRenderer renderer = new ClassCheckboxRenderer();

	private final JTree tree;
	private IPackageModel model;

	public ClassCheckboxManager(final JTree tree, IPackageModel m) {
		this.tree = tree;
		this.model = m;
	}

	@Override
	public Object getCellEditorValue() {
		final ClassSelectorPanel panel = renderer.getPanel();
		final ClassCheckboxData checkBoxNode =
			new ClassCheckboxData(panel.label.getText(), panel.check.isSelected(), this.model);
		return checkBoxNode;
	}

	@Override
	public boolean isCellEditable(final EventObject event) {
		if (!(event instanceof MouseEvent)) return false;
		final MouseEvent mouseEvent = (MouseEvent) event;

		final TreePath path =
			tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
		if (path == null) return false;

		final Object node = path.getLastPathComponent();
		if (!(node instanceof DefaultMutableTreeNode)) return false;
		final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;

		final Object userObject = treeNode.getUserObject();
		return userObject instanceof ClassCheckboxData;
	}

	@Override
	public Component getTreeCellEditorComponent(final JTree tree,
		final Object value, final boolean selected, final boolean expanded,
		final boolean leaf, final int row)
	{

		final Component editor =
			renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
				row, true);

		// editor always selected / focused
		final ItemListener itemListener = new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent itemEvent) {
				if (stopCellEditing()) {
					fireEditingStopped();
				}
			}
		};
		if (editor instanceof ClassSelectorPanel) {
			final ClassSelectorPanel panel = (ClassSelectorPanel) editor;
			panel.check.addItemListener(itemListener);
		}

		return editor;
	}

}
