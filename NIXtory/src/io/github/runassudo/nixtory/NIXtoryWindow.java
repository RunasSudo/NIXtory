package io.github.runassudo.nixtory;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class NIXtoryWindow {

	ArrayList<Process> processes = new ArrayList<Process>();

	private JFrame frame;
	private JTabbedPane tpVideoInput;
	private JTabbedPane tpAudioInput;
	private JCheckBox chckbxAudioInput;
	private JCheckBox chckbxVideoInput;
	private JTextField txtOutputDirectory;
	private JTextField txtScreen;
	private JTextField txtOffset;
	private JTextField txtResolution;
	private JTextField txtFramerate;
	private JTextArea txtrLog;
	private JTabbedPane tpMain;
	private JButton btnPickWindow;
	private JTextField txtFramerate_GLC;
	private JTextField txtHotkey;
	private JCheckBox chckbxLockFramerate;
	private JTextField txtCommand;
	private JCheckBox chckbxAutostart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NIXtoryWindow window = new NIXtoryWindow();
					window.frame.setVisible(true);
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
		// frame.pack();
		tpAudioInput.removeAll();
		findSettings();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		tpMain = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tpMain);

		JPanel pnOpts = new JPanel();
		tpMain.addTab("Options", null, pnOpts, null);
		pnOpts.setLayout(new BoxLayout(pnOpts, BoxLayout.Y_AXIS));

		JPanel pnVideoInput = new JPanel();
		pnOpts.add(pnVideoInput);
		pnVideoInput.setLayout(new BorderLayout(0, 0));

		JPanel pnChckbxVideoInput = new JPanel();
		pnVideoInput.add(pnChckbxVideoInput, BorderLayout.NORTH);
		pnChckbxVideoInput.setLayout(new BorderLayout(0, 0));

		chckbxVideoInput = new JCheckBox("Video Input");
		pnChckbxVideoInput.add(chckbxVideoInput, BorderLayout.WEST);
		chckbxVideoInput.setSelected(true);

		JPanel pnBtnControls = new JPanel();
		pnChckbxVideoInput.add(pnBtnControls, BorderLayout.EAST);
		pnBtnControls.setLayout(new BoxLayout(pnBtnControls, BoxLayout.X_AXIS));

		btnPickWindow = new JButton("Pick Window");
		btnPickWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String x = null, y = null, width = null, height = null;

					ProcessBuilder pb = new ProcessBuilder("xwininfo");
					Process proc = pb.start();
					proc.waitFor();
					try (BufferedReader rdr = new BufferedReader(
							new InputStreamReader(proc.getInputStream()))) {
						String s = null;
						while ((s = rdr.readLine()) != null) {
							s = s.trim();

							if (s.startsWith("Absolute upper-left X:"))
								x = s.substring(23).trim();
							if (s.startsWith("Absolute upper-left Y:"))
								y = s.substring(23).trim();
							if (s.startsWith("Width:"))
								width = s.substring(6).trim();
							if (s.startsWith("Height:"))
								height = s.substring(7).trim();
						}
					}

					txtOffset.setText(x + "," + y);
					txtResolution.setText(width + "x" + height);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		pnBtnControls.add(btnPickWindow);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tpMain.setSelectedIndex(1);
				startRecording();
			}
		});
		pnBtnControls.add(btnStart);

		tpVideoInput = new JTabbedPane(JTabbedPane.TOP);
		tpVideoInput.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tpVideoInput.getSelectedIndex() == 0)
					btnPickWindow.setEnabled(true);
				else
					btnPickWindow.setEnabled(false);
			}
		});
		pnVideoInput.add(tpVideoInput, BorderLayout.CENTER);

		JPanel pnInputX11Grab = new JPanel();
		tpVideoInput.addTab("x11grab", null, pnInputX11Grab, null);
		pnInputX11Grab.setLayout(new BorderLayout(0, 0));

		JPanel pnX11Grab = new JPanel();
		pnInputX11Grab.add(pnX11Grab, BorderLayout.NORTH);
		pnX11Grab.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblScreen = new JLabel("Screen:");
		pnX11Grab.add(lblScreen);

		txtScreen = new JTextField();
		txtScreen.setText(":0.0");
		pnX11Grab.add(txtScreen);
		txtScreen.setColumns(10);

		JLabel lblOffset = new JLabel("Offset:");
		pnX11Grab.add(lblOffset);

		txtOffset = new JTextField();
		txtOffset.setText("0,0");
		pnX11Grab.add(txtOffset);
		txtOffset.setColumns(10);

		JLabel lblResolution = new JLabel("Resolution:");
		pnX11Grab.add(lblResolution);

		txtResolution = new JTextField();
		txtResolution.setText("1600x900");
		pnX11Grab.add(txtResolution);
		txtResolution.setColumns(10);

		JLabel lblFramerate = new JLabel("Framerate:");
		pnX11Grab.add(lblFramerate);

		txtFramerate = new JTextField();
		txtFramerate.setText("30");
		pnX11Grab.add(txtFramerate);
		txtFramerate.setColumns(10);

		JPanel pnInputGLC = new JPanel();
		tpVideoInput.addTab("GLC (beta)", null, pnInputGLC, null);
		pnInputGLC.setLayout(new BorderLayout(0, 0));

		JPanel pnGLC = new JPanel();
		pnInputGLC.add(pnGLC, BorderLayout.NORTH);
		pnGLC.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblFramerate_GLC = new JLabel("Framerate:");
		pnGLC.add(lblFramerate_GLC);

		txtFramerate_GLC = new JTextField();
		txtFramerate_GLC.setText("30");
		pnGLC.add(txtFramerate_GLC);
		txtFramerate_GLC.setColumns(10);

		JLabel lblLockFramerate = new JLabel("Lock Framerate:");
		pnGLC.add(lblLockFramerate);

		chckbxLockFramerate = new JCheckBox("");
		pnGLC.add(chckbxLockFramerate);

		JLabel lblHotkey = new JLabel("Hotkey:");
		pnGLC.add(lblHotkey);

		txtHotkey = new JTextField();
		txtHotkey.setText("<Shift>F8");
		pnGLC.add(txtHotkey);
		txtHotkey.setColumns(10);

		JLabel lblCommand = new JLabel("Command:");
		pnGLC.add(lblCommand);

		txtCommand = new JTextField();
		pnGLC.add(txtCommand);
		txtCommand.setColumns(10);

		JLabel lblAutostart = new JLabel("Autostart:");
		pnGLC.add(lblAutostart);

		chckbxAutostart = new JCheckBox("");
		pnGLC.add(chckbxAutostart);

		JPanel pnAudioInput = new JPanel();
		pnOpts.add(pnAudioInput);
		pnAudioInput.setLayout(new BorderLayout(0, 0));

		JPanel pnChckbxAudioInput = new JPanel();
		pnAudioInput.add(pnChckbxAudioInput, BorderLayout.NORTH);
		pnChckbxAudioInput.setLayout(new BorderLayout(0, 0));

		chckbxAudioInput = new JCheckBox("Audio Input");
		pnChckbxAudioInput.add(chckbxAudioInput, BorderLayout.WEST);

		JPanel pnBtnAudioInput = new JPanel();
		pnChckbxAudioInput.add(pnBtnAudioInput, BorderLayout.EAST);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tpAudioInput.addTab(
						"Input " + (tpAudioInput.getTabCount() + 1), null,
						makeAudioInputPanel(), null);
			}
		});
		pnBtnAudioInput.setLayout(new BoxLayout(pnBtnAudioInput,
				BoxLayout.X_AXIS));
		pnBtnAudioInput.add(btnAdd);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tpAudioInput.getSelectedIndex() >= 0)
					tpAudioInput.removeTabAt(tpAudioInput.getSelectedIndex());
			}
		});
		pnBtnAudioInput.add(btnRemove);

		tpAudioInput = new JTabbedPane(JTabbedPane.TOP);
		pnAudioInput.add(tpAudioInput);
		tpAudioInput.addTab("Input " + (tpAudioInput.getTabCount() + 1),
				makeAudioInputPanel());

		JPanel pnOutput = new JPanel();
		pnOpts.add(pnOutput);
		pnOutput.setLayout(new BorderLayout(0, 0));

		JLabel lblOutput = new JLabel("Output");
		pnOutput.add(lblOutput, BorderLayout.NORTH);

		JPanel wpOutputOptions = new JPanel();
		pnOutput.add(wpOutputOptions, BorderLayout.CENTER);
		wpOutputOptions.setLayout(new BorderLayout(0, 0));

		JPanel pnOutputOptions = new JPanel();
		wpOutputOptions.add(pnOutputOptions, BorderLayout.NORTH);
		pnOutputOptions.setLayout(new GridLayout(1, 2, 0, 0));

		JLabel lblOutputDirectory = new JLabel("Output Directory:");
		pnOutputOptions.add(lblOutputDirectory);

		txtOutputDirectory = new JTextField();
		txtOutputDirectory.setText("/home/runassudo/Videos/NIXtory");
		pnOutputOptions.add(txtOutputDirectory);
		txtOutputDirectory.setColumns(30);

		JPanel pnLog = new JPanel();
		tpMain.addTab("Log", null, pnLog, null);
		pnLog.setLayout(new BorderLayout(0, 0));

		JPanel pnBtnStop = new JPanel();
		pnLog.add(pnBtnStop, BorderLayout.NORTH);
		pnBtnStop.setLayout(new BorderLayout(0, 0));

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Process p : processes) {
					p.destroy();
				}
			}
		});
		pnBtnStop.add(btnStop, BorderLayout.EAST);

		txtrLog = new JTextArea();
		txtrLog.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtrLog);
		pnLog.add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadConfig = new JMenuItem("Load Config");
		mntmLoadConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser fc = new JFileChooser();
					int choice = fc.showOpenDialog(frame);
					if (choice == JFileChooser.APPROVE_OPTION) {
						Properties data = new Properties();
						data.load(new FileInputStream(fc.getSelectedFile()));

						chckbxVideoInput.setSelected(data.getProperty(
								"videoInput").equals("true"));
						txtScreen.setText(data.getProperty("screen"));
						txtOffset.setText(data.getProperty("offset"));
						txtResolution.setText(data.getProperty("resolution"));
						txtFramerate.setText(data.getProperty("framerate"));
						txtFramerate_GLC.setText(data
								.getProperty("framerateGLC"));
						chckbxLockFramerate.setSelected(data.getProperty(
								"lockFramerate").equals("true"));
						txtHotkey.setText(data.getProperty("hotkey"));
						txtCommand.setText(data.getProperty("command"));
						chckbxAutostart.setSelected(data.getProperty(
								"autostart").equals("true"));

						chckbxAudioInput.setSelected(data.getProperty(
								"audioInput").equals("true"));
						int numAudioInput = Integer.parseInt(data
								.getProperty("numAudioInput"));
						for (int i = 0; i < numAudioInput; i++) {
							String name = data.getProperty("audio" + i
									+ ".name");
							String format = data.getProperty("audio" + i
									+ ".format");
							String input = data.getProperty("audio" + i
									+ ".input");

							tpAudioInput.addTab(name, null,
									makeAudioInputPanel(name, format, input),
									null);
						}

						txtOutputDirectory.setText(data
								.getProperty("outputDirectory"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmLoadConfig);

		JMenuItem mntmSaveConfig = new JMenuItem("Save Config");
		mntmSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser fc = new JFileChooser();
					int choice = fc.showSaveDialog(frame);
					if (choice == JFileChooser.APPROVE_OPTION) {
						Properties data = new Properties();

						data.setProperty("videoInput",
								chckbxVideoInput.isSelected() + "");
						data.setProperty("screen", txtScreen.getText());
						data.setProperty("offset", txtOffset.getText());
						data.setProperty("resolution", txtResolution.getText());
						data.setProperty("framerate", txtFramerate.getText());
						data.setProperty("framerateGLC",
								txtFramerate_GLC.getText());
						data.setProperty("lockFramerate",
								chckbxLockFramerate.isSelected() + "");
						data.setProperty("hotkey", txtHotkey.getText());
						data.setProperty("command", txtCommand.getText());
						data.setProperty("autostart",
								chckbxAutostart.isSelected() + "");

						data.setProperty("audioInput",
								chckbxAudioInput.isSelected() + "");
						data.setProperty("numAudioInput",
								tpAudioInput.getTabCount() + "");
						for (int i = 0; i < tpAudioInput.getTabCount(); i++) {
							JPanel wp = (JPanel) tpAudioInput.getComponentAt(i);
							JPanel tab = (JPanel) wp.getComponent(0);

							String name = ((JTextField) tab.getComponent(1))
									.getText();
							String format = ((JTextField) tab.getComponent(3))
									.getText();
							String input = ((JTextField) tab.getComponent(5))
									.getText();

							data.setProperty("audio" + i + ".name", name);
							data.setProperty("audio" + i + ".format", format);
							data.setProperty("audio" + i + ".input", input);
						}

						data.setProperty("outputDirectory",
								txtOutputDirectory.getText());

						data.store(new FileOutputStream(fc.getSelectedFile()),
								"NIXtory Data");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSaveConfig);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmGoogle = new JMenuItem("Google");
		mnHelp.add(mntmGoogle);
	}

	private void startRecording() {
		if (chckbxVideoInput.isSelected()) {
			if (tpVideoInput.getSelectedIndex() == 0) { // x11grab
				startProcess("video", "ffmpeg", "-y", "-r",
						txtFramerate.getText(), "-s", txtResolution.getText(),
						"-f", "x11grab", "-i", txtScreen.getText() + "+"
								+ txtOffset.getText(), "-c:v", "libx264",
						"-crf", "0", txtOutputDirectory.getText()
								+ "/video.mkv");
			} else { // GLC
				String[] base = { "glc-capture", "-v", "3", "-f",
						txtFramerate_GLC.getText(), "-k", txtHotkey.getText(),
						"--disable-audio", "-o",
						txtOutputDirectory.getText() + "/video.glc" };

				if (chckbxLockFramerate.isSelected())
					base = concat(base, new String[] { "-n" });
				if (chckbxAutostart.isSelected())
					base = concat(base, new String[] { "-s" });

				String[] command = translateCommandline(txtCommand.getText());
				String[] both = concat(base, command);

				startProcess("video", both);
			}
		}
		if (chckbxAudioInput.isSelected()) {
			for (int i = 0; i < tpAudioInput.getTabCount(); i++) {
				JPanel wp = (JPanel) tpAudioInput.getComponentAt(i);
				JPanel tab = (JPanel) wp.getComponent(0);

				String format = ((JTextField) tab.getComponent(3)).getText();
				String input = ((JTextField) tab.getComponent(5)).getText();

				startProcess("audio" + i, "ffmpeg", "-y", "-f", format, "-i",
						input, "-c:a", "flac", txtOutputDirectory.getText()
								+ "/audio" + i + ".flac");
			}
		}
	}

	private void startProcess(final String title, String... command) {
		System.err.println("Executing " + untranslateCommandline(command));
		txtrLog.append(title + ": " + untranslateCommandline(command));
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			final Process p = pb.start();
			new Thread(new Runnable() {
				public void run() {
					try (OutputStream out = new TextAreaOutputStream(txtrLog,
							title)) {
						InputStream in = p.getInputStream();
						int d;
						while ((d = in.read()) >= 0) {
							out.write(d);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			processes.add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findSettings() {
		if (System.getenv("DISPLAY") != null)
			txtScreen.setText(System.getenv("DISPLAY"));

		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDisplayMode();
		txtResolution.setText(dm.getWidth() + "x" + dm.getHeight());
	}

	private JPanel makeAudioInputPanel() {
		return makeAudioInputPanel("Input " + (tpAudioInput.getTabCount() + 1),
				"alsa", "pulse");
	}

	private JPanel makeAudioInputPanel(String name, String format, String input) {
		JPanel wpAudioInput = new JPanel();
		wpAudioInput.setLayout(new BorderLayout(0, 0));
		JPanel tmpAudioInput = new JPanel();
		wpAudioInput.add(tmpAudioInput, BorderLayout.NORTH);
		tmpAudioInput.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblName = new JLabel("Name:");
		tmpAudioInput.add(lblName);

		final JTextField txtName = new JTextField();
		txtName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tpAudioInput.setTitleAt(tpAudioInput.getSelectedIndex(),
						txtName.getText());
			}
		});
		txtName.setText(name);
		tmpAudioInput.add(txtName);
		txtName.setColumns(10);

		JLabel lblFormat = new JLabel("Format:");
		tmpAudioInput.add(lblFormat);

		JTextField txtFormat = new JTextField();
		txtFormat.setText(format);
		tmpAudioInput.add(txtFormat);
		txtFormat.setColumns(10);

		JLabel lblInput = new JLabel("Input:");
		tmpAudioInput.add(lblInput);

		JTextField txtInput = new JTextField();
		txtInput.setText(input);
		tmpAudioInput.add(txtInput);
		txtInput.setColumns(10);

		return wpAudioInput;
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static String untranslateCommandline(String[] toProcess) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < toProcess.length; i++) {
			if (toProcess[i].contains(" "))
				b.append("\"");
			b.append(toProcess[i]);
			if (toProcess[i].contains(" "))
				b.append("\"");
			if (i != toProcess.length - 1)
				b.append(" ");
		}
		return b.toString();
	}

	// Taken from Ant
	public static String[] translateCommandline(String toProcess) {
		if (toProcess == null || toProcess.length() == 0) {
			// no command? no string
			return new String[0];
		}
		// parse with a simple finite state machine

		final int normal = 0;
		final int inQuote = 1;
		final int inDoubleQuote = 2;
		int state = normal;
		final StringTokenizer tok = new StringTokenizer(toProcess, "\"\' ",
				true);
		final ArrayList<String> result = new ArrayList<String>();
		final StringBuilder current = new StringBuilder();
		boolean lastTokenHasBeenQuoted = false;

		while (tok.hasMoreTokens()) {
			String nextTok = tok.nextToken();
			switch (state) {
			case inQuote:
				if ("\'".equals(nextTok)) {
					lastTokenHasBeenQuoted = true;
					state = normal;
				} else {
					current.append(nextTok);
				}
				break;
			case inDoubleQuote:
				if ("\"".equals(nextTok)) {
					lastTokenHasBeenQuoted = true;
					state = normal;
				} else {
					current.append(nextTok);
				}
				break;
			default:
				if ("\'".equals(nextTok)) {
					state = inQuote;
				} else if ("\"".equals(nextTok)) {
					state = inDoubleQuote;
				} else if (" ".equals(nextTok)) {
					if (lastTokenHasBeenQuoted || current.length() != 0) {
						result.add(current.toString());
						current.setLength(0);
					}
				} else {
					current.append(nextTok);
				}
				lastTokenHasBeenQuoted = false;
				break;
			}
		}
		if (lastTokenHasBeenQuoted || current.length() != 0) {
			result.add(current.toString());
		}
		if (state == inQuote || state == inDoubleQuote) {
			throw new RuntimeException("unbalanced quotes in " + toProcess);
		}
		return result.toArray(new String[result.size()]);
	}
}
