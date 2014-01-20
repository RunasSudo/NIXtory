package io.github.runassudo.nixtory;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;
	private final JTextField summary;
	private final StringBuilder sb = new StringBuilder();
	private String title;

	public TextAreaOutputStream(final JTextArea textArea,
			final JTextField summary, String title) {
		this.textArea = textArea;
		this.summary = summary;
		this.title = title;
		sb.append(title + "> ");
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}

	@Override
	public void write(int b) throws IOException {
		if (b == '\r' || b == '\n') {
			final String text = sb.toString();
			// I DONT UNDERSTAND ANY OF THIS INVOKELATER WHATSITS!!!!!! ._.
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textArea.append(text + "\n");
				}
			});
			if (summary != null)
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						summary.setText(text);
					}
				});
			sb.setLength(0);
			sb.append(title);
			sb.append("> ");
		} else {
			sb.append((char) b);
		}
	}
}