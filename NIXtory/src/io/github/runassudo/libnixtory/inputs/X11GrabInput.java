package io.github.runassudo.libnixtory.inputs;

import java.util.HashMap;

import javax.swing.JTextField;

import io.github.runassudo.libnixtory.VideoInput;

public class X11GrabInput extends VideoInput {
	String screen, width, height, x, y, framerate;

	@Override
	public void getSettings(HashMap<String, Setting> settings) {
		settings.put("screen", new Setting("Screen", new JTextField(screen)));
		settings.put("width", new Setting("Width", new JTextField(width)));
		settings.put("height", new Setting("Height", new JTextField(height)));
		settings.put("x", new Setting("X", new JTextField(x)));
		settings.put("y", new Setting("Y", new JTextField(y)));
		settings.put("framerate", new Setting("Framerate", new JTextField(
				framerate)));
	}

	@Override
	public String toString() {
		return "Screen Capture";
	}
}
