package io.github.runassudo.nixtory;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class NIXtoryWindow {

	private JFrame frmNixtoryDev;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		
		JPanel tabOutput = new JPanel();
		tpMain.addTab("Output", null, tabOutput, null);
		
		JPanel tabEncoder = new JPanel();
		tpMain.addTab("Encoder", null, tabEncoder, null);
		
		JPanel tabInput = new JPanel();
		tpMain.addTab("Input", null, tabInput, null);
		
		JPanel tabHotkeys = new JPanel();
		tpMain.addTab("Hotkeys", null, tabHotkeys, null);
	}

}
