package io.github.runassudo.libnixtory;

import java.util.HashMap;

import javax.swing.JComponent;

public abstract class FFmpegInput {
	public abstract void getSettings(HashMap<String, Setting> settings);

	@Override
	public abstract String toString();

	public static class Setting {
		public String displayName;
		public JComponent component;

		public Setting(String displayName, JComponent component) {
			this.displayName = displayName;
			this.component = component;
		}
	}
}
