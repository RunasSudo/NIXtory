package io.github.runassudo.nixtory;

import io.github.runassudo.libnixtory.AudioInput;
import io.github.runassudo.libnixtory.SettingsProfile;
import io.github.runassudo.libnixtory.VideoInput;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.Beans;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NIXtoryWindow {

	private JFrame frmNixtoryDev;
	private JComboBox<SettingsProfile> ddSettingsProfile;
	private JList<VideoInput> listVideoInputs;
	private JList<AudioInput> listAudioInputs;

	private DefaultListModel<VideoInput> modelVideoInputs;
	private DefaultListModel<AudioInput> modelAudioInputs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Because WindowBuilder doesn't like SeaGlass set normally.
		System.setProperty("swing.defaultlaf",
				"com.seaglasslookandfeel.SeaGlassLookAndFeel");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NIXtoryWindow window = new NIXtoryWindow();
					window.frmNixtoryDev.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NIXtoryWindow() {
		initialize();
		if (!Beans.isDesignTime()) {
			loadProfiles();
		}
	}

	private void loadProfiles() {
		// TODO: Actually load settings profiles.
		SettingsProfile tmp1 = new SettingsProfile();
		tmp1.name = "Scratch Profile";
		tmp1.modified = true;
		ddSettingsProfile.addItem(tmp1);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNixtoryDev = new JFrame();
		frmNixtoryDev.setTitle("NIXtory 0.2 DEV");
		frmNixtoryDev.setBounds(100, 100, 450, 300);
		frmNixtoryDev.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tpMain = new JTabbedPane(JTabbedPane.TOP);
		frmNixtoryDev.getContentPane().add(tpMain, BorderLayout.CENTER);

		JPanel tabProfile = new JPanel();
		tpMain.addTab("Profile", null, tabProfile, null);
		tabProfile
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblSettingsProfile = new JLabel("Settings Profile:");
		tabProfile.add(lblSettingsProfile, "1, 2");

		ddSettingsProfile = new JComboBox<SettingsProfile>();
		ddSettingsProfile.setModel(new DefaultComboBoxModel<SettingsProfile>());
		lblSettingsProfile.setLabelFor(ddSettingsProfile);
		tabProfile.add(ddSettingsProfile, "3, 2");

		JButton btnSaveProfile = new JButton("");
		btnSaveProfile
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/dialog-apply.png")));
		btnSaveProfile.putClientProperty("JButton.buttonType", "segmented");
		btnSaveProfile.putClientProperty("JButton.segmentPosition", "first");
		tabProfile.add(btnSaveProfile, "5, 2");

		JButton btnDeleteProfile = new JButton("");
		btnDeleteProfile
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/window-close.png")));
		btnDeleteProfile.putClientProperty("JButton.buttonType", "segmented");
		btnDeleteProfile.putClientProperty("JButton.segmentPosition", "last");
		tabProfile.add(btnDeleteProfile, "6, 2");

		JPanel tabInput = new JPanel();
		tpMain.addTab("Input", null, tabInput, null);
		tabInput.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		JLabel lblVideoInputs = new JLabel("Video Inputs");
		tabInput.add(lblVideoInputs, "1, 2");

		JButton btnAddVideoInput = new JButton("");
		btnAddVideoInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Add actual Video Input.
				modelVideoInputs.addElement(new VideoInput() {
					@Override
					public String toString() {
						return "Screen Capture (0.0, 0x0, 1600x900)";
					}
				});
			}
		});
		btnAddVideoInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-add.png")));
		btnAddVideoInput.putClientProperty("JButton.buttonType", "segmented");
		btnAddVideoInput.putClientProperty("JButton.segmentPosition", "first");
		tabInput.add(btnAddVideoInput, "3, 2");

		JButton btnRemoveVideoInput = new JButton("");
		btnRemoveVideoInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sel = listVideoInputs.getSelectedIndex();
				if (sel >= 0)
					modelVideoInputs.remove(sel);
			}
		});
		btnRemoveVideoInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-remove.png")));
		btnRemoveVideoInput
				.putClientProperty("JButton.buttonType", "segmented");
		btnRemoveVideoInput.putClientProperty("JButton.segmentPosition",
				"middle");
		tabInput.add(btnRemoveVideoInput, "4, 2");

		JButton btnEditVideoInput = new JButton("");
		btnEditVideoInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/gtk-edit.png")));
		btnEditVideoInput.putClientProperty("JButton.buttonType", "segmented");
		btnEditVideoInput.putClientProperty("JButton.segmentPosition", "last");
		tabInput.add(btnEditVideoInput, "5, 2");
		modelVideoInputs = new DefaultListModel<VideoInput>();

		JScrollPane spVideoInputs = new JScrollPane();
		tabInput.add(spVideoInputs, "1, 4, 5, 1, fill, fill");

		listVideoInputs = new JList<VideoInput>();
		spVideoInputs.setViewportView(listVideoInputs);
		listVideoInputs.setModel(modelVideoInputs);

		JLabel lblAudioInputs = new JLabel("Audio Inputs");
		tabInput.add(lblAudioInputs, "1, 6");

		JButton btnAddAudioInput = new JButton("");
		btnAddAudioInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Add actual Audio Input.
				modelAudioInputs.addElement(new AudioInput() {
					@Override
					public String toString() {
						return "Screen Capture (0.0, 0x0, 1600x900)";
					}
				});
			}
		});
		btnAddAudioInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-add.png")));
		btnEditVideoInput.putClientProperty("JButton.buttonType", "segmented");
		btnEditVideoInput.putClientProperty("JButton.segmentPosition", "first");
		tabInput.add(btnAddAudioInput, "3, 6");

		JButton btnRemoveAudioInput = new JButton("");
		btnRemoveAudioInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sel = listAudioInputs.getSelectedIndex();
				if (sel >= 0)
					modelAudioInputs.remove(sel);
			}
		});
		btnRemoveAudioInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-remove.png")));
		btnEditVideoInput.putClientProperty("JButton.buttonType", "segmented");
		btnEditVideoInput
				.putClientProperty("JButton.segmentPosition", "middle");
		tabInput.add(btnRemoveAudioInput, "4, 6");

		JButton btnEditAudioInput = new JButton("");
		btnEditAudioInput
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/gtk-edit.png")));
		btnEditVideoInput.putClientProperty("JButton.buttonType", "segmented");
		btnEditVideoInput.putClientProperty("JButton.segmentPosition", "last");
		tabInput.add(btnEditAudioInput, "5, 6");
		modelAudioInputs = new DefaultListModel<AudioInput>();

		JScrollPane spAudioInputs = new JScrollPane();
		tabInput.add(spAudioInputs, "1, 8, 5, 1, fill, fill");

		listAudioInputs = new JList<AudioInput>();
		spAudioInputs.setViewportView(listAudioInputs);
		listAudioInputs.setModel(modelAudioInputs);

		JPanel tabEncoder = new JPanel();
		tpMain.addTab("Encoder", null, tabEncoder, null);

		JPanel tabOutput = new JPanel();
		tpMain.addTab("Output", null, tabOutput, null);

		JPanel tabHotkeys = new JPanel();
		tpMain.addTab("Hotkeys", null, tabHotkeys, null);
	}

}
