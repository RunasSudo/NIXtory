package io.github.runassudo.nixtory;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class NIXtoryWindow {

	private JFrame frmNixtory;

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
					window.frmNixtory.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNixtory = new JFrame();
		frmNixtory.setTitle("NIXtory 0.2 DEV");
		frmNixtory.setBounds(100, 100, 450, 300);
		frmNixtory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tpMain = new JTabbedPane(JTabbedPane.TOP);
		frmNixtory.getContentPane().add(tpMain, BorderLayout.CENTER);
		
		JPanel pnOverview = new JPanel();
		tpMain.addTab("Overview", null, pnOverview, null);
		pnOverview.setLayout(new BorderLayout(0, 0));
		
		JPanel pnProfiles = new JPanel();
		pnOverview.add(pnProfiles, BorderLayout.NORTH);
		pnProfiles.setLayout(new BorderLayout(0, 0));
		
		JComboBox cbProfile = new JComboBox();
		pnProfiles.add(cbProfile, BorderLayout.CENTER);
		
		JPanel pnProfileSL = new JPanel();
		pnProfiles.add(pnProfileSL, BorderLayout.EAST);
		pnProfileSL.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnProfileSave = new JButton("");
		btnProfileSave.setIcon(new ImageIcon(NIXtoryWindow.class.getResource("/io/github/runassudo/nixtory/assets/document-save.png")));
		btnProfileSave.putClientProperty("JButton.buttonType", "segmented");
		btnProfileSave.putClientProperty("JButton.segmentPosition", "first");
		pnProfileSL.add(btnProfileSave);
		
		JButton btnProfileLoad = new JButton("");
		btnProfileLoad.setIcon(new ImageIcon(NIXtoryWindow.class.getResource("/io/github/runassudo/nixtory/assets/document-open.png")));
		btnProfileLoad.putClientProperty("JButton.buttonType", "segmented");
		btnProfileLoad.putClientProperty("JButton.segmentPosition", "last");
		pnProfileSL.add(btnProfileLoad);
		
		JPanel pnInputs = new JPanel();
		tpMain.addTab("Input Streams", null, pnInputs, null);
		
		JPanel pnOutputs = new JPanel();
		tpMain.addTab("Output Streams", null, pnOutputs, null);
		
		JPanel pnStatus = new JPanel();
		tpMain.addTab("Status", null, pnStatus, null);
	}
}
