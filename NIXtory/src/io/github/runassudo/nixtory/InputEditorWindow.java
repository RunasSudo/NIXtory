package io.github.runassudo.nixtory;

import io.github.runassudo.libnixtory.FFmpegInput;
import io.github.runassudo.libnixtory.VideoInput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class InputEditorWindow extends JFrame {

	private static final long serialVersionUID = 6758971231500131374L;
	private JPanel contentPane;

	private FFmpegInput input;
	private JComboBox<String> ddInputType;

	/**
	 * Create the frame.
	 */
	public InputEditorWindow(final boolean isVideo,
			final InputEditedListener listener) {
		setTitle("Edit Input");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblInputType = new JLabel("Input Type");
		contentPane.add(lblInputType, "1, 2");

		ddInputType = new JComboBox<String>();
		ddInputType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					switch ((String) ddInputType.getSelectedItem()) {
					case "null":
						input = null;
					case "File":
						if (isVideo) {
							input = new VideoInput() {
								@Override
								public String toString() {
									return "File";
								}
							};
						}
						break;
					}
				}
			}
		});
		contentPane.add(ddInputType, "3, 2");

		JButton btnApplyChanges = new JButton("");
		btnApplyChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (input != null)
					listener.inputEdited(input);
				dispose();
			}
		});
		btnApplyChanges
				.setIcon(new ImageIcon(
						InputEditorWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/dialog-apply.png")));
		contentPane.add(btnApplyChanges, "5, 2");

		JButton btnRejectChanges = new JButton("");
		btnRejectChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnRejectChanges
				.setIcon(new ImageIcon(
						InputEditorWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/window-close.png")));
		contentPane.add(btnRejectChanges, "7, 2");

		if (isVideo) {
			ddInputType.setModel(new DefaultComboBoxModel<String>(new String[] {
					"null", "File", "Screen (x11grab)", "Application (glc)",
					"libav Filter (lavfi)" }));
		} else {
			ddInputType.setModel(new DefaultComboBoxModel<String>(new String[] {
					"null", "File", "libav Filter (lavfi)" }));
		}
	}

}
