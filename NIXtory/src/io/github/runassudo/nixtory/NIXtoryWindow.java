package io.github.runassudo.nixtory;

import java.awt.EventQueue;

import javax.swing.JFrame;

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
	}

}
