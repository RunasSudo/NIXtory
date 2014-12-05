// NIXtory: DXtory for Linux.
// Copyright © 2014 RunasSudo
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package io.github.runassudo.nixtory;

import io.github.runassudo.libnixtory.NIXtoryInput;
import io.github.runassudo.libnixtory.NIXtoryOutput;
import io.github.runassudo.libnixtory.NIXtoryStream;
import io.github.runassudo.nixtory.WindowStreamTypeChooser.StreamTypeChooserCallback;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NIXtoryWindow {

	private JFrame frmNixtory;
	private JTextField txtOutputDirectory;

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
		frmNixtory.setBounds(100, 100, 450, 500);
		frmNixtory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tpMain = new JTabbedPane(JTabbedPane.TOP);
		frmNixtory.getContentPane().add(tpMain, BorderLayout.CENTER);

		JPanel pnOverview = new JPanel();
		tpMain.addTab("Overview", null, pnOverview, null);
		pnOverview.setLayout(new BorderLayout(0, 0));

		JPanel pnProfiles = new JPanel();
		pnOverview.add(pnProfiles, BorderLayout.NORTH);
		pnProfiles.setLayout(new BorderLayout(0, 0));

		JPanel pnProfileSL = new JPanel();
		pnProfiles.add(pnProfileSL, BorderLayout.EAST);
		pnProfileSL.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnProfileSave = new JButton("");
		btnProfileSave
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/document-save.png")));
		btnProfileSave.putClientProperty("JButton.buttonType", "segmented");
		btnProfileSave.putClientProperty("JButton.segmentPosition", "first");
		pnProfileSL.add(btnProfileSave);

		JButton btnProfileLoad = new JButton("");
		btnProfileLoad
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/document-open.png")));
		btnProfileLoad.putClientProperty("JButton.buttonType", "segmented");
		btnProfileLoad.putClientProperty("JButton.segmentPosition", "last");
		pnProfileSL.add(btnProfileLoad);

		JPanel pnControlsOverview = new JPanel();
		pnOverview.add(pnControlsOverview, BorderLayout.SOUTH);
		pnControlsOverview.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnStartOverview = new JButton("Start Recording");
		pnControlsOverview.add(btnStartOverview);

		JButton btnStopOverview = new JButton("Stop Recording");
		pnControlsOverview.add(btnStopOverview);

		JButton btnOpenFolderOverview = new JButton("Open Folder");
		pnControlsOverview.add(btnOpenFolderOverview);

		JPanel pnOverviewContent = new JPanel();
		pnOverview.add(pnOverviewContent, BorderLayout.CENTER);
		pnOverviewContent.setLayout(new BorderLayout(0, 0));

		JPanel pnOverviewIO = new JPanel();
		pnOverviewContent.add(pnOverviewIO);
		pnOverviewIO.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel pnOverviewInputs = new JPanel();
		pnOverviewIO.add(pnOverviewInputs);
		pnOverviewInputs.setLayout(new BorderLayout(0, 0));

		JLabel lblInputStreams = new JLabel("Input Streams");
		pnOverviewInputs.add(lblInputStreams, BorderLayout.NORTH);

		JScrollPane spOverviewInputs = new JScrollPane();
		pnOverviewInputs.add(spOverviewInputs, BorderLayout.CENTER);

		final DefaultListModel<NIXtoryInput> modelInputs =
				new DefaultListModel<>();
		JList<NIXtoryInput> listOverviewInputs = new JList<>(modelInputs);
		spOverviewInputs.setViewportView(listOverviewInputs);

		JPanel pnOverviewOutputs = new JPanel();
		pnOverviewIO.add(pnOverviewOutputs);
		pnOverviewOutputs.setLayout(new BorderLayout(0, 0));

		JLabel lblOutputStreams = new JLabel("Output Streams");
		pnOverviewOutputs.add(lblOutputStreams, BorderLayout.NORTH);

		JScrollPane spOverviewOutputs = new JScrollPane();
		pnOverviewOutputs.add(spOverviewOutputs, BorderLayout.CENTER);

		final DefaultListModel<NIXtoryOutput> modelOutputs =
				new DefaultListModel<>();
		JList<NIXtoryOutput> listOverviewOutputs = new JList<>(modelOutputs);
		spOverviewOutputs.setViewportView(listOverviewOutputs);

		JPanel pnOutputDirectory = new JPanel();
		pnOverviewContent.add(pnOutputDirectory, BorderLayout.SOUTH);
		pnOutputDirectory.setLayout(new BorderLayout(0, 0));

		JLabel lblOutputDirectory = new JLabel("Output Directory:");
		pnOutputDirectory.add(lblOutputDirectory, BorderLayout.WEST);

		txtOutputDirectory = new JTextField();
		pnOutputDirectory.add(txtOutputDirectory, BorderLayout.CENTER);
		txtOutputDirectory.setColumns(10);

		JButton btnOutputDirectory = new JButton("...");
		pnOutputDirectory.add(btnOutputDirectory, BorderLayout.EAST);

		JPanel pnInputs = new JPanel();
		tpMain.addTab("Input Streams", null, pnInputs, null);
		pnInputs.setLayout(new BorderLayout(0, 0));

		JPanel pnInputAR = new JPanel();
		pnInputs.add(pnInputAR, BorderLayout.NORTH);
		pnInputAR.setLayout(new BorderLayout(0, 0));

		JPanel pnInputAR2 = new JPanel();
		pnInputAR.add(pnInputAR2, BorderLayout.EAST);
		pnInputAR2.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnInputAdd = new JButton("");
		btnInputAdd.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				WindowStreamTypeChooser<NIXtoryInput> chooser =
						new WindowStreamTypeChooser<>(new AddStreamHandler<>(
								modelInputs), NIXtoryInput.getSupportedInputs());
				chooser.setLocationRelativeTo(frmNixtory);
				chooser.setVisible(true);
			}
		});
		btnInputAdd
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-add.png")));
		pnInputAR2.add(btnInputAdd);

		JButton btnInputRemove = new JButton("");
		btnInputRemove
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-remove.png")));
		pnInputAR2.add(btnInputRemove);

		JPanel pnInputsContent = new JPanel();
		pnInputs.add(pnInputsContent, BorderLayout.CENTER);
		pnInputsContent.setLayout(new BorderLayout(0, 0));

		JLabel lblInputStreams_1 = new JLabel("Input Streams");
		pnInputsContent.add(lblInputStreams_1, BorderLayout.NORTH);

		JScrollPane spInputs = new JScrollPane();
		pnInputsContent.add(spInputs, BorderLayout.CENTER);

		JList<NIXtoryInput> listInputs = new JList<>(modelInputs);
		spInputs.setViewportView(listInputs);

		JPanel pnOutputs = new JPanel();
		tpMain.addTab("Output Streams", null, pnOutputs, null);
		pnOutputs.setLayout(new BorderLayout(0, 0));

		JPanel pnOutputAR = new JPanel();
		pnOutputs.add(pnOutputAR, BorderLayout.NORTH);
		pnOutputAR.setLayout(new BorderLayout(0, 0));

		JPanel pnOutputAR2 = new JPanel();
		pnOutputAR.add(pnOutputAR2, BorderLayout.EAST);
		pnOutputAR2.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnOutputAdd = new JButton("");
		btnOutputAdd.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				WindowStreamTypeChooser<NIXtoryOutput> chooser =
						new WindowStreamTypeChooser<>(new AddStreamHandler<>(
								modelOutputs), NIXtoryOutput
								.getSupportedOutputs());
				chooser.setLocationRelativeTo(frmNixtory);
				chooser.setVisible(true);
			}
		});
		btnOutputAdd
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-add.png")));
		pnOutputAR2.add(btnOutputAdd);

		JButton btnOutputRemove = new JButton("");
		btnOutputRemove
				.setIcon(new ImageIcon(
						NIXtoryWindow.class
								.getResource("/io/github/runassudo/nixtory/assets/list-remove.png")));
		pnOutputAR2.add(btnOutputRemove);

		JPanel pnOutputsContent = new JPanel();
		pnOutputs.add(pnOutputsContent);
		pnOutputsContent.setLayout(new BorderLayout(0, 0));

		JLabel lblOutputStreams_1 = new JLabel("Output Streams");
		pnOutputsContent.add(lblOutputStreams_1, BorderLayout.NORTH);

		JScrollPane spOutputs = new JScrollPane();
		pnOutputsContent.add(spOutputs, BorderLayout.CENTER);

		JList<NIXtoryOutput> listOutputs = new JList<>(modelOutputs);
		spOutputs.setViewportView(listOutputs);

		JPanel pnStatus = new JPanel();
		tpMain.addTab("Status", null, pnStatus, null);
	}

	class AddStreamHandler<T extends NIXtoryStream> implements
			StreamTypeChooserCallback<T> {
		DefaultListModel<T> model;

		public AddStreamHandler(DefaultListModel<T> model) {
			this.model = model;
		}

		public void streamTypeChosen(Class<T> streamType) {
			try {
				model.addElement(streamType.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
