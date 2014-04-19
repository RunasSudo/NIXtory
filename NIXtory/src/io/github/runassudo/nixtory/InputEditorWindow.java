package io.github.runassudo.nixtory;

import io.github.runassudo.libnixtory.FFmpegInput;
import io.github.runassudo.libnixtory.inputs.X11GrabInput;

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

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.awt.GridLayout;

public class InputEditorWindow extends JFrame {

	private static final long serialVersionUID = 6758971231500131374L;
	private JPanel contentPane;

	private FFmpegInput input;
	private JComboBox<String> ddInputType;
	private HashMap<String, FFmpegInput.Setting> inputSettings = new LinkedHashMap<String, FFmpegInput.Setting>();
	private JPanel panelSettings;
	private JPanel settingsWrapper;

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
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelInputType = new JPanel();
		contentPane.add(panelInputType, BorderLayout.NORTH);
		panelInputType.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblInputType = new JLabel("Input Type");
		panelInputType.add(lblInputType, "1, 1");

		ddInputType = new JComboBox<String>();
		ddInputType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					switch ((String) ddInputType.getSelectedItem()) {
					case "null":
						input = null;
						inputSettings.clear();
						updateSettings();
						break;
					case "Screen (x11grab)":
						input = new X11GrabInput();
						inputSettings.clear();
						input.getSettings(inputSettings);
						updateSettings();
						break;
					}
				}
			}
		});
		panelInputType.add(ddInputType, "3, 1");

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
		panelInputType.add(btnApplyChanges, "5, 1");

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
		panelInputType.add(btnRejectChanges, "7, 1");

		// Crazy hack to make options size nicely
		settingsWrapper = new JPanel();
		contentPane.add(settingsWrapper, BorderLayout.CENTER);
		settingsWrapper.setLayout(new BorderLayout());

		panelSettings = new JPanel();
		settingsWrapper.add(panelSettings, BorderLayout.NORTH);
		panelSettings.setLayout(new GridLayout(0, 2, 0, 0));

		if (isVideo) {
			ddInputType.setModel(new DefaultComboBoxModel<String>(new String[] {
					"null", "Video File", "Screen (x11grab)",
					"Application (glc)", "libav Video Filter (lavfi)" }));
		} else {
			ddInputType.setModel(new DefaultComboBoxModel<String>(new String[] {
					"null", "Audio File", "libav Audio Filter (lavfi)" }));
		}
	}

	private void updateSettings() {
		panelSettings.removeAll();
		for (FFmpegInput.Setting setting : inputSettings.values()) {
			panelSettings.add(new JLabel(setting.displayName + ":"));
			panelSettings.add(setting.component);
		}
		settingsWrapper.revalidate();
	}

}
