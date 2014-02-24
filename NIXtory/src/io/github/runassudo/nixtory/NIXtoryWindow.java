package io.github.runassudo.nixtory;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.Beans;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class NIXtoryWindow {

	private JFrame frmNixtoryDev;
	private JComboBox<SettingsProfile> ddSettingsProfile;

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
		tmp1.name = "Twitch";
		SettingsProfile tmp2 = new SettingsProfile();
		tmp2.name = "YouTube";
		tmp2.modified = true;
		ddSettingsProfile.addItem(tmp1);
		ddSettingsProfile.addItem(tmp2);
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
		tabProfile.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
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
		btnSaveProfile.setIcon(new ImageIcon(NIXtoryWindow.class
				.getResource("/io/github/runassudo/nixtory/assets/tick.png")));
		tabProfile.add(btnSaveProfile, "5, 2");

		JButton btnDeleteProfile = new JButton("");
		btnDeleteProfile.setIcon(new ImageIcon(NIXtoryWindow.class
				.getResource("/io/github/runassudo/nixtory/assets/cross.png")));
		tabProfile.add(btnDeleteProfile, "7, 2");

		JPanel tabInput = new JPanel();
		tpMain.addTab("Input", null, tabInput, null);

		JPanel tabEncoder = new JPanel();
		tpMain.addTab("Encoder", null, tabEncoder, null);

		JPanel tabOutput = new JPanel();
		tpMain.addTab("Output", null, tabOutput, null);

		JPanel tabHotkeys = new JPanel();
		tpMain.addTab("Hotkeys", null, tabHotkeys, null);
	}

}
